'use client'

import { useQuery } from '@tanstack/react-query'
import ReviewItem from '@/containers/customer/mypage/components/ReviewListItem'
import getConvertedDate from '@/utils/getConvertedDate'
import ReviewSkeleton from '@/components/skeleton/ReviewSkeleton'

interface Review {
  content: string
  createdAt: string
  storeId: number
  reviewId: number
  orderProducts: string[]
  score: number
  srcList: string[]
  storeName: string
  storePhoto?: string
}

const fetchMyReview = async () => {
  try {
    const response = await fetch(`/api/review/me`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization':
          'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhY2Nlc3MtdG9rZW4iLCJpYXQiOjE3Mjc4MzE0MTQsImV4cCI6MjA4NzgzMTQxNCwidXNlcklkIjoxMn0.UrVrI-WUCXdx017R4uRIl6lzxbktVSfEDjEgYe5J8UQ',
      },
    })

    if (!response.ok) {
      throw new Error('Failed to fetch review')
    }

    return await response.json()
  } catch (error) {
    console.error('Error:', error)
    throw error
  }
}

export default function MyReviewList() {
  let lastDate = ''

  const {
    data: reviewList,
    isLoading,
    error,
  } = useQuery<Review[]>({
    queryKey: ['/api/review/me'],
    queryFn: fetchMyReview,
  })

  if (isLoading) {
    return <ReviewSkeleton />
  }

  return (
    <div>
      {reviewList &&
        reviewList.length > 0 &&
        reviewList.map((review) => {
          const isVisibleDate = lastDate !== getConvertedDate(review.createdAt)
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
        })}
    </div>
  )
}
