/* eslint-disable import/prefer-default-export */
import { NextResponse, NextRequest } from 'next/server'
import { cookies } from 'next/headers'
import { postAnnouncementFile } from '@/libs/announcement'

export const dynamic = 'force-dynamic' // 강제로 동적 렌더링

async function getAccessToken() {
  const cookieStore = cookies()
  return cookieStore.get('accessToken')?.value // 쿠키에서 accessToken 가져오기
}
/* 푸드트럭 공고 제출 파일 자동화 API */
export async function POST(req: NextRequest) {
  try {
    const token = await getAccessToken()
    if (!token) {
      return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
    }
    const body = await req.json()

    const announcementFileResponse = await postAnnouncementFile(token, body)

    if (!announcementFileResponse.ok) {
      const errorMessage = await announcementFileResponse.text()
      return NextResponse.json(
        { error: errorMessage },
        { status: announcementFileResponse.status }
      )
    }

    const announcementFileData = await announcementFileResponse.blob()

    return new Response(announcementFileData, {
      headers: {
        'Content-Type': 'application/octet-stream',
        'Content-Disposition': `attachment; filename="${encodeURIComponent('입점신청서.docx')}"`,
      },
    })
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
