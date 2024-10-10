/* eslint-disable import/prefer-default-export */
import { NextResponse, NextRequest } from 'next/server'
import { fetchAnnouncementList } from '@/libs/announcement'

/* 푸드트럭 공고 내역 조회 API */
export async function GET(
  req: NextRequest,
  { params }: { params: { id: string } }
) {
  try {
    const announcementListResponse = await fetchAnnouncementList()

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
