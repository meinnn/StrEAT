'use client'

import Link from 'next/link'
import StoreLink from '@/components/StoreLink'
import CartItem from '@/containers/customer/home/cart/CartItem'
import CartSkeletonPage from '@/components/skeleton/CartSkeleton'
import { useCart } from '@/contexts/CartContext'

export default function CartPage() {
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

  const totalPrice =
    cartItems
      .filter((item) => item.checked)
      .reduce((acc, item) => acc + item.price, 0) ?? 0

  const totalQuantity =
    cartItems
      .filter((item) => item.checked)
      .reduce((acc, item) => acc + item.quantity, 0) ?? 0

  if (status === 'pending') return <CartSkeletonPage />

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
          <Link href="/customer/stores/1">+ 메뉴 추가</Link>
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
          className={`w-full py-4 font-bold rounded-lg ${
            totalQuantity > 0
              ? 'bg-primary-500 text-white'
              : 'bg-gray-medium text-gray-dark'
          }`}
          disabled={totalQuantity === 0}
        >
          {`${totalPrice.toLocaleString()}원 결제하기`}
        </button>
      </div>

      {isFetchingNextPage && <p>Loading more items...</p>}
    </div>
  )
}
