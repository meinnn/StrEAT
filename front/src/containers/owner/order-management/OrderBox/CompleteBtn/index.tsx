import { useQueryClient } from '@tanstack/react-query'
import { useOwnerInfo } from '@/hooks/useOwnerInfo'

export default function CompleteBtn({ orderId }: { orderId: number }) {
  const queryClient = useQueryClient()
  const {
    data: ownerInfo,
    error: ownerInfoError,
    isLoading: ownerInfoLoading,
  } = useOwnerInfo()

  const handleClickCompleteBtn = async () => {
    const response = await fetch(`/services/order/${orderId}/handle/complete`)

    console.log(response)

    if (!response.ok) {
      console.error('조리 완료 처리에 실패했습니다')
      return
    }

    queryClient.invalidateQueries({
      queryKey: ['/order/list', ownerInfo?.storeId],
    })
    queryClient.invalidateQueries({
      queryKey: ['/order/list/complete', ownerInfo?.storeId],
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
      onClick={handleClickCompleteBtn}
      className="whitespace-nowrap px-2 py-2 text-blue-500 bg-white border-blue-500 font-bold rounded-md border-2 h-20 w-14 text-sm"
    >
      완료
      <br />
      처리
    </button>
  )
}
