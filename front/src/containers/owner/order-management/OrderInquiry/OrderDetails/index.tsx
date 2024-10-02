import { useState } from 'react'
import OrderInfo from '@/containers/owner/order-management/OrderInfo' // 추가 정보를 불러올 컴포넌트

// Order 인터페이스를 더미 데이터에 맞게 수정
interface Order {
  orderNumber: string
  type: string
  orderTime: string
  receiptTime: string
  orderDetails: string
  paymentType: string | null // 필터링에서 사용될 type 필드, null 가능
  totalAmount: string
}

interface OrderDetailsProps {
  order: Order // 더미 데이터에 맞춰 필수로 설정
}

export default function OrderDetails({ order }: OrderDetailsProps) {
  // 드롭다운 상태
  const [isExpanded, setIsExpanded] = useState(false)

  // 드롭다운 토글 함수
  const toggleExpand = () => setIsExpanded(!isExpanded)

  return (
    <div className="border rounded-lg p-4 mb-4 mt-4 bg-white shadow-md">
      {/* 주문 기본 정보 */}
      <div className="flex items-center">
        <div className="text-gray-500">주문번호</div>
        <div className="flex items-center ml-6">
          <span className="bg-primary-400 text-white px-2 py-1 rounded-lg mr-2">
            {/* {order.type} */}
            간편결제
          </span>
          <div className="font-bold">{order.orderNumber}</div>
        </div>
      </div>
      <div className="flex mt-2">
        <div className="text-gray-500">주문시각</div>
        <div className="ml-6">{order.orderTime}</div>
      </div>
      <div className="flex mt-2">
        <div className="text-gray-500">수령시각</div>
        <div className="ml-6">{order.receiptTime}</div>
      </div>
      <div className="flex mt-2">
        <div className="text-gray-500">주문내역</div>
        <div className="ml-6 font-semibold">{order.orderDetails}</div>
      </div>
      <div className="flex mt-2">
        <div className="text-gray-500">결제타입</div>
        <div className="ml-6">
          {/* paymentType이 문자열인지 확인하고, null일 경우 처리 */}
          {order.paymentType ? (
            <span className="bg-primary-400 text-white px-2 py-1 rounded-lg">
              {order.paymentType}
            </span>
          ) : (
            <span>결제타입 없음</span>
          )}
        </div>
      </div>
      <div className="flex mt-2">
        <div className="text-gray-500">결제금액</div>
        <div className="ml-6 font-bold text-lg">{order.totalAmount}</div>
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
          <OrderInfo />
        </div>
      )}
    </div>
  )
}
