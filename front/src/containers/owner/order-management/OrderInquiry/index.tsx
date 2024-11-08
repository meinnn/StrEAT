import { useMemo, useState } from 'react'
import { useQuery } from '@tanstack/react-query'
import ChooseDate from '@/containers/owner/order-management/OrderInquiry/ChooseDate'
import OrderSummary from '@/containers/owner/order-management/OrderInquiry/OrderSummary'
import OrderTag from '@/containers/owner/order-management/OrderInquiry/OrderTag'
import OrderDetails from '@/containers/owner/order-management/OrderInquiry/OrderDetails'
import { useOwnerInfo } from '@/hooks/useOwnerInfo'
import { useMyStoreInfo } from '@/hooks/useMyStoreInfo'

interface Option {
  optionName: string
  optionPrice: number
}

interface Product {
  optionList: Option[]
  productName: string
  productPrice: number
  productSrc: string | null
  quantity: number
}

export interface SearchedOrder {
  menuCount: number
  orderCreatedAt: string
  orderNumber: string
  orderReceivedAt: string | null
  ordersId: number
  paymentMethod: string
  productList: Product[]
  status: string
  storeId: number
  storeName: string
  storeSrc: string
  totalPrice: number
  waitingMenu?: string | null
  waitingTeam?: string | null
}

export interface Condition {
  startDate: string
  endDate: string
  statusTag: string[]
  paymentMethodTag: string[]
}

export default function OrderInquiry() {
  const {
    data: ownerInfo,
    error: ownerInfoError,
    isLoading: ownerInfoLoading,
  } = useOwnerInfo()
  const {
    data: storeInfo,
    error: storeError,
    isLoading: storeLoading,
  } = useMyStoreInfo(ownerInfo?.storeId)
  const now = new Date()
  const start = new Date(new Date().setDate(new Date().getDate() - 7))
  const [condition, setCondition] = useState<Condition>({
    startDate: new Date(
      start.getTime() - start.getTimezoneOffset() * 60000
    ).toISOString(),
    endDate: new Date(
      now.getTime() - now.getTimezoneOffset() * 60000
    ).toISOString(),
    statusTag: ['REJECTED'],
    paymentMethodTag: ['CASH'],
  })

  const getSearchedOrderList = async () => {
    const { startDate, endDate } = condition

    if (startDate.length === 0 || endDate.length === 0) return []

    const response = await fetch(
      `/services/order/${storeInfo?.storeInfo?.id}/search`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(condition),
      }
    )

    if (!response.ok) {
      console.error('주문 내역 리스트 조회에 실패했습니다')
    }
    return response.json()
  }

  const {
    data: searchedOrderListData,
    error,
    isLoading,
  } = useQuery<
    {
      searchOrderList: SearchedOrder[]
      totalCount: number
      totalDataCount: number
      totalPageCount: number
    },
    Error
  >({
    queryKey: ['/order/search', storeInfo?.storeInfo?.id, condition],
    queryFn: getSearchedOrderList,
  })

  // 주문 수에 따른 총 결제 금액
  const totalPaymentAmount = useMemo(() => {
    if (
      !searchedOrderListData ||
      searchedOrderListData?.searchOrderList?.length === 0
    )
      return '-'

    return searchedOrderListData?.searchOrderList?.reduce((acc, order) => {
      const price = order.totalPrice
      return acc + price
    }, 0)
  }, [searchedOrderListData])

  if (isLoading || storeLoading || ownerInfoLoading) {
    return <p>로딩중</p>
  }

  if (error || storeError || ownerInfoError) {
    return <p>에러 발생</p>
  }

  return (
    <div>
      <ChooseDate condition={condition} setCondition={setCondition} />
      <OrderTag condition={condition} setCondition={setCondition} />
      {searchedOrderListData ? (
        <>
          <OrderSummary
            orderCount={searchedOrderListData?.totalDataCount}
            totalPaymentAmount={totalPaymentAmount}
          />
          <div className="flex flex-col p-2">
            {searchedOrderListData?.searchOrderList?.map((searchedOrder) => (
              <OrderDetails
                key={searchedOrder.ordersId}
                order={searchedOrder}
              />
            ))}
          </div>
        </>
      ) : null}
    </div>
  )
}
