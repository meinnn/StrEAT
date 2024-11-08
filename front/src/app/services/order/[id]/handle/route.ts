/* eslint-disable import/prefer-default-export */
import { NextResponse, NextRequest } from 'next/server'
import { cookies } from 'next/headers'
import { postOrderStatus } from '@/libs/order'

async function getAccessToken() {
  const cookieStore = cookies()
  return cookieStore.get('accessToken')?.value // 쿠키에서 accessToken 가져오기
}

/* 주문 승인/거절 API - flag 값 거절은 0/승인은 1 */
export async function GET(
  req: NextRequest,
  { params }: { params: { id: string } }
) {
  try {
    const token = await getAccessToken()
    if (!token) {
      return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
    }

    const orderId = params.id
    const { searchParams } = req.nextUrl
    const flag = Number(searchParams.get('flag'))

    console.log('flag:', flag)

    // if (flag === null || flag === undefined) {
    //   return NextResponse.json(
    //     { error: '거절/승인 flag가 존재하지 않습니다.' },
    //     { status: 202 }
    //   )
    // }

    const orderStatusResponse = await postOrderStatus(token, {
      orderId,
      flag,
    })

    if (!orderStatusResponse.ok) {
      const errorMessage = await orderStatusResponse.text()
      return NextResponse.json(
        { error: errorMessage },
        { status: orderStatusResponse.status }
      )
    }

    const orderStatusData = await orderStatusResponse.json()

    return NextResponse.json(orderStatusData)
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
