'use client'

import { useRouter, useSearchParams } from 'next/navigation'
import { IoIosClose } from 'react-icons/io'
import { useQueryClient } from '@tanstack/react-query'
import { useMyStoreInfo } from '@/hooks/useMyStoreInfo'
import { useOwnerInfo } from '@/hooks/useOwnerInfo'

export default function LocationConfirmCancelModal() {
  const router = useRouter()
  const searchParams = useSearchParams()
  const queryClient = useQueryClient()
  const locationId = searchParams.get('locationId')
  const address = searchParams.get('address')
  const {
    data: ownerInfo,
    error: ownerInfoError,
    isLoading: ownerInfoLoading,
  } = useOwnerInfo()
  const {
    data: storeData,
    error,
    isLoading,
  } = useMyStoreInfo(ownerInfo?.storeId)

  // ** 네, 맞아요 버튼을 클릭했을 경우 **
  // 1. 고정형일 경우: 영업상태 변경 API만 사용
  // 2. 이동형일 경우: 영업상태 변경 API + 점포 위치 정보 갈아끼우는 API 사용

  const updateStoreStatus = async () => {
    if (!ownerInfo?.storeId) return
    const response = await fetch(
      // 점포 상태 변경하는 API
      `/services/store/${ownerInfo?.storeId}/status`,
      {
        method: 'PATCH',
        body: JSON.stringify({
          status: '영업중',
        }),
      }
    )
    if (!response.ok) {
      alert('영업 시작에 실패했습니다.')
    }
    queryClient.invalidateQueries({ queryKey: ['/store', ownerInfo?.storeId] })
  }

  const updateStoreBusinessLocation = async () => {
    if (!ownerInfo?.storeId) return
    if (storeData?.storeInfo.type === '고정형') {
      console.log('고정형일 경우')
    } else if (storeData?.storeInfo.type === '이동형') {
      // 이동형
      const response = await fetch(
        // 점포 위치 정보 갈아끼우는 API
        `/services/store/${ownerInfo?.storeId}/business-location/update`,
        {
          method: 'PATCH',
          body: JSON.stringify({ locationId }),
        }
      )
      if (!response.ok) {
        console.error('점포 위치 업데이트에 실패했습니다.')
        return
      }

      updateStoreStatus()
    }
  }

  if (isLoading || ownerInfoLoading) {
    return <p>로딩중</p>
  }

  if (error || ownerInfoError) {
    return <p>에러 발생</p>
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
      <div className="flex flex-col gap-3 mt-3 items-center">
        <h1 className="font-medium">영업 장소가 이 장소가 맞나요?</h1>
        <p className="text-xs">{address}</p>
      </div>
      <div className="w-full flex justify-between gap-2">
        <button
          type="button"
          onClick={() => router.back()}
          className="py-2 text-xs font-normal w-full bg-gray-medium rounded-md"
        >
          아니에요
        </button>
        <button
          type="button"
          onClick={() => {
            updateStoreBusinessLocation()
            router.replace('/owner/store')
          }}
          className="py-2 text-xs font-normal w-full bg-primary-500 text-secondary-light rounded-md"
        >
          네, 맞아요
        </button>
      </div>
    </div>
  )
}
