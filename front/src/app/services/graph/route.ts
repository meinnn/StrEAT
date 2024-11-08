import { NextResponse } from 'next/server'
import { cookies } from 'next/headers'

// eslint-disable-next-line import/prefer-default-export
export async function GET(req: Request) {
  const cookieStore = cookies()
  const token = cookieStore.get('accessToken')?.value // 쿠키에서 access_token 가져오기

  if (!token) {
    return NextResponse.json({ message: 'Token is missing' }, { status: 401 })
  }

  try {
    const response = await fetch(
      `https://j11a307.p.ssafy.io/api/orders/order-manage/report`, // 엔드포인트에서 storeId 제거
      {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      }
    )

    if (!response.ok) {
      throw new Error('Failed to fetch order report')
    }

    const result = await response.json()
    return NextResponse.json(result) // API에서 받은 데이터를 반환
  } catch (error) {
    console.error('Error fetching order report:', error)
    return NextResponse.json(
      { message: 'Error fetching order report' },
      { status: 500 }
    )
  }
}
