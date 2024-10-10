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
    // FormData 받기
    const formData = await request.formData()

    // 외부 API로 FormData와 함께 요청
    const response = await fetch(
      'https://j11a307.p.ssafy.io/api/products/all',
      {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${token}`, // 인증 토큰 추가
        },
        body: formData, // FormData 전송
      }
    )

    if (response.ok) {
      const data = await response.json()
      return NextResponse.json({ message: 'Register successful', data })
    }

    // 실패 시 에러 메시지 반환
    const errorData = await response.json()
    return NextResponse.json(
      { message: 'Register failed', error: errorData },
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
