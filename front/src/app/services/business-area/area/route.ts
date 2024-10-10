import { NextResponse } from 'next/server'
import { cookies } from 'next/headers'

// eslint-disable-next-line import/prefer-default-export
export async function GET(request: Request) {
  const { searchParams } = new URL(request.url)
  const latitude = searchParams.get('latitude')
  const longitude = searchParams.get('longitude')

  if (!latitude || !longitude) {
    return NextResponse.json(
      { message: '위도와 경도가 필요합니다.' },
      { status: 400 }
    )
  }

  try {
    // 위도, 경도 로그 출력
    console.log('Latitude:', latitude, 'Longitude:', longitude)

    // 쿠키에서 토큰을 가져옴
    const token = cookies().get('accessToken')?.value
    if (!token) {
      return NextResponse.json(
        { message: '인증 토큰이 없습니다.' },
        { status: 401 }
      )
    }

    // 토큰 로그 출력
    console.log('Token:', token)

    // 외부 API 요청
    const apiResponse = await fetch(
      `https://j11a307.p.ssafy.io/api/orders/order-manage/spot/surround-radius?latitude=${latitude}&longitude=${longitude}`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )

    // 외부 API 응답 상태 로그
    console.log('API Response Status:', apiResponse.status)

    if (!apiResponse.ok) {
      throw new Error(`외부 API 요청 실패, 상태 코드: ${apiResponse.status}`)
    }

    const data = await apiResponse.json()

    // 응답 데이터를 클라이언트에 전달
    return NextResponse.json({
      message: '주변 상권 분석 결과 조회 성공했습니다.',
      data: {
        districtName: data.districtName,
        businessDistrictName: data.businessDistrictName,
        ageGroup: data.ageGroup,
        genderGroup: data.genderGroup,
        dayGroup: data.dayGroup,
        timeGroup: data.timeGroup,
        sameStoreNum: data.sameStoreNum,
      },
    })
  } catch (error) {
    if (error instanceof Error) {
      console.error('Error fetching data:', error.message)
      return NextResponse.json(
        {
          message: '서버에서 데이터를 가져오는 데 실패했습니다.',
          error: error.message,
        },
        { status: 500 }
      )
    }
    console.error('Unknown error:', error)
    return NextResponse.json(
      {
        message: '알 수 없는 오류가 발생했습니다.',
      },
      { status: 500 }
    )
  }
}
