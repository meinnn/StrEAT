import { NextResponse } from 'next/server'
import { cookies } from 'next/headers'

// eslint-disable-next-line import/prefer-default-export
export async function GET(
  request: Request,
  { params }: { params: { id: string } }
) {
  const cookieStore = cookies()
  const token = cookieStore.get('accessToken')?.value // 쿠키에서 access_token 가져오기

  if (!token) {
    return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
  }
  try {
    const response = await fetch(
      `https://j11a307.p.ssafy.io/api/orders/basket/${params.id}/info`,
      {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )

    if (!response.ok) {
      return NextResponse.json(
        { message: 'Failed to fetch menu details' },
        { status: response.status }
      )
    }

    const result = await response.json()
    return NextResponse.json(result.data, {
      status: 200,
    })
  } catch (error) {
    console.error('Error fetching option category:', error)
    return NextResponse.json(
      { message: 'Internal Server Error' },
      { status: 500 }
    )
  }
}
