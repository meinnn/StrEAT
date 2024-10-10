'use client'

import { useQuery } from '@tanstack/react-query'

interface Option {
  optionName: string
  optionPrice: number
}

interface Product {
  productName: string
  productPrice: number
  productSrc: string | null
  quantity: number
  optionList: Option[]
}

interface OrderDetail {
  menuCount: number
  orderCreatedAt: string
  orderNumber: string
  orderReceivedAt: string
  ordersId: number
  paymentMethod: string
  productList: Product[]
  status: string
  storeId: number
  storeName: string
  storeSrc: string
  totalPrice: number
  waitingMenu: number
  waitingTeam: number
}

export default function OrderDetail({ orderId }: { orderId: string }) {
  const getOrderDetail = async () => {
    const response = await fetch(`/services/order/${orderId}`)
    if (!response.ok) {
      console.error('주문 내역 상세 조회에 실패했습니다')
    }
    return response.json()
  }

  const {
    data: orderDetailData,
    error,
    isLoading,
  } = useQuery<OrderDetail, Error>({
    queryKey: ['/order', orderId],
    queryFn: getOrderDetail,
  })

  console.log('storeOrderDetailData:', orderDetailData)

  if (isLoading) {
    return <p>로딩중</p>
  }

  if (error) {
    return <p>에러 발생</p>
  }

  return (
    <div className="text-left bg-white p-6 rounded-lg min-w-96">
      {/* 주문 정보 헤더 */}
      <div className="text-xl font-semibold mb-4">
        주문정보 {orderDetailData?.orderNumber}
        <div className="mt-2">
          <hr className="border-t-2 border-gray-300" />
        </div>
      </div>

      {orderDetailData &&
      orderDetailData?.productList &&
      orderDetailData.productList.length > 0
        ? orderDetailData.productList.map((product) => {
            return (
              <div
                key={product.productName}
                className="flex justify-between items-center pl-2"
              >
                <div className="flex items-center">
                  <div className="flex flex-col">
                    <p className="font-medium">{product.productName}</p>

                    <div className="flex flex-col text-black text-sm">
                      {product?.optionList?.map((option) => {
                        return (
                          <p key={option.optionName}>
                            └ &nbsp;
                            {`${option.optionName} (${option.optionPrice.toLocaleString()}원)`}{' '}
                          </p>
                        )
                      })}
                    </div>
                  </div>
                </div>
                <div className="flex gap-4">
                  <p>&nbsp;{product.quantity}개</p>
                  <p className="font-medium">
                    {(product.productPrice * product.quantity).toLocaleString()}
                    원
                  </p>
                </div>
              </div>
            )
          })
        : null}
      <hr className="my-4 border-t-2 border-black" />
      {/* 총 결제 금액 */}
      <div className="flex justify-between font-bold">
        <p>총 결제 금액</p>
        <p>{orderDetailData?.totalPrice?.toLocaleString()}원</p>
      </div>
      <div className="text-sm text-black mt-2">
        {orderDetailData?.orderReceivedAt?.split('T').join(' ')}
      </div>
    </div>
  )
}
