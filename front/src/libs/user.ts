/* eslint-disable import/prefer-default-export */

// 사장 정보 조회 API
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
