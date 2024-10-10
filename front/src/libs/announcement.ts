/* eslint-disable import/prefer-default-export */
const defaultHeaders = {
  'Content-Type': 'application/json',
  'Authorization':
    'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhY2Nlc3MtdG9rZW4iLCJpYXQiOjE3Mjc4MzE0MTQsImV4cCI6MjA4NzgzMTQxNCwidXNlcklkIjoxMn0.UrVrI-WUCXdx017R4uRIl6lzxbktVSfEDjEgYe5J8UQ',
}

// 푸드트럭 공고 내역 조회 API - GET
// /services/announcement/list
export const fetchAnnouncementList = async () => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/announcements/list`,
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

// 푸드트럭 공고 제출 파일 자동화 API - POST
// /services/announcement/create-doc
export const postAnnouncementFile = async ({
  eventName,
}: {
  eventName: string
}) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/announcements/create-doc`,
    {
      method: 'POST',
      headers: defaultHeaders,
      body: JSON.stringify({ eventName }),
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
