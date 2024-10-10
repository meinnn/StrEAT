/* eslint-disable import/prefer-default-export */
import { NextResponse, NextRequest } from 'next/server'
import { postStoreBusinessDays, postStoreInfo } from '@/libs/store'

export async function POST(request: NextRequest) {
  try {
    const body = await request.json()

    // 점포 정보 저장하는 API
    // 점포 영업일 및 영업시간 저장하는 API
    const storeInfoResponse = await postStoreInfo(body.storeInfo)
    const storeBusinessDaysResponse = await postStoreBusinessDays(
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
