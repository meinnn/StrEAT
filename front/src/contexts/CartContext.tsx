'use client'

import React, {
  createContext,
  useContext,
  useEffect,
  useState,
  useMemo,
  ReactNode,
  useCallback,
} from 'react'
import { useInfiniteQuery } from '@tanstack/react-query'
import { CartMenu } from '@/types/menu'

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

interface CartStore {
  storeId: number
  storeName: string
  storeSrc: string | null
}

interface CartContextProps {
  cartItems: CartMenu[]
  setCartItems: React.Dispatch<React.SetStateAction<CartMenu[]>>
  cartStore: CartStore | null
  fetchNextPage: () => void
  hasNextPage: boolean | undefined
  isFetchingNextPage: boolean
  status: 'error' | 'success' | 'pending'
  reloadCartItems: () => void
  handleItemCheck: (id: number) => void
  handleRemoveItem: (id: number) => void
}

const CartContext = createContext<CartContextProps | undefined>(undefined)

export const useCart = () => {
  const context = useContext(CartContext)
  if (!context) {
    throw new Error('useCart must be used within a CartProvider')
  }
  return context
}

async function fetchCartItems({ pageParam = 0 }): Promise<CartResponseData> {
  const response = await fetch(`/services/cart/list?page=${pageParam}`)
  if (!response.ok) {
    throw new Error('Failed to fetch cart items')
  }
  const result = await response.json()

  const updatedBasketList = result.basketList.map((item: BasketItem) => ({
    ...item,
    checked: true,
  }))

  return {
    ...result,
    basketList: updatedBasketList,
  }
}

export function CartProvider({ children }: { children: ReactNode }) {
  const [cartItems, setCartItems] = useState<CartMenu[]>([])
  const [cartStore, setCartStore] = useState<CartStore | null>(null)

  const {
    data,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
    status,
    refetch,
  } = useInfiniteQuery({
    queryKey: ['cartItems'],
    queryFn: fetchCartItems,
    getNextPageParam: (lastPage, pages) => {
      return pages.length < lastPage.totalPageCount ? pages.length : undefined
    },
    initialPageParam: 0,
  })

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

      const firstPageStore = data.pages[0]
      setCartStore({
        storeId: firstPageStore.storeId,
        storeName: firstPageStore.storeName,
        storeSrc: firstPageStore.storeSrc,
      })
    }
  }, [data])

  const reloadCartItems = useCallback(() => {
    refetch().then()
  }, [refetch])

  const handleItemCheck = useCallback((id: number) => {
    setCartItems((prevItems) =>
      prevItems.map((item) =>
        item.cartId === id ? { ...item, checked: !item.checked } : item
      )
    )
  }, [])

  const handleRemoveItem = useCallback(async (id: number) => {
    setCartItems((prevItems) => prevItems.filter((item) => item.cartId !== id))

    const response = await fetch(`/services/cart/item/${id}`, {
      method: 'DELETE',
    })

    if (!response.ok) {
      throw new Error('Failed to delete cart item')
    }
  }, [])

  // useMemo를 사용하여 value 객체 메모이제이션
  const value = useMemo(
    () => ({
      cartItems,
      setCartItems,
      cartStore,
      fetchNextPage,
      hasNextPage,
      isFetchingNextPage,
      status,
      reloadCartItems,
      handleItemCheck,
      handleRemoveItem,
    }),
    [
      cartItems,
      cartStore,
      fetchNextPage,
      handleItemCheck,
      handleRemoveItem,
      hasNextPage,
      isFetchingNextPage,
      reloadCartItems,
      status,
    ]
  )

  return <CartContext.Provider value={value}>{children}</CartContext.Provider>
}
