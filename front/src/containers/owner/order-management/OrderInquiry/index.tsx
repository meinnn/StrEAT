// 주문 조회 컴포넌트
import ChooseDate from '@/containers/owner/order-management/OrderInquiry/ChooseDate'
import OrderSummary from '@/containers/owner/order-management/OrderInquiry/OrderSummary'

export default function OrderInquiry() {
  return (
    <div>
      <ChooseDate />
      <OrderSummary />
    </div>
  )
}
