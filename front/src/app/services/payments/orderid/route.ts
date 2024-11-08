import { NextResponse } from 'next/server'
import { cookies } from 'next/headers'

// eslint-disable-next-line import/prefer-default-export
export async function POST(req: Request) {
  const { storeId, totalPrice, orderProducts } = await req.json() // 요청 본문에서 필요한 데이터 추출
  const cookieStore = cookies()
  const token = cookieStore.get('accessToken')?.value // 쿠키에서 access_token 가져오기

  const response = await fetch(
    `https://j11a307.p.ssafy.io/api/orders/order-request/${storeId}`,
    {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${token}`, // 필요시 Authorization 헤더 추가
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        totalPrice,
        orderProducts,
      }),
    }
  )

  if (!response.ok) {
    return NextResponse.json({ message: 'Order ID 요청 실패' }, { status: 500 })
  }

  const result = await response.json()
  const orderId = result.data

  return NextResponse.json({
    orderId, // API에서 받은 orderId 반환
    message: 'Order ID 발급 성공',
  })
}
