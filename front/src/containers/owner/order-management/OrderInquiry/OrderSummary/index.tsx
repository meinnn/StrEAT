import { RiSearchLine } from 'react-icons/ri'

interface Order {
  orderNumber: string
  type: string
  orderTime: string
  receiptTime: string
  orderDetails: string
  paymentType: string
  totalAmount: string
}

interface OrderSummaryProps {
  orders: Order[]
}

export default function OrderSummary({ orders }: OrderSummaryProps) {
  return (
    <div className="px-4">
      {/* 검색 기능 */}
      <div className="flex items-center border border-gray-dark rounded-xl p-3 bg-white">
        <RiSearchLine className="text-primary-500 mr-2" size={20} />
        <input
          type="text"
          placeholder="검색"
          className="w-full bg-transparent outline-none text-sm"
        />
      </div>

      {/* 주문 수, 결제 금액 */}
      <div>
        <div className="flex justify-between mt-10">
          <span className="text-gray-500 text-sm">주문 수</span>
          <div className="flex items-end">
            <div className="text-xl font-bold">{orders.length}</div>{' '}
            {/* 주문 수만 표시 */}
            <div className="text-sm font-bold pl-1">건</div>
          </div>
        </div>
        <div className="flex justify-between mt-4">
          <span className="text-gray-500 text-sm ">결제 금액</span>
          <div className="flex items-end">
            <div className="flex font-bold text-xl">-</div>{' '}
            {/* 결제 금액은 나중에 처리 */}
            <div className="text-sm font-bold pl-1">원</div>
          </div>
        </div>
      </div>

      {/* 종료 선 */}
      <div className="border-t border-gray-dark mt-4" />
    </div>
  )
}
