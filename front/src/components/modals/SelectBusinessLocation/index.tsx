'use client'

import { useQuery } from '@tanstack/react-query'
import Image from 'next/image'
import Link from 'next/link'
import { useRouter } from 'next/navigation'
import { HiOutlineLocationMarker } from 'react-icons/hi'
import { useOwnerInfo } from '@/hooks/useOwnerInfo'
import { useMyStoreInfo } from '@/hooks/useMyStoreInfo'

interface StoreLocationPhoto {
  createdAt: string
  id: number
  latitude: number
  longitude: number
  src: string
}

interface StoreLocation {
  address: string
  createdAt: string
  id: number
  latitude: number
  locationPhotos: StoreLocationPhoto[]
  longitude: number
  nickname: string
}

export default function SelectBusinessLocation() {
  const router = useRouter()
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

  const getStoreLocation = async () => {
    const response = await fetch(
      `/services/store/${storeInfo?.storeInfo.id}/business-location`,
      {
        method: 'GET',
      }
    )
    if (!response.ok) {
      console.error('영업 위치 리스트 조회에 실패했습니다')
    }
    return response.json()
  }

  const {
    data: storeBusinessLocationData,
    error,
    isLoading,
  } = useQuery<StoreLocation[], Error>({
    queryKey: ['/store/business-location', storeInfo?.storeInfo.id],
    queryFn: getStoreLocation,
  })

  console.log('storeBusinessLocationData:', storeBusinessLocationData)

  if (isLoading || ownerInfoLoading || storeLoading) {
    return <p>로딩중</p>
  }

  if (error || ownerInfoError || storeError) {
    return <p>에러 발생</p>
  }

  return (
    <div className="relative bg-white h-full pt-5 pb-4 flex overflow-hidden flex-col max-h-[35rem] w-full gap-2 max-w-96 rounded-lg">
      <div className="px-5 pb-3 flex flex-col gap-1">
        <h1 className="text-text font-semibold">영업 위치</h1>
        <p className="text-xs text-text">영업할 위치를 선택해주세요</p>
      </div>
      <div className="overflow-y-auto mb-14 px-5 pb-5 flex flex-col gap-2">
        {storeBusinessLocationData && storeBusinessLocationData.length > 0 ? (
          storeBusinessLocationData.map((storeLocation) => {
            return (
              <div
                onClick={() =>
                  router.push(
                    `/owner/store/open?locationId=${storeLocation.id}&address=${storeLocation.address}`
                  )
                }
                key={storeLocation.id}
                className="hover:bg-secondary-medium shrink-0 p-4 shadow-md relative w-full overflow-hidden border border-gray-medium bg-white rounded-lg"
              >
                <div className="w-full flex items-start gap-4">
                  <div className="shrink-0 border border-gray-medium relative w-16 aspect-square rounded-md overflow-hidden">
                    <Image
                      src={
                        storeLocation.locationPhotos[0]?.src
                          ? storeLocation.locationPhotos[0].src
                          : '/images/보쌈사진.jpg'
                      }
                      alt="점포 위치 사진"
                      fill
                      className="absolute object-cover"
                      priority
                    />
                  </div>
                  <div className="flex flex-col gap-2">
                    <p className="font-semibold">{storeLocation.nickname}</p>
                    <div className="flex gap-1 items-start">
                      <HiOutlineLocationMarker className="shrink-0  w-5 h-5 text-primary-500" />
                      <p>{storeLocation.address}</p>
                    </div>
                  </div>
                </div>
              </div>
            )
          })
        ) : (
          <div className="flex justify-center items-center h-80 flex-col gap-1">
            <div className="relative w-28 aspect-square">
              <Image
                src="/images/no_content_illustration.png"
                className="object-cover"
                alt="내용이 없다는 일러스트"
                fill
                priority
              />
            </div>
            <div className="flex flex-col gap-5">
              <p className="text-text font-bold">조회된 영업위치가 없습니다</p>
            </div>
          </div>
        )}
      </div>

      <div className="bg-white w-full absolute bottom-0 pb-4 pt-3 px-5">
        <Link href="/register-business-location">
          <button className="whitespace-nowrap bg-primary-500 text-sm text-secondary-light py-3 rounded-md w-full">
            원하는 위치가 없어요. 새로운 위치를 추가할래요
          </button>
        </Link>
      </div>
    </div>
  )
}
