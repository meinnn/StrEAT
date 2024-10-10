/* eslint-disable import/prefer-default-export */

// 메뉴 전체 조회 API
export const fetchStoreMenuList = async (
  token: string,
  storeId: number | string
) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/products/store/${storeId}/detail`,
    {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
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
export const updateProductStockStatus = async (
  token: string,
  productId: string
) => {
  console.log('품절 여부 페이로드:', productId)
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/products/${productId}/stock-status`,
    {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
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
