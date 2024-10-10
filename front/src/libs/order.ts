/* eslint-disable import/prefer-default-export */

// 태그, 날짜에 따른 주문 내역 검색 API - POST - 완료
// /services/order/[id]/search
export const searchOrderList = async (
  token: string,
  payload: {
    storeId: string
    body: {
      startDate: string
      endDate: string
      statusTag: string[]
      paymentMethodTag: string[]
    }
  }
) => {
  const { storeId, body } = payload

  console.log('storeId:', storeId)

  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/orders/order-request/${storeId}/search`,
    {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify({ ...body, pgno: 0, spp: 10 }),
      cache: 'no-cache',
    }
  )

  if (!response.ok) {
    const errorMessage = await response.json()
    throw new Error(
      JSON.stringify({ message: errorMessage.message, status: response.status })
    )
  }

  return response
}

// 점포별 주문내역 조회 API - GET - 완료
// /services/order/[id]/list
export const fetchStoreOrderList = async (
  token: string,
  {
    storeId,
    pgno,
    spp,
    status,
  }: {
    storeId: string
    pgno: number
    spp: number
    status: string
  }
) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/orders/order-request/${storeId}/list?pgno=${pgno}&spp=${spp}&status=${status}`,
    {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      cache: 'no-cache',
    }
  )

  if (!response.ok) {
    const errorMessage = await response.json()
    throw new Error(
      JSON.stringify({ message: errorMessage.message, status: response.status })
    )
  }

  return response
}

// 전체 대기 팀/메뉴 조회 API - GET
// /services/order/[id]/list/waiting
export const fetchTotalWaitng = async (token: string) => {
  const storeId = 60
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/orders/order-request/${storeId}/list/waiting`,
    {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      cache: 'no-cache',
    }
  )

  if (!response.ok) {
    const errorMessage = await response.json()
    throw new Error(
      JSON.stringify({ message: errorMessage.message, status: response.status })
    )
  }

  return response
}

// 주문 승인/거절 API - GET - 완료
// 거절 시 0, 승인 시 1
// /services/order/[id]/handle
export const postOrderStatus = async (
  token: string,
  {
    orderId,
    flag,
  }: {
    orderId: string
    flag: number
  }
) => {
  console.log('orderId flag:', orderId, flag)
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/orders/order-request/${orderId}/handle?flag=${flag}`,
    {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      cache: 'no-cache',
    }
  )

  if (!response.ok) {
    const errorMessage = await response.json()
    throw new Error(
      JSON.stringify({ message: errorMessage.message, status: response.status })
    )
  }

  return response
}

// 조리 완료 API - GET - 완료
// /services/order/[id]/handle/complete
export const postOrderComplete = async (token: string, ordersId: string) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/orders/order-request/${ordersId}/handle/complete`,
    {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      cache: 'no-cache',
    }
  )

  if (!response.ok) {
    const errorMessage = await response.json()
    throw new Error(
      JSON.stringify({ message: errorMessage.message, status: response.status })
    )
  }

  return response
}

// 내 주문내역 리스트 조회 API - GET
// /services/order/mine/list
export const fetchMyOrderList = async (token: string) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/orders/order-request/mine/list`,
    {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      cache: 'no-store',
    }
  )

  if (!response.ok) {
    const errorMessage = await response.json()
    throw new Error(
      JSON.stringify({ message: errorMessage.message, status: response.status })
    )
  }

  return response
}

// 진행중인 내 주문 내역 조회 API - GET
// /services/order/mine/list/ongoing
export const fetchProcessingMyOrderList = async (token: string) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/orders/order-request/mine/list/ongoing`,
    {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      cache: 'no-store',
    }
  )

  if (!response.ok) {
    const errorMessage = await response.json()
    throw new Error(
      JSON.stringify({ message: errorMessage.message, status: response.status })
    )
  }

  return response
}

// 주문 내역 상세조회 API - GET
// /services/order/info/[id]
export const fetchOrderDetail = async (token: string, payload: string) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/orders/order-request/info/${payload}`,
    {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      cache: 'no-cache',
    }
  )

  if (!response.ok) {
    const errorMessage = await response.json()
    throw new Error(
      JSON.stringify({ message: errorMessage.message, status: response.status })
    )
  }

  return response
}
