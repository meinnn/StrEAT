/* eslint-disable import/prefer-default-export */
import { NextResponse, NextRequest } from 'next/server'
import { fetchOrderDetail } from '@/libs/order'

// 주문 내역 상세조회 API
export async function GET(
  req: NextRequest,
  { params }: { params: { id: string } }
) {
  try {
    const orderId = params.id

    const storeOrderDetailResponse = await fetchOrderDetail(orderId)

    if (!storeOrderDetailResponse.ok) {
      const errorMessage = await storeOrderDetailResponse.text()
      return NextResponse.json(
        { error: errorMessage },
        { status: storeOrderDetailResponse.status }
      )
    }

    const storeOrderDetailData = await storeOrderDetailResponse.json()

    return NextResponse.json(storeOrderDetailData.data)
  } catch (error: unknown) {
    let errorMessage = '서버 에러가 발생했습니다.'
    let errorStatus = 500

    if (error instanceof Error) {
      const parsedError = JSON.parse(error.message)
      errorMessage = parsedError.message || errorMessage
      errorStatus = parsedError.status || errorStatus
    } else if (typeof error === 'string') {
      errorMessage = error
    } else if (typeof error === 'object' && error !== null) {
      errorMessage = JSON.stringify(error)
    }

    // throw new Error(errorData.error || 'Unknown error occurred');
    return NextResponse.json({ error: errorMessage }, { status: errorStatus })
  }
}
