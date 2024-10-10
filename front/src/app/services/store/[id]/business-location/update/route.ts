/* eslint-disable import/prefer-default-export */
import { NextResponse, NextRequest } from 'next/server'
import { cookies } from 'next/headers'
import { updateStoreBusinessLocation } from '@/libs/store'

async function getAccessToken() {
  const cookieStore = cookies()
  return cookieStore.get('accessToken')?.value // 쿠키에서 accessToken 가져오기
}

/* 이동형 가게가 간편 위치 저장 목록에서 선택한 후에 영업을 시작할 때 가게 위치 정보를 업데이트하는 API  */
export async function PATCH(req: NextRequest) {
  try {
    const token = await getAccessToken()
    if (!token) {
      return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
    }

    const { locationId } = await req.json()

    const storeBusinessLocationResponse = await updateStoreBusinessLocation(
      token,
      locationId
    )

    if (!storeBusinessLocationResponse.ok) {
      const errorMessage = await storeBusinessLocationResponse.text()
      return NextResponse.json(
        { error: errorMessage },
        { status: storeBusinessLocationResponse.status }
      )
    }

    const storeBusinessLocationData = await storeBusinessLocationResponse.json()

    return NextResponse.json(storeBusinessLocationData)
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
