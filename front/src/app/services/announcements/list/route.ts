/* eslint-disable import/prefer-default-export */
import { NextResponse, NextRequest } from 'next/server'
import { cookies } from 'next/headers'
import { fetchAnnouncementList } from '@/libs/announcement'

export const dynamic = 'force-dynamic' // 강제로 동적 렌더링

async function getAccessToken() {
  const cookieStore = cookies()
  return cookieStore.get('accessToken')?.value // 쿠키에서 accessToken 가져오기
}
/* 푸드트럭 공고 내역 조회 API */
export async function GET(
  req: NextRequest,
  { params }: { params: { id: string } }
) {
  try {
    const token = await getAccessToken()
    if (!token) {
      return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
    }
    console.log(token)

    if (!token) {
      return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
    }

    const announcementListResponse = await fetchAnnouncementList(token)

    if (!announcementListResponse.ok) {
      const errorMessage = await announcementListResponse.text()
      return NextResponse.json(
        { error: errorMessage },
        { status: announcementListResponse.status }
      )
    }

    const announcementListData = await announcementListResponse.json()

    return NextResponse.json(announcementListData.data)
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
