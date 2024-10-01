import { useState } from 'react'
import ChooseDate from '@/containers/owner/order-management/OrderInquiry/ChooseDate'
import OrderSummary from '@/containers/owner/order-management/OrderInquiry/OrderSummary'
import OrderTag from '@/containers/owner/order-management/OrderInquiry/OrderTag'
import OrderDetails from '@/containers/owner/order-management/OrderInquiry/OrderDetails'

export default function OrderInquiry() {
  // 더미 주문 데이터 생성
  const dummyOrders = [
    {
      orderNumber: 'T1RE0001AA',
      type: '수령완료', // 태그와 매칭되는 필드
      orderTime: '2024.09.01 오후 08:20:21',
      receiptTime: '2024.09.01 오후 09:30:30',
      orderDetails: '햄버거 유경 세트 외 1건',
      paymentType: '간편결제',
      totalAmount: '54,000원',
    },
    {
      orderNumber: 'T1RE0002BB',
      type: '주문취소', // 태그와 매칭되는 필드
      orderTime: '2024.09.02 오후 06:15:00',
      receiptTime: '2024.09.02 오후 07:20:00',
      orderDetails: '치킨 세트 외 2건',
      paymentType: '카드결제',
      totalAmount: '75,000원',
    },
  ]

  const [filters, setFilters] = useState<string[]>([]) // 필터 상태 관리
  const [filteredOrders, setFilteredOrders] = useState(dummyOrders) // 필터링된 주문 상태 관리
  const [orders, setOrders] = useState(dummyOrders) // 전체 주문 상태 관리 (더미 데이터 사용)

  // 필터 변경 시 호출되는 함수
  const handleFilterChange = (updatedFilters: string[]) => {
    setFilters(updatedFilters)

    if (updatedFilters.length === 0) {
      // 필터가 없으면 전체 데이터를 보여줌
      setFilteredOrders(orders)
    } else {
      // 선택된 태그에 따라 필터링된 데이터로 업데이트
      const filtered = orders.filter((order) => {
        // order.type이 배열이라면, 해당 배열에 필터 중 하나라도 포함되는지 확인
        if (Array.isArray(order.type)) {
          return updatedFilters.some((filter) => order.type.includes(filter))
        }
        // order.type이 단일 문자열일 경우
        return updatedFilters.includes(order.type)
      })
      setFilteredOrders(filtered)
    }
  }

  return (
    <div>
      <ChooseDate />
      <OrderTag onFilterChange={handleFilterChange} />
      <OrderSummary orders={filteredOrders} /> {/* 필터링된 주문 전달 */}
      {/* 필터링된 주문을 각각 OrderDetails로 렌더링 */}
      {filteredOrders.map((order) => (
        <OrderDetails key={order.orderNumber} order={order} />
      ))}
    </div>
  )
}
