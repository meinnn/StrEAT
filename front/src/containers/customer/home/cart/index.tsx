'use client'

import { useEffect, useState } from 'react'
import Link from 'next/link'
import StoreLink from '@/components/StoreLink'
import { useInfiniteQuery } from '@tanstack/react-query'
import { CartMenu } from '@/types/menu'
import CartItem from '@/containers/customer/home/cart/CartItem'
import CartSkeletonPage from '@/components/skeleton/CartSkeleton'

interface BasketItem {
  basketId: number
  productId: number
  productName: string
  productSrc: string | null
  quantity: number
  price: number
  optionNameList: string[]
}

interface CartResponseData {
  storeId: number
  storeName: string
  storeSrc: string
  basketList: BasketItem[]
  totalPageCount: number
  totalDataCount: number
}

async function fetchCartItems({
  pageParam = 0,
}: {
  pageParam: number
}): Promise<CartResponseData> {
  const response = await fetch(`/services/cart/list?page=${pageParam}`)
  if (!response.ok) {
    throw new Error('Failed to fetch cart items')
  }
  const result = await response.json()

  const updatedBasketList = result.data.basketList.map((item: BasketItem) => ({
    ...item,
    checked: true,
  }))

  return {
    ...result.data,
    basketList: updatedBasketList,
  }
}

export default function CartPage() {
  const [cartItems, setCartItems] = useState<CartMenu[]>([]) // cartItems 상태 추가

  const {
    data,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
    status,
    error,
  } = useInfiniteQuery({
    queryKey: ['cartItems'],
    queryFn: fetchCartItems,
    getNextPageParam: (lastPage, pages) => {
      return pages.length < lastPage.totalPageCount ? pages.length : undefined
    },
    initialPageParam: 0,
  })

  // 데이터가 변경될 때마다 cartItems 업데이트
  useEffect(() => {
    if (data) {
      const allItems = data.pages.flatMap((page) => page.basketList)
      const cartMenus: CartMenu[] = allItems.map((item) => ({
        cartId: item.basketId,
        id: item.productId,
        storeId: data.pages[0].storeId,
        name: item.productName,
        image: item.productSrc || '/images/default_img.jpg',
        quantity: item.quantity,
        optionNameList: item.optionNameList,
        price: item.price,
        checked: true,
        optionCategories: [], // Option categories는 초기값으로 빈 배열
      }))
      setCartItems(cartMenus)
    }
  }, [data])

  // 체크박스 상태 업데이트 로직
  const handleItemCheck = (id: number) => {
    setCartItems((prevItems) =>
      prevItems.map((item) =>
        item.cartId === id ? { ...item, checked: !item.checked } : item
      )
    )
  }

  // 아이템 제거 로직
  const handleRemoveItem = async (id: number) => {
    setCartItems((prevItems) => prevItems.filter((item) => item.cartId !== id))

    try {
      const response = await fetch(`/services/cart/item/${id}`, {
        method: 'DELETE',
      })

      if (!response.ok) {
        return new Error('Failed to delete cart item')
      }
    } catch (err) {
      console.error('Error adding item to cart:', error)
    }
    return () => {}
  }

  const totalPrice =
    cartItems
      .filter((item) => item.checked)
      .reduce((acc, item) => acc + item.price, 0) ?? 0

  const totalQuantity = cartItems.filter((item) => item.checked).length ?? 0

  if (status === 'pending') return <CartSkeletonPage />
  if (status === 'error') return <p>Error: {error.message}</p>

  return (
    <div
      className="pb-20 p-4 flex flex-col justify-between"
      style={{ minHeight: 'calc(100vh - 4rem)' }}
      onScroll={(e) => {
        const { scrollTop, scrollHeight, clientHeight } = e.currentTarget
        if (scrollHeight - scrollTop === clientHeight && hasNextPage) {
          fetchNextPage().then()
        }
      }}
    >
      <div>
        <StoreLink
          storeId={1}
          storeName={data?.pages[0]?.storeName ?? ''}
          storeSrc={data?.pages[0]?.storeSrc ?? '/images/default_img.jpg'}
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
