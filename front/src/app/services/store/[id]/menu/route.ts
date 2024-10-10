/* eslint-disable import/prefer-default-export */
import { NextResponse, NextRequest } from 'next/server'
import { fetchStoreMenuList } from '@/libs/product'

// 가게의 모든 메뉴 조회
export async function GET(
  req: Request,
  { params }: { params: { id: string } }
) {
  try {
    const storeId = params.id

    const storeMenuListResponse = await fetchStoreMenuList(storeId)

    if (!storeMenuListResponse.ok) {
      const errorMessage = await storeMenuListResponse.text()
      return NextResponse.json(
        { error: errorMessage },
        { status: storeMenuListResponse.status }
      )
    }

    const storeMenuListData = await storeMenuListResponse.json()

    return NextResponse.json(storeMenuListData.data)
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

    return NextResponse.json({ error: errorMessage }, { status: errorStatus })
  }
}
