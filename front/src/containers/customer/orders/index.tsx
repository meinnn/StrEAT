'use client'

import { useInfiniteQuery } from '@tanstack/react-query'
import { useEffect, useRef } from 'react'
import AppBar from '@/components/AppBar'
import OrderItem from '@/containers/customer/orders/OrderItem'
import ReviewSkeleton from '@/components/skeleton/ReviewSkeleton'
import getConvertedDate from '@/utils/getConvertedDate'

const PROCESS_ID = {
  REJECTED: 0,
  WAITING_FOR_PROCESSING: 1,
  PROCESSING: 2,
  WAITING_FOR_RECEIPT: 3,
  RECEIVED: 4,
}

type ProcessIdKeys = keyof typeof PROCESS_ID

interface Order {
  isReviewed: boolean
  orderStatus: ProcessIdKeys
  ordersCreatedAt: string
  ordersId: number
  products: {
    productName: string
    orderProductCount: number
  }[]
  storeId: number
  storeName: string
  storePhoto: string
}

interface Page {
  data: {
    getMyOrderList: Order[]
    totalDataCount: number
    totalPageCount: number
  }
  hasMore: boolean
}

const fetchMyOrderList = async ({ pageParam = 0 }: any) => {
  try {
    const response = await fetch(
      `/services/order/me?page=${pageParam}&limit=5`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization':
            'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhY2Nlc3MtdG9rZW4iLCJpYXQiOjE3Mjc4MzE0MTQsImV4cCI6MjA4NzgzMTQxNCwidXNlcklkIjoxMn0.UrVrI-WUCXdx017R4uRIl6lzxbktVSfEDjEgYe5J8UQ',
        },
      }
    )

    if (!response.ok) {
      throw new Error('Failed to fetch review')
    }

    return await response.json()
  } catch (error) {
    console.error('Error:', error)
    throw error
  }
}

export default function Orders() {
  let lastDate = ''

  const {
    data: orderListData,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
    status,
  } = useInfiniteQuery<Page, Error>({
    queryFn: async ({ pageParam = 0 }) => fetchMyOrderList({ pageParam }),
    queryKey: ['/services/review/me'],
    getNextPageParam: (lastPage: Page, pages: Page[]) => {
      if (lastPage?.hasMore) {
        return pages.length
      }
      return undefined
    },
    initialPageParam: 0,
  })

  const observerElem = useRef(null)

  useEffect(() => {
    const currentElem = observerElem.current

    const observer = new IntersectionObserver(
      (entries) => {
        if (entries[0].isIntersecting && hasNextPage) {
          fetchNextPage()
        }
      },
      {
        rootMargin: '100px',
      }
    )

    if (currentElem) observer.observe(currentElem)

    return () => {
      if (currentElem) observer.unobserve(currentElem)
    }
  }, [fetchNextPage, hasNextPage])

  if (status === 'pending') {
    return <ReviewSkeleton />
  }

  return (
    <div className="bg-secondary-light">
      <AppBar title="주문내역 조회" />
      <main className=" px-4 flex flex-col pb-32">
        <div className="flex flex-col gap-1">
          {orderListData && orderListData?.pages?.length > 0 ? (
            <>
              {orderListData?.pages?.map((page) =>
                page?.data?.getMyOrderList.map((order) => {
                  const isVisibleDate =
                    lastDate !== getConvertedDate(order.ordersCreatedAt)
                  lastDate = getConvertedDate(order.ordersCreatedAt)

                  return (
                    <div key={order.ordersId}>
                      {isVisibleDate && (
                        <h2 className="font-medium pb-5 pt-7 text-text">
                          {getConvertedDate(order.ordersCreatedAt)}
                        </h2>
                      )}
                      <OrderItem
                        key={order.ordersId}
                        id={order.ordersId}
                        date={getConvertedDate(order.ordersCreatedAt)}
                        storeImageUrl={order.storePhoto}
                        storeName={order.storeName}
                        orderList={order.products}
                        orderStatus={order.orderStatus}
                        isReviewed={order.isReviewed}
                      />
                    </div>
                  )
                })
              )}
              <div ref={observerElem}>
                {isFetchingNextPage && <ReviewSkeleton />}
              </div>
            </>
          ) : (
            <p>값 없어</p>
          )}
        </div>
      </main>
    </div>
  )
}
