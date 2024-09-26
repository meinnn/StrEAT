import { RiSearchLine } from 'react-icons/ri'

export default function OrderSummary() {
  return (
    <div>
      {/* 검색 기능 */}
      <div className="flex items-center border border-gray-medium rounded-xl p-3 mx-2 bg-white">
        <RiSearchLine className="text-primary-500 mr-2" size={20} />
        <input
          type="text"
          placeholder="검색"
          className="w-full bg-transparent outline-none text-sm"
        />
      </div>

      {/* 주문 수, 결재 금액 */}
    </div>
  )
}
