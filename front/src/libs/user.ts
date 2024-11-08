/* eslint-disable import/prefer-default-export */

// 사장 정보 조회 API - 완료
export const fetchOwnerInformation = async (token: string) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/users/owners/profile`,
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

// 찜 목록 조회 API
export const fetchFavoriteList = async (token: string) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/users/dibs`,
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

// 가게 찜하는 API - POST
export const postAddFavoriteStore = async (token: string, storeId: string) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/users/dibs/${storeId}`,
    {
      method: 'POST',
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

// 가게 찜 취소하는 API - DELETE
export const postDeleteFavoriteStore = async (
  token: string,
  storeId: string
) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/users/dibs/${storeId}`,
    {
      method: 'DELETE',
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

// 가게 알림 켜는 API - POST
export const postOnStoreNotification = async (
  token: string,
  storeId: number
) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/users/dibs/alert/${storeId}`,
    {
      method: 'POST',
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

// 가게 알림 끄는 API - DELETE
export const postOffStoreNotification = async (
  token: string,
  storeId: number
) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/users/dibs/alert/${storeId}`,
    {
      method: 'DELETE',
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
