/* eslint-disable import/prefer-default-export */
import { NextResponse, NextRequest } from 'next/server'
import { cookies } from 'next/headers'
import { postStoreBusinessLocation } from '@/libs/store'
import { searchOrderList } from '@/libs/order'

async function getAccessToken() {
  const cookieStore = cookies()
  return cookieStore.get('accessToken')?.value // 쿠키에서 accessToken 가져오기
}

/* 태그, 날짜에 따른 주문 내역 검색  */
export async function POST(
  req: NextRequest,
  { params }: { params: { id: string } }
) {
  try {
    const token = await getAccessToken()
    if (!token) {
      return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
    }

    const storeId = params.id
    const body = await req.json()

    const searchedOrderListResponse = await searchOrderList(token, {
      storeId,
      body,
    })

    if (!searchedOrderListResponse.ok) {
      const errorMessage = await searchedOrderListResponse.text()
      return NextResponse.json(
        { error: errorMessage },
        { status: searchedOrderListResponse.status }
      )
    }

    const searchedOrderListData = await searchedOrderListResponse.json()

    return NextResponse.json(searchedOrderListData.data)
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
