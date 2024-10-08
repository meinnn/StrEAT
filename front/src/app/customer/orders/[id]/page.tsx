import AppBar from '@/components/AppBar'
import OrderProgress from '@/containers/customer/orders/OrderProgress'
import OrderSummary from '@/containers/customer/orders/OrderSummary'
import { cookies } from 'next/headers'
import { OrderDetailData } from '@/types/order'

async function fetchOrderDetail(id: string): Promise<OrderDetailData> {
  const cookieStore = cookies()
  const token = cookieStore.get('accessToken')?.value

  const response = await fetch(
    `https://j11a307.p.ssafy.io/api/orders/order-request/info/${id}`,
    {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }
  )

  if (!response.ok) {
    throw new Error('Failed to fetch order detail')
  }

  const result = await response.json()
  return result.data
}

export default async function OrderDetail({
  params,
}: {
  params: { id: string }
}) {
  const orderDetail = await fetchOrderDetail(params.id)

  return (
    <>
      <AppBar title="주문 상세" />
      <div className="divide-y-4 divide-gray-medium">
        <OrderProgress
          orderStatus={orderDetail.status}
          waitingStatus={{
            team: orderDetail.waitingTeam,
            menu: orderDetail.waitingTeam,
          }}
        />
        <OrderSummary orderDetail={orderDetail} />
      </div>
    </>
  )
}
