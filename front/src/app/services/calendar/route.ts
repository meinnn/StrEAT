import { NextResponse } from 'next/server'
import { cookies } from 'next/headers'

// eslint-disable-next-line import/prefer-default-export
export async function GET(req: Request) {
  const { searchParams } = new URL(req.url)
  const month = searchParams.get('month')
  const cookieStore = cookies()
  const token = cookieStore.get('accessToken')?.value

  if (!month) {
    return NextResponse.json({ message: 'Month is required' }, { status: 400 })
  }

  if (!token) {
    return NextResponse.json({ message: 'Token is missing' }, { status: 401 })
  }

  try {
    // API 요청을 보냄
    const response = await fetch(
      `https://j11a307.p.ssafy.io/api/orders/order-manage/day-list?month=${month}`,
      {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`, // Authorization 헤더에 토큰 추가
          'Content-Type': 'application/json',
        },
      }
    )

    if (!response.ok) {
      // 응답이 JSON 형식인 경우 JSON 파싱, 그렇지 않으면 텍스트로 받음
      let errorMsg
      try {
        errorMsg = await response.json()
      } catch {
        errorMsg = await response.text()
      }
      console.error(`Failed to fetch daily sales: ${errorMsg}`)
      return NextResponse.json(
        { message: 'Failed to fetch daily sales', details: errorMsg },
        { status: response.status }
      )
    }

    const result = await response.json()

    // 성공적으로 데이터를 받았을 때, 응답 데이터를 반환
    return NextResponse.json(result)
  } catch (error) {
    console.error('Error fetching daily sales:', error)
    return NextResponse.json(
      { message: 'Failed to fetch daily sales' },
      { status: 500 }
    )
  }
}
