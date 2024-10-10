/* eslint-disable import/prefer-default-export */
const defaultHeaders = {
  'Content-Type': 'application/json',
  'Authorization':
    'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhY2Nlc3MtdG9rZW4iLCJpYXQiOjE3Mjc4MzE0MTQsImV4cCI6MjA4NzgzMTQxNCwidXNlcklkIjoxMn0.UrVrI-WUCXdx017R4uRIl6lzxbktVSfEDjEgYe5J8UQ',
}

// 메뉴 전체 조회 API
export const fetchStoreMenuList = async (storeId: number | string) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/products/store/${storeId}/detail`,
    {
      method: 'GET',
      headers: defaultHeaders,
      cache: 'no-store',
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

// 품절 여부 수정하는 API (true: 품절X / false: 품절O)
// /services/store/[id]/menu/stock-status
export const updateProductStockStatus = async (productId: string) => {
  console.log('품절 여부 페이로드:', productId)
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/products/${productId}/stock-status`,
    {
      method: 'PUT',
      headers: defaultHeaders,
      cache: 'no-store',
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
