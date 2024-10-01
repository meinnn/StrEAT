import { RiSearchLine } from 'react-icons/ri'

export default function OrderSummary() {
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

      {/* 주문 수, 결재 금액 */}
      <div>
        <div className="flex justify-between mt-10">
          <span className="text-gray-500 text-sm">주문 수</span>
          <div className="flex items-end">
            <div className=" text-xl font-bold">19</div>
            <div className="text-sm font-bold pl-1">건</div>
          </div>
        </div>
        <div className="flex justify-between mt-4">
          <span className="text-gray-500 text-sm ">결재 금액</span>
          <div className="flex items-end">
            <div className="flex font-bold text-xl">770,000</div>
            <div className="text-sm font-bold pl-1">원</div>
          </div>
        </div>
      </div>
      {/* 종료 선 */}
      <div className="border-t border-gray-dark mt-4"></div>
    </div>
  )
}
