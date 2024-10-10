/* eslint-disable consistent-return */

'use client'

import { useInfiniteQuery } from '@tanstack/react-query'
import { useEffect, useRef } from 'react'
import Image from 'next/image'
import ReviewItem from '@/containers/customer/mypage/components/ReviewListItem'
import getConvertedDate from '@/utils/getConvertedDate'
import ReviewSkeleton from '@/components/skeleton/ReviewSkeleton'

interface Review {
  storeName: string
  content: string
  createdAt: string
  storeId: number
  reviewId: number
  orderProducts: string[]
  score: number
  srcList: string[]
  storePhoto?: string
}

interface Page {
  data: {
    getMyReviewList: Review[]
    totalDataCount: number
    totalPageCount: number
  }
  hasMore: boolean
}

const fetchMyReview = async ({ pageParam = 0 }: any) => {
  try {
    const response = await fetch(
      `/services/review/me?page=${pageParam}&limit=5`,
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
  }
}

export default function MyReviewList() {
  let lastDate = ''

  const {
    data: reviewData,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
    status,
  } = useInfiniteQuery<Page, Error>({
    queryFn: async ({ pageParam = 0 }) => fetchMyReview({ pageParam }),
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
    <div>
      {reviewData && reviewData?.pages?.length > 0 ? (
        <>
          {reviewData?.pages?.map((page) =>
            page?.data?.getMyReviewList?.map((review) => {
              const isVisibleDate =
                lastDate !== getConvertedDate(review.createdAt)
              lastDate = getConvertedDate(review.createdAt)

              return (
                <div key={review.reviewId}>
                  {isVisibleDate && (
                    <h2 className="pl-4 pt-7 pb-3 text-lg font-medium text-text">
                      {getConvertedDate(review.createdAt)}
                    </h2>
                  )}
                  <ReviewItem
                    reviewId={review.reviewId}
                    storeId={review.storeId}
                    storeName={review.storeName}
                    storeImage={review.storePhoto}
                    date={getConvertedDate(review.createdAt)}
                    orderList={review.orderProducts}
                    score={review.score}
                    content={review.content}
                    reviewImageList={review.srcList}
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
        <div className="flex justify-center items-center h-80 flex-col gap-1">
          <div className="relative w-40 aspect-square">
            <Image
              src="/images/no_content_illustration.png"
              className="object-cover"
              alt="내용이 없다는 일러스트"
              fill
              priority
            />
          </div>
          <p className="text-text font-bold">조회된 리뷰가 없습니다</p>
        </div>
      )}
    </div>
  )
}
