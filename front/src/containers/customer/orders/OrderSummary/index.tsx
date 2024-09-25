import StoreLink from '@/components/StoreLink'
import OrderMenuDetail from '@/components/OrderMenuDetail'
import { OrderItem } from '@/types/order'

export const ORDER_ITEMS: OrderItem[] = [
  {
    id: 1,
    name: '후라이드 치킨',
    image: '/images/보쌈사진.jpg',
    price: 1000000000,
    quantity: 1,
    options: ['한마리', '양념치킨 소스'],
  },
  {
    id: 2,
    name: '양념 치킨',
    image: '/images/보쌈사진.jpg',
    price: 1000000000,
    quantity: 1,
    options: ['한마리', '양념치킨 소스'],
  },
]

export default function OrderSummary() {
  // 임시 데이터
  const orderItems = [...ORDER_ITEMS]

  // 총 개수와 총 가격 계산 (임시 데이터로)
  const totalQuantity = orderItems.reduce((acc, item) => acc + item.quantity, 0)
  const totalPrice = orderItems.reduce((acc, item) => acc + item.price, 0)

  return (
    <div className="p-6">
      <StoreLink storeId={1} />

      <h3 className="text-xl text-primary-950 font-semibold mb-4 mt-8">
        주문내역
      </h3>
      <div className="m-4">
        {orderItems.map((item) => (
          <div key={item.id} className="flex justify-between my-6">
            <OrderMenuDetail item={item} />
            <p className="text-md font-bold">{item.price.toLocaleString()}원</p>
          </div>
        ))}
      </div>

      {/* 주문 총합 */}
      <div className="border-t border-gray-medium p-4">
        <div className="flex justify-between">
          <span className="font-medium">총 {totalQuantity}개</span>
          <span className="text-primary-500 font-semibold text-xl">
            {totalPrice.toLocaleString()}원
          </span>
        </div>
      </div>
    </div>
  )
}
