import PrintOrderBtn from '@/containers/owner/order-management/OrderBox/PrintOrderBtn'
import CompleteBtn from '@/containers/owner/order-management/OrderBox/CompleteBtn'
import { useOrderList } from '@/hooks/useOrderList'
import { useOwnerInfo } from '@/hooks/useOwnerInfo'
import RefusalBtn from '@/containers/owner/order-management/OrderBox/RefusalBtn'
import AcceptBtn from '@/containers/owner/order-management/OrderBox/AcceptBtn'

export default function OrderBox() {
  const {
    data: ownerInfo,
    error: ownerInfoError,
    isLoading: ownerInfoLoading,
  } = useOwnerInfo()

  // 'PROCESSING' | 'WAITING_FOR_PROCESSING'
  const {
    data: storeOrderListData,
    error,
    isLoading,
  } = useOrderList(ownerInfo?.storeId, 'PROCESSING')

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
      {storeOrderListData && storeOrderListData?.getStoreOrderLists?.length > 0
        ? storeOrderListData.getStoreOrderLists.map((order) => {
            return (
              <div
                key={order.id}
                className={`flex gap-2 border p-4 rounded-lg shadow-md max-w-full mx-3 justify-between ${
                  order.status === 'WAITING_FOR_PROCESSING'
                    ? 'bg-primary-400 text-white'
                    : 'bg-secondary-light text-text'
                }`}
              >
                <div className="flex items-start">
                  {/* 왼쪽에 시간 배치 */}
                  <div className="text-2xl font-semibold mr-4 flex flex-col justify-center h-full">
                    {parsedTime(order.orderCreatedAt)}
                  </div>
                  {/* 메뉴 정보 및 가격 */}
                  <div className="flex flex-col justify-between max-w-full gap-2">
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
                <div className="flex space-x-2">
                  {order.status === 'WAITING_FOR_PROCESSING' ? (
                    <div className="flex gap-1">
                      <RefusalBtn orderId={order.id} />
                      <AcceptBtn orderId={order.id} />
                    </div>
                  ) : (
                    <div className="flex gap-1">
                      <PrintOrderBtn />
                      <CompleteBtn orderId={order.id} />
                    </div>
                  )}
                </div>
              </div>
            )
          })
        : null}
    </div>
  )
}
