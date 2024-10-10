import PrintOrderBtn from '@/containers/owner/order-management/OrderBox/PrintOrderBtn'
import OrderInfoBtn from '@/containers/owner/order-management/OrderInfo/OrderInfoBtn'
import { useOrderList } from '@/hooks/useOrderList'
import { useOwnerInfo } from '@/hooks/useOwnerInfo'

export default function CompleteBox() {
  const {
    data: ownerInfo,
    error: ownerInfoError,
    isLoading: ownerInfoLoading,
  } = useOwnerInfo()

  // // "PROCESSING" || "RECEIVING"
  const {
    data: storeOrderCopleteListData,
    error,
    isLoading,
  } = useOrderList(ownerInfo?.storeId, 'RECEIVING')

  if (isLoading || ownerInfoLoading) {
    return <p>로딩중</p>
  }

  if (error || ownerInfoError) {
    return <p>에러 발생</p>
  }

  const parsedTime = (dateString: string) => {
    const date = new Date(dateString)
    const hours = date.getHours().toString().padStart(2, '0')
    const minutes = date.getMinutes().toString().padStart(2, '0')

    return `${hours}:${minutes}`
  }

  return (
    <div className="flex flex-col gap-2 mt-2">
      {storeOrderCopleteListData &&
      storeOrderCopleteListData?.getStoreOrderLists?.length > 0
        ? storeOrderCopleteListData.getStoreOrderLists.map((order) => {
            return (
              <div
                key={order.id}
                className="bg-gray-medium flex gap-2 border p-4 rounded-lg shadow-md max-w-full mx-3 justify-between"
              >
                {/* 수령 전 라벨 */}
                <div
                  className={`${order.status === 'WAITING_FOR_RECEIPT' ? 'border-gray-400  text-gray-400 bg-white' : 'border-primary-500  text-primary-500 bg-white'} absolute rounded-full py-0.5 text-xs font-semibold  border-2 text-center w-[72px] -ms-1`}
                >
                  {order.status === 'WAITING_FOR_RECEIPT'
                    ? ' 수령 전'
                    : '수령 완료'}
                </div>

                <div className="flex items-start">
                  {/* 왼쪽에 시간 배치 */}
                  <div className="text-2xl font-semibold mr-4 flex flex-col justify-center h-full">
                    {parsedTime(order.orderCreatedAt)}
                  </div>
                  {/* 메뉴 정보 및 가격 */}
                  <div className="flex flex-col justify-between max-w-full">
                    <div className="text-m font-semibold break-words break-all">
                      {`[메뉴 ${order.menuCount}개] ${order.totalPrice.toLocaleString()}원`}
                    </div>
                    <div className="text-xs break-words whitespace-normal flex gap-1 flex-wrap">
                      {order.products.map((product) => {
                        return (
                          <div
                            key={`${product.productName} ${product.orderProductCount}`}
                          >
                            <span className="whitespace-nowrap">{`${product.productName} ${product.orderProductCount}개`}</span>
                            <span className="last:hidden">/</span>
                          </div>
                        )
                      })}
                    </div>
                  </div>
                </div>

                {/* 버튼 배치 */}
                <div className="flex justify-end space-x-2">
                  <PrintOrderBtn />
                  <OrderInfoBtn orderId={order.id} />
                </div>
              </div>
            )
          })
        : null}
    </div>
  )
}
