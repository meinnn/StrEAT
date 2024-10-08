import { NextResponse } from 'next/server'
import { cookies } from 'next/headers'

// eslint-disable-next-line import/prefer-default-export
export async function GET(request: Request) {
  const { searchParams } = new URL(request.url)

  const cookieStore = cookies()
  const token = cookieStore.get('accessToken')?.value // 쿠키에서 access_token 가져오기

  if (!token) {
    return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
  }

  const pgno = searchParams.get('pgno') ?? '0'
  const spp = searchParams.get('spp') ?? '10'

  try {
    // 장바구니 목록을 가져오는 API 요청
    const response = await fetch(
      `https://j11a307.p.ssafy.io/api/orders/basket/list?pgno=${pgno}&spp=${spp}`,
      {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )

    if (response.status === 404) {
      // 장바구니 빔
      return NextResponse.json({}, { status: 200 })
    }

    if (!response.ok) {
      return NextResponse.json(
        { message: 'Failed to fetch basket list' },
        { status: response.status }
      )
    }

    const data = await response.json()
    return NextResponse.json(data, { status: 200 })
  } catch (error) {
    console.error('Error fetching basket list:', error)
    return NextResponse.json(
      { message: 'Internal Server Error' },
      { status: 500 }
    )
  }
}
