import { NextResponse } from 'next/server'
import { cookies } from 'next/headers'

// eslint-disable-next-line import/prefer-default-export
export async function GET() {
  try {
    const cookieStore = cookies()
    const token = cookieStore.get('accessToken')?.value

    const response = await fetch(
      'https://j11a307.p.ssafy.io/api/orders/order-request/mine/list/ongoing',
      {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${token}`,
        },
        cache: 'no-store',
      }
    )

    const result = await response.json()
    return NextResponse.json(result.data, { status: 200 })
  } catch (error) {
    console.error('Error fetching ongoing order list:', error)
    return NextResponse.json(
      { message: 'Internal Server Error' },
      { status: 500 }
    )
  }
}
