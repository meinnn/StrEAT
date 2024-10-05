import { NextResponse } from 'next/server'
import { cookies } from 'next/headers'

async function getAccessToken() {
  const cookieStore = cookies()
  return cookieStore.get('accessToken')?.value // 쿠키에서 accessToken 가져오기
}

export async function POST(
  request: Request,
  { params }: { params: { id: string } }
) {
  const token = await getAccessToken()
  if (!token) {
    return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
  }

  const body = await request.json()
  try {
    const response = await fetch(
      `https://j11a307.p.ssafy.io/api/orders/${params.id}/basket`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify(body),
      }
    )

    if (!response.ok) {
      return NextResponse.json(
        { message: 'Failed to add menu to cart' },
        { status: response.status }
      )
    }

    const result = await response.json()
    return NextResponse.json(result, { status: 201 })
  } catch (error) {
    console.error('Error fetching option category:', error)
    return NextResponse.json(
      { message: 'Internal Server Error' },
      { status: 500 }
    )
  }
}

export async function DELETE(
  request: Request,
  { params }: { params: { id: string } }
) {
  const token = await getAccessToken()
  if (!token) {
    return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
  }

  try {
    const response = await fetch(
      `https://j11a307.p.ssafy.io/api/orders/${params.id}/basket`,
      {
        method: 'DELETE',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
    console.log(response)

    if (!response.ok) {
      return NextResponse.json(
        { message: 'Failed to delete menu from cart' },
        { status: response.status }
      )
    }

    return NextResponse.json({ message: 'Remove item successful' })
  } catch (error) {
    console.error('Error fetching option category:', error)
    return NextResponse.json(
      { message: 'Internal Server Error' },
      { status: 500 }
    )
  }
}
