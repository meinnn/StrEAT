import { NextResponse } from 'next/server'

// eslint-disable-next-line import/prefer-default-export
export async function GET() {
  try {
    // 외부 API에 GET 요청을 보냅니다.
    const response = await fetch(
      'https://j11a307.p.ssafy.io/api/products/categories',
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      }
    )

    if (!response.ok) {
      // 응답이 실패한 경우 에러 처리
      return NextResponse.json(
        { message: 'Failed to fetch categories' },
        { status: response.status }
      )
    }

    const data = await response.json()

    // 성공적으로 데이터를 받아왔을 때 응답 반환
    return NextResponse.json(data)
  } catch (error) {
    // 예외가 발생한 경우 에러 처리
    return NextResponse.json(
      { message: 'An error occurred while fetching categories', error },
      { status: 500 }
    )
  }
}
