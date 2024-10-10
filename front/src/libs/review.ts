/**
 * 리뷰 관련 API
 *
 *
 * */

/* eslint-disable import/prefer-default-export */

// 점포별 리뷰 개수 및 평점 조회
// /services/stores/[storeId]/reviews/summary
export const fetchStoreReviewCountAverage = async (token: string) => {
  const storeId = 60
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/orders/stores/${storeId}/reviews/summary`,
    {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
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
