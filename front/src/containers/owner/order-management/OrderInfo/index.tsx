export default function OrderInfo() {
  return (
    <div className="text-left p-2 mb-4">
      {/* 주문 정보 헤더 */}
      <div className="text-xl font-semibold mb-4">
        주문정보 001
        <div className="mt-2">
          <hr className="border-t-2 border-gray-300" />
        </div>
      </div>

      {/* 첫 번째 주문 세트 */}
      <div className="flex justify-between mb-2 font-medium text-medium">
        <p>햄버거 유경 세트 1개</p>
        <p className="font-bold">28,000원</p>
      </div>
      <div className="pl-4 text-black text-sm">
        <p>└ 1인분 (25,000원)</p>
        <p>└ 오이피클(1인분) (1,000원)</p>
        <p>└ 콩나물(1인분) (1,000원)</p>
        <p>└ 공기밥(1인분, 최고급흰미) (1,000원)</p>
      </div>
      <hr className="my-4 border-t-2 border-gray-300" />

      {/* 두 번째 주문 세트 */}
      <div className="flex justify-between mb-2 font-medium text-medium">
        <p>햄버거 유경 세트 1개</p>
        <p className="font-bold">26,000원</p>
      </div>
      <div className="pl-4 text-black text-sm">
        <p>└ 1인분 (25,000원)</p>
        <p>└ 공기밥(1인분, 최고급흰미) (1,000원)</p>
      </div>
      <hr className="my-4 border-t-2 border-black" />

      {/* 총 결제 금액 */}
      <div className="flex justify-between font-bold">
        <p>총 결제 금액</p>
        <p>54,000원</p>
      </div>
      <div className="text-sm text-black mt-2">24.09.24 08:44</div>
    </div>
  )
}
