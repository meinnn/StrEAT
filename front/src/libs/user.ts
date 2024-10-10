/* eslint-disable import/prefer-default-export */
const defaultHeaders = {
  'Content-Type': 'application/json',
  'Authorization':
    'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhY2Nlc3MtdG9rZW4iLCJpYXQiOjE3Mjc4MzE0MTQsImV4cCI6MjA4NzgzMTQxNCwidXNlcklkIjoxMn0.UrVrI-WUCXdx017R4uRIl6lzxbktVSfEDjEgYe5J8UQ',
}

// 사장 정보 조회 API
export const fetchOwnerInformation = async () => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/users/owners/profile`,
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
