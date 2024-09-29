// 주문 조회 컴포넌트
import { useState } from 'react'
import ChooseDate from '@/containers/owner/order-management/OrderInquiry/ChooseDate'
import OrderSummary from '@/containers/owner/order-management/OrderInquiry/OrderSummary'
import OrderTag from '@/containers/owner/order-management/OrderInquiry/OrderTag'

export default function OrderInquiry() {
  // 필터 상태 관리
  const [filter, setFilters] = useState<string[]>([])

  // 필터 변경 시 호출되는 함수
  const handleFilterChange = (updatedFilters: string[]) => {
    setFilters(updatedFilters)
    // 여기서 필터를 기반으로 API 요청
  }

  return (
    <div>
      <ChooseDate />
      <OrderTag onFilterChange={handleFilterChange} />
      <OrderSummary />
    </div>
  )
}
