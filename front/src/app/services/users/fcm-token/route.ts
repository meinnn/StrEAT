import { NextRequest, NextResponse } from 'next/server'
import { cookies } from 'next/headers'

// eslint-disable-next-line import/prefer-default-export
export async function POST(request: NextRequest) {
  const cookieStore = cookies()
  const token = cookieStore.get('accessToken')?.value

  if (!token) {
    return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
  }

  const { fcmToken } = await request.json()

  const response = await fetch(
    `https://j11a307.p.ssafy.io/api/users/fcm-token`,
    {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
      body: fcmToken,
    }
  )

  if (response.ok) {
    return NextResponse.json({ message: 'Send FCM token successful' })
  }
  // 실패 시 에러 메시지 반환
  return NextResponse.json(
    { message: 'Send FCM token failed' },
    { status: 500 }
  )
}
