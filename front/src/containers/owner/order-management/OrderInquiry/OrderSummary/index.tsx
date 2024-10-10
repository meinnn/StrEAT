interface OrderSummaryProps {
  orderCount: number
  totalPaymentAmount: number | string
}

export default function OrderSummary({
  orderCount,
  totalPaymentAmount,
}: OrderSummaryProps) {
  return (
    <div className="px-4">
      {/* 주문 수, 결제 금액 */}
      <div>
        <div className="flex justify-between mt-10">
          <span className="text-gray-500 text-sm">주문 수</span>
          <div className="flex items-end">
            <div className="text-xl font-bold">{orderCount}</div>
            {/* 주문 수만 표시 */}
            <div className="text-sm font-bold pl-1">건</div>
          </div>
        </div>
        <div className="flex justify-between mt-4">
          <span className="text-gray-500 text-sm ">결제 금액</span>
          <div className="flex items-end">
            <div className="flex font-bold text-xl">
              {totalPaymentAmount?.toLocaleString()}
            </div>
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
