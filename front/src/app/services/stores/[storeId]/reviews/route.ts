import { NextResponse } from 'next/server'
import { cookies } from 'next/headers'

// eslint-disable-next-line import/prefer-default-export
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
      `https://j11a307.p.ssafy.io/api/orders/stores/${params.storeId}/reviews/summary`,
      {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )

    if (response.status === 404) {
      return NextResponse.json(
        { message: '점포 존재하지 않음', data: null },
        { status: 404 }
      )
    }

    if (!response.ok) {
      return NextResponse.json(
        { message: 'Failed to fetch store reviews' },
        { status: response.status }
      )
    }

    const data = await response.json()
    return NextResponse.json(
      { message: '점포별 리뷰 조회 성공', data },
      { status: 200 }
    )
  } catch (error) {
    console.error('Error fetching store reviews:', error)
    return NextResponse.json(
      { message: 'Internal Server Error' },
      { status: 500 }
    )
  }
}
