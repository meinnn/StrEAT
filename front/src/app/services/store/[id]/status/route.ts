/* eslint-disable import/prefer-default-export */
import { NextResponse, NextRequest } from 'next/server'
import { updateStoreStatus } from '@/libs/store'

/* 가게 영업 상태 바꾸는 API  */
export async function PATCH(
  req: NextRequest,
  { params }: { params: { id: string } }
) {
  try {
    const storeId = params.id
    const { status } = await req.json()

    const storeStatusResponse = await updateStoreStatus({
      storeId,
      status,
    })

    if (!storeStatusResponse.ok) {
      const errorMessage = await storeStatusResponse.text()
      return NextResponse.json(
        { error: errorMessage },
        { status: storeStatusResponse.status }
      )
    }

    const storeStatusData = await storeStatusResponse.json()

    return NextResponse.json(storeStatusData)
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
