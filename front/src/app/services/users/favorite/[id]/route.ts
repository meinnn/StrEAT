/* eslint-disable import/prefer-default-export */
import { NextResponse, NextRequest } from 'next/server'
import { cookies } from 'next/headers'
import {
  fetchFavoriteList,
  postAddFavoriteStore,
  postDeleteFavoriteStore,
} from '@/libs/user'

async function getAccessToken() {
  const cookieStore = cookies()
  return cookieStore.get('accessToken')?.value // 쿠키에서 accessToken 가져오기
}

// 찜 목록 조회 API
export async function GET(
  req: NextRequest,
  { params }: { params: { id: string } }
) {
  try {
    const token = await getAccessToken()
    if (!token) {
      return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
    }

    const favoriteListResponse = await fetchFavoriteList(token)

    if (!favoriteListResponse.ok) {
      const errorMessage = await favoriteListResponse.text()
      return NextResponse.json(
        { error: errorMessage },
        { status: favoriteListResponse.status }
      )
    }

    const favoriteListData = await favoriteListResponse.json()

    return NextResponse.json(favoriteListData.data)
  } catch (error: unknown) {
    let errorMessage = '서버 에러가 발생했습니다.'
    const errorStatus = 500

    if (error instanceof Error) {
      errorMessage = error.message || errorMessage
    } else if (typeof error === 'string') {
      errorMessage = error
    } else if (typeof error === 'object' && error !== null) {
      errorMessage = JSON.stringify(error)
    }

    return NextResponse.json({ error: errorMessage }, { status: errorStatus })
  }
}

// 찜 목록에 추가하는 API
export async function POST(
  req: NextRequest,
  { params }: { params: { id: string } }
) {
  try {
    const storeId = params.id
    const token = await getAccessToken()
    if (!token) {
      return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
    }

    const favoriteListResponse = await postAddFavoriteStore(token, storeId)

    if (!favoriteListResponse.ok) {
      const errorMessage = await favoriteListResponse.text()
      return NextResponse.json(
        { error: errorMessage },
        { status: favoriteListResponse.status }
      )
    }

    const favoriteListData = await favoriteListResponse.json()

    return NextResponse.json(favoriteListData.data)
  } catch (error: unknown) {
    let errorMessage = '서버 에러가 발생했습니다.'
    const errorStatus = 500

    if (error instanceof Error) {
      errorMessage = error.message || errorMessage
    } else if (typeof error === 'string') {
      errorMessage = error
    } else if (typeof error === 'object' && error !== null) {
      errorMessage = JSON.stringify(error)
    }

    return NextResponse.json({ error: errorMessage }, { status: errorStatus })
  }
}

// 찜 목록에서 삭제하는 API
export async function DELETE(
  req: NextRequest,
  { params }: { params: { id: string } }
) {
  try {
    const storeId = params.id

    const token = await getAccessToken()
    if (!token) {
      return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
    }

    const favoriteListResponse = await postDeleteFavoriteStore(token, storeId)

    if (!favoriteListResponse.ok) {
      const errorMessage = await favoriteListResponse.text()
      return NextResponse.json(
        { error: errorMessage },
        { status: favoriteListResponse.status }
      )
    }

    return NextResponse.json(
      { message: '찜 취소 성공', ok: true },
      { status: 200 }
    )
  } catch (error: unknown) {
    let errorMessage = '서버 에러가 발생했습니다.'
    const errorStatus = 500

    if (error instanceof Error) {
      errorMessage = error.message || errorMessage
    } else if (typeof error === 'string') {
      errorMessage = error
    } else if (typeof error === 'object' && error !== null) {
      errorMessage = JSON.stringify(error)
    }

    return NextResponse.json({ error: errorMessage }, { status: errorStatus })
  }
}
