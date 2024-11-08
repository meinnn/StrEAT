'use client'

import Link from 'next/link'
import StoreLink from '@/components/StoreLink'
import CartItem from '@/containers/customer/home/cart/CartItem'
import CartSkeletonPage from '@/components/skeleton/CartSkeleton'
import { useCart } from '@/contexts/CartContext'
import { useRouter } from 'next/navigation'
import EmptyCart from '@/containers/customer/home/cart/EmptyCart'
import { useState, useCallback } from 'react'

export default function CartPage() {
  const router = useRouter()
  const {
    cartItems,
    cartStore,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
    status,
    handleItemCheck,
    handleRemoveItem,
  } = useCart()

  const [loading, setLoading] = useState(false) // 로딩 상태 추가

  const totalPrice =
    cartItems
      .filter((item) => item.checked)
      .reduce((acc, item) => acc + item.price, 0) ?? 0

  const totalQuantity =
    cartItems
      .filter((item) => item.checked)
      .reduce((acc, item) => acc + item.quantity, 0) ?? 0

  // orderId 요청 함수
  const fetchOrderId = useCallback(async () => {
    if (!cartStore) {
      console.error('Store ID is missing.')
      return null
    }

    const requestBody = {
      storeId: cartStore.storeId,
      totalPrice, // 총 가격
      orderProducts: cartItems.map((item) => ({
        id: item.id,
        quantity: item.quantity,
        orderProductOptions: [], // 옵션을 추가하는 경우 여기에 반영
      })),
    }

    try {
      const response = await fetch(`/services/payments/orderid`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(requestBody),
      })

      if (!response.ok) {
        throw new Error('Order ID 발급 실패')
      }

      const result = await response.json()
      return result.orderId
    } catch (error) {
      console.error('Order ID 발급 오류:', error)
      return null
    }
  }, [cartStore, cartItems, totalPrice])

  // 결제 버튼 클릭 시 실행되는 함수
  const handlePayment = async () => {
    setLoading(true) // 로딩 상태 시작
    const orderId = await fetchOrderId() // orderId를 먼저 요청
    setLoading(false) // 로딩 상태 종료

    if (orderId) {
      // orderId가 발급되면 결제 페이지로 이동
      router.push(`/customer/payment/toss?orderId=${orderId}`)
    }
  }

  if (status === 'pending') return <CartSkeletonPage />
  if (cartItems.length === 0) return <EmptyCart />

  return (
    <div
      className="pb-20 p-4 flex flex-col justify-between"
      style={{ minHeight: 'calc(100vh - 4rem)' }}
      onScroll={(e) => {
        const { scrollTop, scrollHeight, clientHeight } = e.currentTarget
        if (scrollHeight - scrollTop === clientHeight && hasNextPage) {
          fetchNextPage()
        }
      }}
    >
      <div>
        <StoreLink
          storeId={cartStore?.storeId || 1}
          storeName={cartStore?.storeName || ''}
          storeSrc={cartStore?.storeSrc || '/images/default_img.jpg'}
        />

        {cartItems.map((item) => (
          <CartItem
            key={item.cartId}
            item={item}
            onCheck={handleItemCheck}
            onRemove={handleRemoveItem}
          />
        ))}

        <div className="w-full py-2 border border-primary-300 text-primary-500 rounded-lg text-center">
          <Link href={`/customer/stores/${cartStore?.storeId}`}>
            + 메뉴 추가
          </Link>
        </div>
      </div>

      <div>
        <h3 className="text-xl font-semibold ms-1 mt-8">예상 결제 금액</h3>
        <div className="border border-gray-medium rounded-lg p-4 mt-2">
          <div className="flex justify-between mb-2">
            <span className="text-gray-600">선택한 메뉴 수</span>
            <span>총 {totalQuantity}개</span>
          </div>
          <div className="flex justify-between">
            <span className="text-gray-600">예상 주문 금액</span>
            <span className="font-bold">{totalPrice.toLocaleString()}원</span>
          </div>
        </div>
      </div>

      <div className="fixed bottom-0 inset-x-0 p-3 bg-white">
        <button
          type="button"
          onClick={handlePayment} // 결제 버튼 클릭 시 실행
          className={`w-full py-4 font-bold rounded-lg ${
            totalQuantity > 0
              ? 'bg-primary-500 text-white'
              : 'bg-gray-medium text-gray-dark'
          }`}
          disabled={totalQuantity === 0 || loading} // 로딩 중이면 버튼 비활성화
        >
          {loading
            ? '결제 준비 중...'
            : `${totalPrice.toLocaleString()}원 결제하기`}
        </button>
      </div>

      {isFetchingNextPage && <p>Loading more items...</p>}
    </div>
  )
}
