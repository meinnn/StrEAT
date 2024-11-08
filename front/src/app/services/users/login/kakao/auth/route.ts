import { NextResponse } from 'next/server'
import { cookies } from 'next/headers'

const BASE_URL = 'https://j11a307.p.ssafy.io'

// eslint-disable-next-line import/prefer-default-export
export async function GET(request: Request) {
  const url = new URL(request.url)
  const code = url.searchParams.get('code')

  if (!code) {
    // 인가 코드가 없는 경우 에러 처리
    return NextResponse.json(
      { error: 'Authorization code not found' },
      { status: 400 }
    )
  }

  const apiUrl = `https://j11a307.p.ssafy.io/api/users/login/kakao/auth?code=${code}`

  try {
    const response = await fetch(apiUrl, {
      method: 'GET',
    })

    if (!response.ok) {
      // 요청 실패 시 에러 처리
      const errorData = await response.json()
      return NextResponse.json(
        { error: errorData },
        { status: response.status }
      )
    }

    const accessToken = response.headers.get('accesstoken')
    if (!accessToken) {
      return NextResponse.json({ error: 'No Access Token' }, { status: 500 })
    }

    // 쿠키에 액세스 토큰 설정
    cookies().set({
      name: 'accessToken',
      value: accessToken,
      httpOnly: true,
      path: '/',
      maxAge: 60 * 24 * 60 * 60, // 2개월
    })

    const data = await response.json()
    const { userType } = data

    return NextResponse.redirect(
      new URL(`/permission?userType=${userType}`, BASE_URL)
    )
  } catch (error) {
    // 네트워크 오류 등의 예외 처리
    return NextResponse.json({ error: 'Failed to fetch data' }, { status: 500 })
  }
}
