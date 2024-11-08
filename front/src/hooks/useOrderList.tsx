/* eslint-disable import/prefer-default-export */
import { useQuery } from '@tanstack/react-query'

interface Product {
  orderProductCount: number
  productName: string
}

interface StoreOrder {
  id: number
  menuCount: number
  orderCreatedAt: string
  orderNumber: string
  products: Product[]
  status: string
  totalPrice: number
}

export const useOrderList = (
  storeId: number | null | undefined,
  status: 'PROCESSING' | 'RECEIVING'
) => {
  const errorMessage =
    status === 'PROCESSING'
      ? '주문 내역 리스트 조회에 실패했습니다'
      : '주문 완료 리스트 조회에 실패했습니다'
  const getStoreOrderList = async () => {
    const response = await fetch(
      `/services/order/${storeId}/list?pgno=${100}&spp=${10}&status=${status}`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      }
    )

    console.log('response:', response)

    if (!response.ok) {
      console.error(errorMessage)
    }
    return response.json()
  }

  return useQuery<
    {
      getStoreOrderLists: StoreOrder[]
      totalDataCount: number
      totalPageCount: number
    },
    Error
  >({
    queryKey: [
      `/order/list${status === 'PROCESSING' ? '' : '/complete'}`,
      storeId,
    ],
    queryFn: getStoreOrderList,
    enabled: !!storeId,
  })
}
