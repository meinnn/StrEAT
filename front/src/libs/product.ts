/* eslint-disable import/prefer-default-export */

export const menuAdd = async (payload: FormData, authToken: string) => {
  try {
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_BACK_URL}/api/products/all`,
      {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${authToken}`, // 헤더에 인증 토큰 추가
        },
        body: payload,
      }
    )

    if (!response.ok) {
      // 서버에서 반환하는 에러 메시지를 받아옴
      let errorMessage = '서버 오류가 발생했습니다.'
      try {
        const errorData = await response.json()
        errorMessage = errorData.message || errorMessage
      } catch (jsonError) {
        console.error('JSON 파싱 오류:', jsonError)
      }
      throw new Error(
        JSON.stringify({ message: errorMessage, status: response.status })
      )
    }

    return await response.json() // JSON 응답을 반환
  } catch (error) {
    console.error('API 요청 실패:', error) // 네트워크 문제 등 예기치 못한 에러 로그
    throw error // 상위로 에러를 다시 던짐
  }
}

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
