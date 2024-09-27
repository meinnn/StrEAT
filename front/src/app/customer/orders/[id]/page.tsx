import AppBar from '@/components/AppBar'
import OrderProgress from '@/containers/customer/orders/OrderProgress'
import OrderSummary from '@/containers/customer/orders/OrderSummary'

export default function OrderDetail() {
  return (
    <>
      <AppBar title="주문 상세" />
      <div className="divide-y-4 divide-gray-medium">
        <OrderProgress />
        <OrderSummary />
      </div>
    </>
  )
}
