/* eslint-disable import/prefer-default-export */
import { NextResponse, NextRequest } from 'next/server'
import { postStoreBusinessLocation } from '@/libs/store'
import { postAnnouncementFile } from '@/libs/announcement'

/* 푸드트럭 공고 제출 파일 자동화 API */
export async function POST(req: NextRequest) {
  try {
    const body = await req.json()

    const announcementFileResponse = await postAnnouncementFile(body)

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
