'use client'

import { useRouter, useSearchParams } from 'next/navigation'
import { IoIosClose } from 'react-icons/io'
import { useQueryClient } from '@tanstack/react-query'
import { useOwnerInfo } from '@/hooks/useOwnerInfo'

export default function StoreCloseModal() {
  const router = useRouter()
  const searchParams = useSearchParams()
  const {
    data: ownerInfo,
    error: ownerInfoError,
    isLoading: ownerInfoLoading,
  } = useOwnerInfo()
  const queryClient = useQueryClient()

  const updateStoreStatus = async () => {
    if (!ownerInfo?.storeId) return
    const response = await fetch(
      // 점포 상태 변경하는 API
      `/services/store/${ownerInfo?.storeId}/status`,
      {
        method: 'PATCH',
        body: JSON.stringify({
          status: '준비중',
        }),
      }
    )
    if (!response.ok) {
      alert('영업 종료에 실패했습니다.')
    }
    queryClient.invalidateQueries({ queryKey: ['/store', ownerInfo?.storeId] })
  }

  return (
    <div className="relative flex flex-col gap-8 items-center text-text bg-white px-3 py-3 rounded-xl max-w-[22rem] w-full">
      <button
        type="button"
        onClick={() => router.back()}
        className="absolute top-2 right-3 w-7 h-7"
      >
        <IoIosClose className="w-full h-full" />
      </button>
      <div className="flex flex-col gap-3 mt-5 items-center">
        <h1 className="font-medium">정말 영업을 종료하시겠습니까?</h1>
      </div>
      <div className="w-full flex justify-between gap-2">
        <button
          type="button"
          onClick={() => router.back()}
          className="py-2 text-xs font-normal w-full bg-gray-medium rounded-md"
        >
          아니요
        </button>
        <button
          type="button"
          onClick={() => {
            updateStoreStatus()
            router.back()
          }}
          className="py-2 text-xs font-normal w-full bg-primary-500 text-secondary-light rounded-md"
        >
          네, 종료할게요
        </button>
      </div>
    </div>
  )
}
