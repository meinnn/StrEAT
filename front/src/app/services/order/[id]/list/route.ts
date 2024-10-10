/* eslint-disable import/prefer-default-export */
import { NextResponse, NextRequest } from 'next/server'
import { fetchStoreOrderList } from '@/libs/order'

// 점포별 주문내역 조회 API (PROCESSING/RECEIVING)
export async function GET(
  req: NextRequest,
  { params }: { params: { id: string } }
) {
  try {
    const storeId = params.id
    const { searchParams } = req.nextUrl
    const pgno = Number(searchParams.get('page') ?? '0')
    const spp = Number(searchParams.get('spp') ?? '10')
    const status = searchParams.get('status') ?? 'PROCESSING'

    // 점포별 주문내역 조회하는 API
    const storeOrderListResponse = await fetchStoreOrderList({
      storeId,
      pgno,
      spp,
      status,
    })

    if (!storeOrderListResponse.ok) {
      const errorMessage = await storeOrderListResponse.text()
      return NextResponse.json(
        { error: errorMessage },
        { status: storeOrderListResponse.status }
      )
    }

    const storeOrderListData = await storeOrderListResponse.json()

    return NextResponse.json(storeOrderListData.data)
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

    throw new Error('Unknown error occurred')
    // return NextResponse.json({ error: errorMessage }, { status: errorStatus })
  }
}
