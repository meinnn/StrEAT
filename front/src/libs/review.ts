/**
 * 리뷰 관련 API
 *
 *
 * */

/* eslint-disable import/prefer-default-export */
const defaultHeaders = {
  'Content-Type': 'application/json',
  'Authorization':
    'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhY2Nlc3MtdG9rZW4iLCJpYXQiOjE3Mjc4MzE0MTQsImV4cCI6MjA4NzgzMTQxNCwidXNlcklkIjoxMn0.UrVrI-WUCXdx017R4uRIl6lzxbktVSfEDjEgYe5J8UQ',
}

// 점포별 리뷰 개수 및 평점 조회
// /services/stores/[storeId]/reviews/summary
export const fetchStoreReviewCountAverage = async () => {
  const storeId = 60
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/orders/stores/${storeId}/reviews/summary`,
    {
      method: 'GET',
      headers: defaultHeaders,
      cache: 'no-cache',
    }
  )

  if (!response.ok) {
    const errorMessage = await response.json()
    throw new Error(
      JSON.stringify({ message: errorMessage.message, status: response.status })
    )
  }

  return response
}
