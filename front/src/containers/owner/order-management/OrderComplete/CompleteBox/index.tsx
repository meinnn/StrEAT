import PrintOrderBtn from '@/containers/owner/order-management/OrderBox/PrintOrderBtn'
import OrderInfoBtn from '@/containers/owner/order-management/OrderInfo/OrderInfoBtn'

export default function CompleteBox() {
  return (
    <div>
      {/* 수령 전 박스 */}
      <div className="bg-gray-medium flex gap-2 border p-4 rounded-lg shadow-md max-w-full mt-4 mx-3 justify-between">
        {/* 수령 전 라벨 */}
        <div className="absolute rounded-full py-0.5 text-xs font-semibold text-gray-400 bg-white border-2 border-gray-400 text-center w-[72px] -ms-1">
          수령 전
        </div>

        <div className="flex items-start">
          {/* 왼쪽에 시간 배치 */}
          <div className="text-2xl font-semibold mr-4 flex flex-col justify-center h-full">
            13:22
          </div>
          {/* 메뉴 정보 및 가격 */}
          <div className="flex flex-col justify-between max-w-full">
            <div className="text-m font-semibold break-words break-all">
              [메뉴 2개] 10,800원
            </div>
            <div className="text-xs break-words whitespace-normal">
              김치찌개 1개 / 된장찌개 2개 / 김치 볶음밥 1 / 김치찌개 1개 /
              된장찌개 2개 / 김치 볶음밥 1
            </div>
          </div>
        </div>

        {/* 버튼 배치 */}
        <div className="flex justify-end space-x-2">
          <PrintOrderBtn />
          <OrderInfoBtn />
        </div>
      </div>

      {/* 수령 완료 박스 */}
      <div className="bg-gray-medium flex gap-2 border p-4 rounded-lg shadow-md max-w-full mt-4 mx-3 justify-between">
        {/* 수령 전 라벨 */}
        <div className="absolute rounded-full py-0.5 text-xs font-semibold bg-white text-primary-400 border-2 border-primary-400 text-center w-[72px] -ms-1">
          수령 완료
        </div>

        <div className="flex items-start">
          {/* 왼쪽에 시간 배치 */}
          <div className="text-2xl font-semibold mr-4 flex flex-col justify-center h-full">
            13:22
          </div>
          {/* 메뉴 정보 및 가격 */}
          <div className="flex flex-col justify-between max-w-full">
            <div className="text-m font-semibold break-words break-all">
              [메뉴 2개] 10,800원
            </div>
            <div className="text-xs break-words whitespace-normal">
              김치찌개 1개 / 된장찌개 2개 / 김치 볶음밥 1 / 김치찌개 1개 /
              된장찌개 2개 / 김치 볶음밥 1
            </div>
          </div>
        </div>

        {/* 버튼 배치 */}
        <div className="flex justify-end space-x-2">
          <PrintOrderBtn />
          <OrderInfoBtn />
        </div>
      </div>
    </div>
  )
}
