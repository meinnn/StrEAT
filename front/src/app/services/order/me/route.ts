/* eslint-disable import/prefer-default-export */
import { NextRequest, NextResponse } from 'next/server'
import { cookies } from 'next/headers'

export const dynamic = 'force-dynamic' // 강제로 동적 렌더링

async function getAccessToken() {
  const cookieStore = cookies()
  return cookieStore.get('accessToken')?.value // 쿠키에서 accessToken 가져오기
}

export async function GET(req: NextRequest): Promise<NextResponse> {
  try {
    const token = await getAccessToken()
    if (!token) {
      return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
    }

    const { searchParams } = req.nextUrl
    const page = Number(searchParams.get('page') ?? '0')
    const limit = Number(searchParams.get('limit') ?? '5')

    const response = await fetch(
      `${process.env.NEXT_PUBLIC_BACK_URL}/api/orders/order-request/mine/list?pgno=${page}&spp=${limit}`,
      {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${token}`,
        },
        cache: 'no-store',
      }
    )

    if (!response.ok) {
      const errorData = await response.text()
      console.error('Error Response:', errorData)
      return NextResponse.json(
        { message: 'Error occurred while fetching orders', error: errorData },
        { status: response.status }
      )
    }

    const data = await response.json()

    const hasMore = data.data.totalPageCount > page + 1
    return NextResponse.json({ data: data.data, hasMore })
  } catch (error: unknown) {
    let errorMessage = '에러가 발생했습니다'

    if (error instanceof Error) {
      errorMessage = error.message
    } else if (typeof error === 'string') {
      errorMessage = error
    } else if (typeof error === 'object' && error !== null) {
      errorMessage = JSON.stringify(error)
    }

    console.error('에러:', errorMessage)
    return NextResponse.json({ error: errorMessage }, { status: 500 })
  }
}
