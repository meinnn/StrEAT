/* eslint-disable import/prefer-default-export */
import { NextResponse, NextRequest } from 'next/server'

// 사업자등록번호 확인 API
export async function POST(req: NextRequest) {
  try {
    const body = await req.json()

    const response = await fetch(
      `http://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=${process.env.NEXT_PUBLIC_BUSINESS_REGISTRATION_NUMBER_SERVICE_KEY}`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
        },
        body: JSON.stringify({
          b_no: [body.b_no],
        }),
      }
    )

    if (!response.ok) {
      return NextResponse.json(
        { message: '사업자등록번호 확인에 실패했습니다' },
        { status: response.status }
      )
    }

    const businessRegistrationNumberData = await response.json()

    return NextResponse.json(businessRegistrationNumberData)
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
