/* eslint-disable import/prefer-default-export */
import { NextResponse, NextRequest } from 'next/server'
import { cookies } from 'next/headers'
import { postStoreImage } from '@/libs/store'

async function getAccessToken() {
  const cookieStore = cookies()
  return cookieStore.get('accessToken')?.value // 쿠키에서 accessToken 가져오기
}

export async function POST(req: NextRequest) {
  try {
    const token = await getAccessToken()
    if (!token) {
      return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
    }

    const formData = await req.formData()

    const formEntries = Array.from(formData.entries())

    const newFormData = new FormData()

    newFormData.append(
      formEntries[0][0],
      formEntries[0][1] as File,
      (formEntries[0][1] as File).name
    )

    newFormData.append(
      formEntries[0][0],
      formEntries[0][1] as File,
      (formEntries[0][1] as File).name
    )

    const storeImageResponse = await postStoreImage(token, newFormData)

    if (!storeImageResponse.ok) {
      const errorMessage = await storeImageResponse.text()
      return NextResponse.json(
        { error: errorMessage },
        { status: storeImageResponse.status }
      )
    }

    const data = await storeImageResponse.json()

    return NextResponse.json(data)
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
