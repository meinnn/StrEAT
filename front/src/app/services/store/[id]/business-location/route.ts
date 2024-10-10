/* eslint-disable import/prefer-default-export */
import { NextResponse, NextRequest } from 'next/server'
import { cookies } from 'next/headers'
import {
  deleteStoreBusinessLocation,
  fetchStoreLocation,
  postStoreBusinessLocation,
} from '@/libs/store'

async function getAccessToken() {
  const cookieStore = cookies()
  return cookieStore.get('accessToken')?.value // 쿠키에서 accessToken 가져오기
}

/* 점포 영업 위치 리스트 조회 */
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

    // 점포 영업 위치 조회하는  API
    const storeLocationResponse = await fetchStoreLocation(token, storeId)

    if (!storeLocationResponse.ok) {
      const errorMessage = await storeLocationResponse.text()
      return NextResponse.json(
        { error: errorMessage },
        { status: storeLocationResponse.status }
      )
    }

    const storeLocationData = await storeLocationResponse.json()

    return NextResponse.json(storeLocationData.data)
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

/* 점포 영업 장소 위치 저장  */
export async function POST(req: NextRequest) {
  try {
    const token = await getAccessToken()
    if (!token) {
      return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
    }

    const formData = await req.formData()

    const storeBusinessLocationResponse = await postStoreBusinessLocation(
      token,
      formData
    )

    if (!storeBusinessLocationResponse.ok) {
      const errorMessage = await storeBusinessLocationResponse.text()
      return NextResponse.json(
        { error: errorMessage },
        { status: storeBusinessLocationResponse.status }
      )
    }

    const storeBusinessLocationData = await storeBusinessLocationResponse.json()

    console.log('storeBusinessLocationData', storeBusinessLocationData)

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
/* 점포 영업 장소 위치 저장  */
export async function DELETE(req: NextRequest) {
  try {
    const token = await getAccessToken()
    if (!token) {
      return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
    }

    const locationIdList = await req.json()

    const storeBusinessLocationResponse = await deleteStoreBusinessLocation(
      token,
      locationIdList
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
