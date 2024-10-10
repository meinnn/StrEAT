/* eslint-disable import/prefer-default-export */
import { NextResponse } from 'next/server'
import { NextRequest } from 'next/server'

export async function GET(
  request: NextRequest,
  { params }: { params: { id: string } }
): Promise<NextResponse> {
  try {
    const storeId = params.id

    // 점포 이미지 가져오는 api
    // 점포 정보 가져오는 api
    // 점포 영업일 및 영업시간 가져오는 api
    // 점포 영업 위치 정보 가져오는 api
    // 점포 리뷰 개수 및 평점 조회하는 api
    const [
      storeImageResponse,
      storeInfoResponse,
      storeBusinessDaysResponse,
      storeLocationsResponse,
      storeReviewCountAverageResponse,
    ] = await Promise.all([
      fetch(
        `${process.env.NEXT_PUBLIC_BACK_URL}/api/stores/store-photos/photo/${storeId}`,
        { cache: 'no-cache' }
      ),
      fetch(`${process.env.NEXT_PUBLIC_BACK_URL}/api/stores/${storeId}`, {
        cache: 'no-cache',
      }),
      fetch(
        `${process.env.NEXT_PUBLIC_BACK_URL}/api/stores/business-days/${storeId}`,
        { cache: 'no-cache' }
      ),
      fetch(
        `${process.env.NEXT_PUBLIC_BACK_URL}/api/stores/store-location-photos/${storeId}`,
        { cache: 'no-cache' }
      ),
      fetch(
        `${process.env.NEXT_PUBLIC_BACK_URL}/api/orders/stores/${storeId}/reviews/summary`,
        { cache: 'no-cache' }
      ),
    ])

    if (!storeImageResponse.ok) {
      const errorMessage = await storeImageResponse.text()
      console.error('Error Response from Get Store Image:', errorMessage)
      return NextResponse.json(
        { error: errorMessage },
        { status: storeImageResponse.status }
      )
    }

    if (!storeInfoResponse.ok) {
      const errorMessage = await storeInfoResponse.text()
      console.error('Error Response from Get Store Info:', errorMessage)
      return NextResponse.json(
        { error: errorMessage },
        { status: storeInfoResponse.status }
      )
    }

    if (!storeBusinessDaysResponse.ok) {
      const errorMessage = await storeBusinessDaysResponse.text()
      console.error('Error Response from Get Store BusinessDays:', errorMessage)
      return NextResponse.json(
        { error: errorMessage },
        { status: storeBusinessDaysResponse.status }
      )
    }

    if (!storeLocationsResponse.ok) {
      const errorMessage = await storeLocationsResponse.text()
      console.error('Error Response from Get Store Location:', errorMessage)
      return NextResponse.json(
        { error: errorMessage },
        { status: storeLocationsResponse.status }
      )
    }

    if (!storeReviewCountAverageResponse.ok) {
      const errorMessage = await storeReviewCountAverageResponse.text()
      console.error(
        'Error Response from Get Store Review Count And Average:',
        errorMessage
      )
      return NextResponse.json(
        { error: errorMessage },
        { status: storeReviewCountAverageResponse.status }
      )
    }

    const storeImageData = await storeImageResponse.json()
    const storeInfoData = await storeInfoResponse.json()
    const storeBusinessDaysData = await storeBusinessDaysResponse.json()
    const storeLocationsData = await storeLocationsResponse.json()
    const storeReviewCountAverageData =
      await storeReviewCountAverageResponse.json()

    const combinedData = {
      storeImage: [...storeImageData.data],
      storeInfo: { ...storeInfoData.data },
      storeBusinessDays: { ...storeBusinessDaysData.data },
      storeLocations: [...storeLocationsData.data],
      storeReview: { ...storeReviewCountAverageData.data },
    }

    return NextResponse.json(combinedData)
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
