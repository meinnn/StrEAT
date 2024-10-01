import { NextResponse } from 'next/server'
import { cookies } from 'next/headers'

// eslint-disable-next-line import/prefer-default-export
export async function POST() {
  const cookieStore = cookies()
  const token = cookieStore.get('accessToken')?.value // 쿠키에서 access_token 가져오기

  if (!token) {
    return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
  }

  const response = await fetch('https://j11a307.p.ssafy.io/api/users/logout', {
    method: 'POST',
    headers: {
      Authorization: `Bearer ${token}`,
    },
  })

  if (response.ok) {
    // 쿠키 삭제
    cookieStore.delete('accessToken')
    return NextResponse.json({ message: 'Logout successful' })
  }
  return NextResponse.json({ message: 'Logout failed' }, { status: 500 })
}
