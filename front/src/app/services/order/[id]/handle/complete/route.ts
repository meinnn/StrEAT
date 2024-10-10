/* eslint-disable import/prefer-default-export */
import { NextResponse, NextRequest } from 'next/server'
import { cookies } from 'next/headers'
import { postOrderComplete } from '@/libs/order'

async function getAccessToken() {
  const cookieStore = cookies()
  return cookieStore.get('accessToken')?.value // 쿠키에서 accessToken 가져오기
}

/* 조리 완료 API */
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

    const orderCompleteResponse = await postOrderComplete(token, orderId)

    if (!orderCompleteResponse.ok) {
      const errorMessage = await orderCompleteResponse.text()
      return NextResponse.json(
        { error: errorMessage },
        { status: orderCompleteResponse.status }
      )
    }

    const orderCompleteData = await orderCompleteResponse.json()

    return NextResponse.json(orderCompleteData)
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
