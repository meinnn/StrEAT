/* eslint-disable import/prefer-default-export */
import { NextResponse, NextRequest } from 'next/server'
import { cookies } from 'next/headers'
import { pickUpFood } from '@/libs/store'

async function getAccessToken() {
  const cookieStore = cookies()
  return cookieStore.get('accessToken')?.value
}

export async function POST(req: NextRequest) {
  try {
    const token = await getAccessToken()

    console.log('토큰:', token)
    if (!token) {
      return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
    }

    console.log('음식수령 token:', token)

    // 점포별 주문내역 조회하는 API
    const pickUpResponse = await pickUpFood(token)

    console.log('pickUpResponse:', pickUpResponse)

    if (!pickUpResponse.ok) {
      const errorMessage = await pickUpResponse.text()
      return NextResponse.json(
        { error: errorMessage },
        { status: pickUpResponse.status }
      )
    }

    const pickUpData = await pickUpResponse.json()

    return NextResponse.json(pickUpData.data)
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
