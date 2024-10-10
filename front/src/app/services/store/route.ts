/* eslint-disable import/prefer-default-export */
import { NextResponse, NextRequest } from 'next/server'
import { cookies } from 'next/headers'
import { postStoreBusinessDays, postStoreInfo } from '@/libs/store'

async function getAccessToken() {
  const cookieStore = cookies()
  return cookieStore.get('accessToken')?.value // 쿠키에서 accessToken 가져오기
}

export async function POST(request: NextRequest) {
  try {
    const token = await getAccessToken()
    if (!token) {
      return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
    }

    const body = await request.json()

    // 점포 정보 저장하는 API
    // 점포 영업일 및 영업시간 저장하는 API
    const storeInfoResponse = await postStoreInfo(token, body.storeInfo)
    const storeBusinessDaysResponse = await postStoreBusinessDays(
      token,
      body.businessDays
    )

    if (!storeInfoResponse.ok) {
      const errorMessage = await storeInfoResponse.text()
      return NextResponse.json(
        { error: errorMessage },
        { status: storeInfoResponse.status }
      )
    }

    if (!storeBusinessDaysResponse.ok) {
      const errorMessage = await storeBusinessDaysResponse.text()
      return NextResponse.json(
        { error: errorMessage },
        { status: storeBusinessDaysResponse.status }
      )
    }

    const parsedData = {
      message: '가게 생성 성공',
    }

    return NextResponse.json(parsedData)
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
