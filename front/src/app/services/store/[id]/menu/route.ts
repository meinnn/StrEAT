/* eslint-disable import/prefer-default-export */
import { NextResponse, NextRequest } from 'next/server'
import { cookies } from 'next/headers'
import { fetchStoreMenuList } from '@/libs/product'

async function getAccessToken() {
  const cookieStore = cookies()
  return cookieStore.get('accessToken')?.value // 쿠키에서 accessToken 가져오기
}

// 가게의 모든 메뉴 조회
export async function GET(
  req: Request,
  { params }: { params: { id: string } }
) {
  try {
    const token = await getAccessToken()
    if (!token) {
      return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
    }

    const storeId = params.id

    const storeMenuListResponse = await fetchStoreMenuList(token, storeId)

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
