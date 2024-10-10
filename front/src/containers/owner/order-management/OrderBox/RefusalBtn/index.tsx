import { useQueryClient } from '@tanstack/react-query'
import { useOwnerInfo } from '@/hooks/useOwnerInfo'

export default function RefusalBtn({ orderId }: { orderId: number }) {
  const queryClient = useQueryClient()
  const {
    data: ownerInfo,
    error: ownerInfoError,
    isLoading: ownerInfoLoading,
  } = useOwnerInfo()

  const handleClickRefusalBtn = async () => {
    const response = await fetch(`/services/order/${orderId}/handle?flag=0`)

    if (!response.ok) {
      console.error('주문 거절에 실패했습니다')
      return
    }

    // 주문 거절이 됐다는 토스트 메시지 보여주기
    queryClient.invalidateQueries({
      queryKey: ['/order/list', ownerInfo?.storeId],
    })
  }

  if (ownerInfoLoading) {
    return <p>로딩중</p>
  }

  if (ownerInfoError) {
    return <p>에러 발생</p>
  }

  return (
    <button
      onClick={handleClickRefusalBtn}
      className="px-2 py-2 bg-white border border-gray-dark text-text font-bold rounded-md h-20 w-14 text-sm"
    >
      주문
      <br />
      거절
    </button>
  )
}
