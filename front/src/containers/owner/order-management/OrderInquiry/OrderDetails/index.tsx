import { useState } from 'react'
import OrderDetail from '@/components/OrderDetail' // 추가 정보를 불러올 컴포넌트
import { SearchedOrder } from '..'

interface OrderDetailsProps {
  order: SearchedOrder
}

const PAYMENT_METHOD_KEY = {
  CREDIT_CARD: '카드',
  ACCOUNT_TRANSFER: '계좌이체',
  SIMPLE_PAYMENT: '간편결제',
  CASH: '현금',
} as const

const STATUS_KEY = {
  RECEIVED: '수령완료',
  REJECTED: '주문거절',
  WAITING_FOR_RECEIPT: '수령대기',
  PROCESSING: '조리중',
  WAITING_FOR_PROCESSING: '주문 요청',
} as const

export default function OrderDetails({ order }: OrderDetailsProps) {
  // 드롭다운 상태
  const [isExpanded, setIsExpanded] = useState(false)

  // 드롭다운 토글 함수
  const toggleExpand = () => setIsExpanded(!isExpanded)

  return (
    <div className="border rounded-lg p-4 mb-4 mt-4 bg-white shadow-md">
      {/* 주문 기본 정보 */}
      <div className="flex items-center">
        <span className="text-gray-500">주문번호</span>
        <div className="flex items-center ml-6">
          <span className="bg-primary-400 text-xs text-white px-2 py-1 rounded-md mr-2">
            {STATUS_KEY[order.status as keyof typeof STATUS_KEY]}
          </span>
          <strong className="font-bold">{order.orderNumber}</strong>
        </div>
      </div>
      <div className="flex mt-2">
        <span className="text-gray-500">주문시각</span>
        <div className="ml-6">{order.orderCreatedAt}</div>
      </div>
      <div className="flex mt-2">
        <span className="text-gray-500">수령시각</span>
        <div className="ml-6">
          {order.orderReceivedAt ? order.orderReceivedAt : '-'}
        </div>
      </div>
      <div className="flex mt-2">
        <span className="text-gray-500">주문내역</span>
        <div className="ml-6 font-semibold">{order.menuCount}</div>
      </div>
      <div className="flex mt-2">
        <span className="text-gray-500">결제타입</span>
        <div className="ml-6">
          {
            PAYMENT_METHOD_KEY[
              order.paymentMethod as keyof typeof PAYMENT_METHOD_KEY
            ]
          }
        </div>
      </div>
      <div className="flex mt-2 items-center">
        <span className="text-gray-500">결제금액</span>
        <strong className="ml-6 font-bold text-lg">
          {order.totalPrice.toLocaleString()}
        </strong>
      </div>

      {/* 자세히 보기 버튼 */}
      <div className="flex justify-end mt-4">
        <button className="text-black font-semibold" onClick={toggleExpand}>
          {isExpanded ? '접기 ▲' : '자세히 ▼'}
        </button>
      </div>

      {/* 드롭다운으로 보여줄 추가 정보 */}
      {isExpanded && (
        <div className="mt-4">
          <OrderDetail orderId={String(order.ordersId)} />
        </div>
      )}
    </div>
  )
}
