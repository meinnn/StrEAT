import { NextRequest, NextResponse } from 'next/server'
import { cookies } from 'next/headers'

// eslint-disable-next-line import/prefer-default-export
export async function GET(request: NextRequest) {
  const cookieStore = cookies()
  const token = cookieStore.get('accessToken')?.value // 쿠키에서 accessToken 가져오기
  console.log('토큰정보:', token)
  if (!token) {
    return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
  }

  try {
    // 외부 API로 GET 요청
    const response = await fetch(
      'https://j11a307.p.ssafy.io/api/orders/order-manage/spot/top-three',
      {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`, // 인증 토큰 추가
          'Content-Type': 'application/json',
        },
      }
    )

    if (response.ok) {
      const data = await response.json()
      return NextResponse.json({ message: 'Request successful', data })
    }

    // 실패 시 에러 메시지 반환
    const errorData = await response.json()
    return NextResponse.json(
      { message: 'Request failed', error: errorData },
      { status: 500 }
    )
  } catch (error) {
    console.error('API 요청 실패:', error)
    return NextResponse.json(
      { message: '서버 오류가 발생했습니다.' },
      { status: 500 }
    )
  }
}
