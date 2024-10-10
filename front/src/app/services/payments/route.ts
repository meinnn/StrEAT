import { NextRequest, NextResponse } from 'next/server'
import { cookies } from 'next/headers'

// eslint-disable-next-line import/prefer-default-export
export async function POST(request: NextRequest) {
  const cookieStore = cookies()
  const token = cookieStore.get('accessToken')?.value // 쿠키에서 access_token 가져오기

  if (!token) {
    return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
  }

  try {
    // 요청 본문에서 데이터를 받기
    const { paymentKey, orderId, amount } = await request.json()

    if (!paymentKey || !orderId || !amount) {
      return NextResponse.json(
        { message: 'Missing required request body parameters' },
        { status: 400 }
      )
    }

    // API로 JSON 요청 보내기 (Swagger 형식에 맞춰 요청)
    const response = await fetch(
      'https://j11a307.p.ssafy.io/api/payments/toss/request-payment',
      {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          paymentKey,
          orderId,
          amount,
        }),
      }
    )

    if (response.ok) {
      const data = await response.json()
      return NextResponse.json({ message: 'Payment successful', data })
    }

    // 실패 시 에러 메시지 반환
    const errorData = await response.json()
    return NextResponse.json(
      { message: 'Payment failed', error: errorData },
      { status: response.status }
    )
  } catch (error) {
    console.error('API 요청 실패:', error)
    return NextResponse.json(
      { message: '서버 오류가 발생했습니다.' },
      { status: 500 }
    )
  }
}
