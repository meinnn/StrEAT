import { useQueryClient } from '@tanstack/react-query'
import { useOwnerInfo } from '@/hooks/useOwnerInfo'

export default function AcceptBtn({ orderId }: { orderId: number }) {
  const queryClient = useQueryClient()
  const {
    data: ownerInfo,
    error: ownerInfoError,
    isLoading: ownerInfoLoading,
  } = useOwnerInfo()

  const handleClickAccpetBtn = async () => {
    const response = await fetch(`/services/order/${orderId}/handle?flag=1`)

    if (!response.ok) {
      console.error('주문 승인에 실패했습니다')
      return
    }

    // 주문 승인 완료했다는 토스트 메시지 보여주기
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
      type="button"
      onClick={handleClickAccpetBtn}
      className="px-2 py-2 font-bold rounded-md border-2 h-20 w-14 text-sm bg-white text-primary-600 border-primary-600"
    >
      주문 수락
    </button>
  )
}
