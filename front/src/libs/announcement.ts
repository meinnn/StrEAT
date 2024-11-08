/* eslint-disable import/prefer-default-export */

// 푸드트럭 공고 내역 조회 API - GET
// /services/announcement/list
export const fetchAnnouncementList = async (token: string) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/announcements/list`,
    {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${token}`,
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

// 푸드트럭 공고 제출 파일 자동화 API - POST
// /services/announcement/create-doc
export const postAnnouncementFile = async (
  token: string,
  {
    eventName,
  }: {
    eventName: string
  }
) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/announcements/create-doc`,
    {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
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
