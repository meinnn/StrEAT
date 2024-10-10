/* eslint-disable import/prefer-default-export */
import { headers } from 'next/headers'
import { NextResponse } from 'next/server'
import { NextRequest } from 'next/server'
import { updateStoreBusinessHours } from '@/libs/store'

export async function GET(
  request: NextRequest,
  { params }: { params: { id: string } }
): Promise<NextResponse> {
  try {
    const storeId = params.id

    // 점포 영업일 및 영업시간 가져오는 api
    const storeBusinessDaysResponse = await fetch(
      `${process.env.NEXT_PUBLIC_BACK_URL}/api/stores/business-days/${storeId}`,
      {
        headers: {
          Authorization:
            'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhY2Nlc3MtdG9rZW4iLCJpYXQiOjE3Mjc4MzE0MTQsImV4cCI6MjA4NzgzMTQxNCwidXNlcklkIjoxMn0.UrVrI-WUCXdx017R4uRIl6lzxbktVSfEDjEgYe5J8UQ',
        },
      }
    )

    if (!storeBusinessDaysResponse.ok) {
      const errorMessage = await storeBusinessDaysResponse.text()
      console.error('Error Response from Get Store BusinessDays:', errorMessage)
      return NextResponse.json(
        { error: errorMessage },
        { status: storeBusinessDaysResponse.status }
      )
    }
    const storeBusinessDaysData = await storeBusinessDaysResponse.json()

    return NextResponse.json(storeBusinessDaysData.data)
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

/* 영업일 및 영업시간 바꾸는 API  */
export async function PUT(
  req: NextRequest,
  { params }: { params: { id: string } }
) {
  try {
    const body = await req.json()

    const storeBusinessHoursResponse = await updateStoreBusinessHours(body)

    if (!storeBusinessHoursResponse.ok) {
      const errorMessage = await storeBusinessHoursResponse.text()
      return NextResponse.json(
        { error: errorMessage },
        { status: storeBusinessHoursResponse.status }
      )
    }

    const storeBusinessHoursData = await storeBusinessHoursResponse.json()

    return NextResponse.json(storeBusinessHoursData)
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
