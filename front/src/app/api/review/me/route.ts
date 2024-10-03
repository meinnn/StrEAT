/* eslint-disable import/prefer-default-export */
import { NextResponse } from 'next/server'

interface Review {
  reviewId: number
  storeId: number
  storeName: string
  storePhoto: string
  createdAt: string
  orderProducts: string[]
  content: string
}

export async function GET(): Promise<NextResponse> {
  try {
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_BACK_URL}/api/orders/mine/reviews`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization':
            'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhY2Nlc3MtdG9rZW4iLCJpYXQiOjE3Mjc4MzE0MTQsImV4cCI6MjA4NzgzMTQxNCwidXNlcklkIjoxMn0.UrVrI-WUCXdx017R4uRIl6lzxbktVSfEDjEgYe5J8UQ',
        },
      }
    )

    if (!response.ok) {
      const errorData = await response.text()
      console.error('Error Response:', errorData)
      return NextResponse.json(
        { message: 'Error occurred while submitting review', error: errorData },
        { status: response.status }
      )
    }

    const data: {
      message: string
      data: Review[]
    } = await response.json()
    return NextResponse.json(data.data)
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
