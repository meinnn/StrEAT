import StoreLink from '@/components/StoreLink'
import { OrderDetailData } from '@/types/order'
import OrderMenuDetail from '@/containers/customer/orders/OrderMenuDetail'

export default function OrderSummary({
  orderDetail,
}: {
  orderDetail: OrderDetailData
}) {
  return (
    <div className="p-6">
      <StoreLink
        storeId={orderDetail.storeId}
        storeSrc={orderDetail.storeSrc}
        storeName={orderDetail.storeName}
      />

      <h3 className="text-xl text-primary-950 font-semibold mb-4 mt-8">
        주문내역
      </h3>
      <div className="m-4">
        {orderDetail.productList.map((item) => (
          <div key={item.productName} className="flex justify-between my-6">
            <OrderMenuDetail item={item} />
            <p className="text-md font-bold">
              {item.productPrice.toLocaleString()}원
            </p>
          </div>
        ))}
      </div>

      {/* 주문 총합 */}
      <div className="border-t border-gray-medium p-4">
        <div className="flex justify-between">
          <span className="font-medium">총 {orderDetail.menuCount}개</span>
          <span className="text-primary-500 font-semibold text-xl">
            {orderDetail.totalPrice.toLocaleString()}원
          </span>
        </div>
      </div>
    </div>
  )
}
