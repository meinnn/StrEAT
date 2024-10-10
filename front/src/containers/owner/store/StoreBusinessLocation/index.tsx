import Image from 'next/image'
import { IoSettingsOutline } from 'react-icons/io5'
import { IoIosMore } from 'react-icons/io'
import Link from 'next/link'
import { useBusinessLocation } from '@/hooks/useBusinessLocation'
import { useOwnerInfo } from '@/hooks/useOwnerInfo'
import { StoreLocation } from '@/hooks/useMyStoreInfo'

export default function StoreBusinessLocation({
  storeLocations = [],
}: {
  storeLocations: StoreLocation[]
}) {
  const {
    data: ownerInfo,
    error: ownerInfoError,
    isLoading: ownerInfoLoading,
  } = useOwnerInfo()
  const {
    data: businessLocationData,
    error,
    isLoading,
  } = useBusinessLocation(ownerInfo?.storeId)
  const isMore = storeLocations.length > 5

  if (isLoading || ownerInfoLoading) {
    return <p>로딩중</p>
  }

  if (error || ownerInfoError) {
    return <p>에러 발생</p>
  }

  return (
    <section className="mt-11 px-6 flex flex-col gap-2">
      <div className="flex items-center gap-1">
        <h3 className="text-xl font-medium">영업 위치</h3>
        <Link href="/owner/store/setting/business-location">
          <IoSettingsOutline className="cursor-pointer" />
        </Link>
      </div>
      <div className="flex gap-3 flex-wrap">
        {businessLocationData &&
          businessLocationData.length > 0 &&
          businessLocationData
            .slice(0, isMore ? 4 : storeLocations.length)
            .map((location) => {
              return (
                <p
                  key={location.id}
                  className="relative w-16 h-16 shrink-0 aspect-square rounded overflow-hidden bg-gray-medium"
                >
                  <Image
                    src={
                      location.locationPhotos.length > 0
                        ? location.locationPhotos[0].src
                        : '/images/보쌈사진.jpg'
                    }
                    alt="location"
                    fill
                    className="object-cover"
                  />
                </p>
              )
            })}
        {isMore ? (
          <Link href="/owner/store/business-location">
            <div className="cursor-pointer flex flex-col shrink-0 justify-center items-center w-16 h-16 aspect-square rounded overflow-hidden bg-gray-medium">
              <IoIosMore className="w-6 h-6" />
              <p className="text-xs">더보기</p>
            </div>
          </Link>
        ) : null}
      </div>
    </section>
  )
}
