import { NextRequest, NextResponse } from 'next/server'

// eslint-disable-next-line import/prefer-default-export
export async function GET(request: NextRequest) {
  const { searchParams } = request.nextUrl
  const query = searchParams.get('query')

  const response = await fetch(
    `https://openapi.naver.com/v1/search/local.json?query=${query}&display=5`,
    {
      headers: {
        'X-Naver-Client-Id': process.env.NEXT_PUBLIC_NAVER_SEARCH_CLIENT_ID!,
        'X-Naver-Client-Secret':
          process.env.NEXT_PUBLIC_NAVER_SEARCH_CLIENT_SECRET!,
      },
    }
  )

  if (!response.ok) {
    return NextResponse.json(
      { message: 'Error fetching data' },
      { status: response.status }
    )
  }

  const data = await response.json()
  return NextResponse.json(data)
}
