'use client'

import Image from 'next/image'
import { IoSettingsOutline } from 'react-icons/io5'
import Link from 'next/link'
import { HiOutlineLocationMarker } from 'react-icons/hi'
import AppBar from '@/components/AppBar'
import { useBusinessLocation } from '@/hooks/useBusinessLocation'
import { useOwnerInfo } from '@/hooks/useOwnerInfo'

export default function OwnerStoreBusinessLocationPage() {
  const {
    data: ownerInfo,
    error: ownerInfoError,
    isLoading: ownerInfoLoading,
  } = useOwnerInfo()
  const {
    data: storeBusinessLocationData,
    error,
    isLoading,
  } = useBusinessLocation(ownerInfo?.storeId)

  if (isLoading || ownerInfoLoading) {
    return <p>로딩중</p>
  }

  if (error || ownerInfoError) {
    return <p>에러 발생</p>
  }

  return (
    <div>
      <AppBar title="점포 설정" />
      <main className="flex flex-col gap-3 px-6 py-5">
        <div className="flex gap-1 items-center">
          <h3 className="pl-2 text-xl font-medium ">영업 위치</h3>
          <Link href="/owner/store/setting/business-location">
            <IoSettingsOutline className="h-5 w-5" />
          </Link>
        </div>
        <div className="flex flex-col w-full gap-2">
          {storeBusinessLocationData && storeBusinessLocationData.length > 0 ? (
            storeBusinessLocationData.map((storeLocation) => {
              return (
                <div
                  key={storeLocation.id}
                  className="p-4 shadow-md relative w-full overflow-hidden border border-gray-medium bg-white rounded-lg"
                >
                  <div className="w-full flex items-center gap-4">
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
              <div className="relative w-40 aspect-square">
                <Image
                  src="/images/no_content_illustration.png"
                  className="object-cover"
                  alt="내용이 없다는 일러스트"
                  fill
                  priority
                />
              </div>
              <div className="flex flex-col gap-5">
                <p className="text-text font-bold">
                  조회된 영업위치가 없습니다
                </p>
                <Link href="/owner/store/39/setting/business-location">
                  <button className="bg-primary-950 text-secondary-light py-1 px-4 rounded-md">
                    영업위치 추가하러 가기
                  </button>
                </Link>
              </div>
            </div>
          )}
        </div>
      </main>
    </div>
  )
}
