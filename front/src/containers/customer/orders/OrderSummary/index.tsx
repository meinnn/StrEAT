import Image from 'next/image'
import Link from 'next/link'
import StoreLink from '@/components/StoreLink'
import OrderMenuDetail from '@/components/OrderMenuDetail'

export default function OrderSummary() {
  // 임시 데이터
  const ORDER_ITEMS = [
    {
      id: 1,
      name: '후라이드 치킨',
      quantity: 1,
      options: ['한마리', '양념치킨 소스'],
      price: 4000,
      image: '/images/보쌈사진.jpg', // 실제 이미지 경로로 변경
    },
    {
      id: 2,
      name: '후라이드 치킨',
      quantity: 1,
      options: ['한마리', '양념치킨 소스'],
      price: 4000,
      image: '/images/보쌈사진.jpg', // 실제 이미지 경로로 변경
    },
    {
      id: 3,
      name: '후라이드 치킨',
      quantity: 1,
      options: ['한마리', '양념치킨 소스'],
      price: 4000,
      image: '/images/보쌈사진.jpg', // 실제 이미지 경로로 변경
    },
  ]

  // 총 개수와 총 가격 계산 (임시 데이터로)
  const totalQuantity = ORDER_ITEMS.reduce(
    (acc, item) => acc + item.quantity,
    0
  )
  const totalPrice = ORDER_ITEMS.reduce((acc, item) => acc + item.price, 0)

  return (
    <div className="p-6">
      <StoreLink storeId={1} />

      <h3 className="text-xl text-primary-950 font-semibold mb-4 mt-8">
        주문내역
      </h3>
      <div className="m-4">
        {ORDER_ITEMS.map((item) => (
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
