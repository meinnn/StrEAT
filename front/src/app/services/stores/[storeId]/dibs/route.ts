import { NextResponse } from 'next/server'
import { cookies } from 'next/headers'

export async function GET(
  request: Request,
  { params }: { params: { storeId: string } }
) {
  const cookieStore = cookies()
  const token = cookieStore.get('accessToken')?.value

  if (!token) {
    return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
  }

  try {
    const response = await fetch(
      `https://j11a307.p.ssafy.io/api/users/dibs/${params.storeId}/called`,
      {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )

    if (!response.ok) {
      if (response.status === 404) {
        return NextResponse.json(
          { message: '잘못된 유저입니다.' },
          { status: 404 }
        )
      }
      return NextResponse.json(
        { message: 'Failed to fetch dibs status' },
        { status: response.status }
      )
    }

    const data = await response.json()
    return NextResponse.json(data, { status: 200 })
  } catch (error) {
    console.error('Error fetching dibs status:', error)
    return NextResponse.json(
      { message: 'Internal Server Error' },
      { status: 500 }
    )
  }
}

// 가게 찜 추가 (POST)
export async function POST(
  request: Request,
  { params }: { params: { storeId: string } }
) {
  const cookieStore = cookies()
  const token = cookieStore.get('accessToken')?.value

  if (!token) {
    return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
  }

  try {
    const response = await fetch(
      `https://j11a307.p.ssafy.io/api/users/dibs/${params.storeId}`,
      {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )

    if (!response.ok) {
      return NextResponse.json(
        { message: 'Failed to add store to dibs' },
        { status: response.status }
      )
    }

    return NextResponse.json(
      { message: 'Store added to dibs' },
      { status: 200 }
    )
  } catch (error) {
    console.error('Error adding store to dibs:', error)
    return NextResponse.json(
      { message: 'Internal Server Error' },
      { status: 500 }
    )
  }
}

// 가게 찜 삭제 (DELETE)
export async function DELETE(
  request: Request,
  { params }: { params: { storeId: string } }
) {
  const cookieStore = cookies()
  const token = cookieStore.get('accessToken')?.value

  if (!token) {
    return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
  }

  try {
    const response = await fetch(
      `https://j11a307.p.ssafy.io/api/users/dibs/${params.storeId}`,
      {
        method: 'DELETE',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )

    if (!response.ok) {
      return NextResponse.json(
        { message: 'Failed to remove store from dibs' },
        { status: response.status }
      )
    }

    return NextResponse.json(
      { message: 'Store removed from dibs' },
      { status: 200 }
    )
  } catch (error) {
    console.error('Error removing store from dibs:', error)
    return NextResponse.json(
      { message: 'Internal Server Error' },
      { status: 500 }
    )
  }
}
