import { cookies } from 'next/headers'
import { NextResponse } from 'next/server'

// eslint-disable-next-line import/prefer-default-export
export async function GET() {
  const cookieStore = cookies()
  const token = cookieStore.get('accessToken')?.value

  try {
    const response = await fetch(
      'https://j11a307.p.ssafy.io/api/users/profile',
      {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )

    const data = await response.json()
    return NextResponse.json(data, { status: 200 })
  } catch (error) {
    console.error('Error fetching user info:', error)
    return NextResponse.json(
      { message: 'Internal Server Error' },
      { status: 500 }
    )
  }
}
