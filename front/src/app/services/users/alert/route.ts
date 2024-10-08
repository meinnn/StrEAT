import { NextRequest, NextResponse } from 'next/server'
import { cookies } from 'next/headers'

// eslint-disable-next-line import/prefer-default-export
export async function POST(req: NextRequest) {
  try {
    const { alertOn, alertType } = await req.json()

    // alertType에 따라 API URL을 결정
    let apiUrl = ''
    if (alertType === 'dibs-store-alert') {
      apiUrl = 'https://j11a307.p.ssafy.io/api/users/dibs-store-alert'
    } else if (alertType === 'order-status-alert') {
      apiUrl = 'https://j11a307.p.ssafy.io/api/users/order-status-alert'
    } else {
      return NextResponse.json(
        { error: '잘못된 알림 유형입니다' },
        { status: 400 }
      )
    }

    const cookieStore = cookies()
    const token = cookieStore.get('accessToken')?.value

    // 결정된 API로 POST 요청 전송
    const response = await fetch(`${apiUrl}?alertOn=${alertOn}`, {
      method: 'POST',
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })

    // fetch 요청이 성공하지 않은 경우 오류 처리
    if (!response.ok) {
      return NextResponse.json(
        { error: '알림 요청을 보내는 데 실패했습니다' },
        { status: response.status }
      )
    }

    // 성공적으로 알림 상태 업데이트
    return NextResponse.json({
      message: '알림 상태가 성공적으로 업데이트되었습니다',
    })
  } catch (error: any) {
    return NextResponse.json({ error: error.message }, { status: 500 })
  }
}
