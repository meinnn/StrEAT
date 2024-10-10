/* eslint-disable import/prefer-default-export */
import { NextResponse, NextRequest } from 'next/server'
import { cookies } from 'next/headers'
import { updateStoreOwnerWord } from '@/libs/store'

async function getAccessToken() {
  const cookieStore = cookies()
  return cookieStore.get('accessToken')?.value // 쿠키에서 accessToken 가져오기
}

/* 사장님 한마디 바꾸는 API  */
export async function PATCH(req: NextRequest) {
  try {
    const token = await getAccessToken()
    if (!token) {
      return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
    }

    const body = await req.json()

    const storeOwnerWordResponse = await updateStoreOwnerWord(token, body)

    if (!storeOwnerWordResponse.ok) {
      const errorMessage = await storeOwnerWordResponse.text()
      return NextResponse.json(
        { error: errorMessage },
        { status: storeOwnerWordResponse.status }
      )
    }

    const storeOwnerWordData = await storeOwnerWordResponse.json()

    return NextResponse.json(storeOwnerWordData)
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
