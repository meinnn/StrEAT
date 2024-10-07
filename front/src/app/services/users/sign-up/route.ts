import { NextRequest, NextResponse } from 'next/server'
import { cookies } from 'next/headers'

// eslint-disable-next-line import/prefer-default-export
export async function POST(request: NextRequest) {
  const cookieStore = cookies()
  const token = cookieStore.get('accessToken')?.value // 쿠키에서 access_token 가져오기

  if (!token) {
    return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
  }

  const { userType } = await request.json() // 요청 본문에서 userType 가져오기

  const response = await fetch(
    `https://j11a307.p.ssafy.io/api/users/${userType}/register`,
    {
      method: 'POST',
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }
  )

  if (response.ok) {
    return NextResponse.json({ message: 'Register successful' })
  }
  // 실패 시 에러 메시지 반환
  return NextResponse.json({ message: 'Register failed' }, { status: 500 })
}
