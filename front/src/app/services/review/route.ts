/* eslint-disable import/prefer-default-export */
import { NextResponse, NextRequest } from 'next/server'

export async function POST(req: NextRequest) {
  try {
    const { searchParams } = req.nextUrl
    const orderId = searchParams.get('orderId')

    const formData = await req.formData()
    const formEntries = Array.from(formData.entries())
    formEntries.forEach(([key, value]) => {
      console.log(key, value)
    })

    const newFormData = new FormData()

    formEntries.forEach(([key, value]) => {
      if (value instanceof File) {
        newFormData.append(key, value, value.name)
      } else {
        newFormData.append(key, value)
      }
    })

    const response = await fetch(
      `${process.env.NEXT_PUBLIC_BACK_URL}/api/orders/${orderId}/review`,
      {
        method: 'POST',
        body: newFormData,
        headers: {
          Authorization:
            'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhY2Nlc3MtdG9rZW4iLCJpYXQiOjE3Mjc4MzE0MTQsImV4cCI6MjA4NzgzMTQxNCwidXNlcklkIjoxMn0.UrVrI-WUCXdx017R4uRIl6lzxbktVSfEDjEgYe5J8UQ',
        },
      }
    )

    if (!response.ok) {
      const errorMessage = await response.text()
      console.error('Error Response:', errorMessage)
      return NextResponse.json(
        { error: errorMessage },
        { status: response.status }
      )
    }

    const data = await response.json()
    return NextResponse.json(data)
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
