import Image from 'next/image'
import StoreBusinessLocation from '@/containers/owner/store/StoreBusinessLocation'
import StoreBusinessHours from '@/containers/owner/store/StoreBusinessHours'
import StoreInformation from '@/containers/owner/store/StoreInformation'
import { StoreBusinessDays, useMyStoreInfo } from '@/hooks/useMyStoreInfo'
import { useOwnerInfo } from '@/hooks/useOwnerInfo'
import StoreSkeleton from '@/components/skeleton/StoreSkeleton'

export default function Store() {
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

  const parseHours = (data: StoreBusinessDays) => {
    const DAYS = [
      {
        id: 'monday',
        label: '월',
      },
      {
        id: 'tuesday',
        label: '화',
      },
      {
        id: 'wednesday',
        label: '수',
      },
      {
        id: 'thursday',
        label: '목',
      },
      {
        id: 'friday',
        label: '금',
      },
      {
        id: 'saturday',
        label: '토',
      },
      {
        id: 'sunday',
        label: '일',
      },
    ]

    const parsedHours = []

    // eslint-disable-next-line no-plusplus
    for (let i = 0; i < DAYS.length; i++) {
      const day = DAYS[i].id
      const tempStart = data[`${day}Start` as keyof StoreBusinessDays] as string
      const tempEnd = data[`${day}End` as keyof StoreBusinessDays] as string

      if (tempStart.length === 0 && tempEnd.length === 0)
        // eslint-disable-next-line no-continue
        continue

      const start = tempStart
      const end = tempEnd

      parsedHours.push({
        day: DAYS[i].label,
        startTime: start,
        endTime: end,
      })
    }

    return parsedHours
  }

  if (isLoading || ownerInfoLoading) {
    return <StoreSkeleton />
  }

  if (error || ownerInfoError) {
    return <div>에러 발생</div>
  }

  return (
    <main className="pb-8 flex flex-col">
      {storeData ? (
        <>
          <div className="relative w-full h-40 overflow-hidden bg-gray-medium">
            <Image
              src={
                storeData?.storeImage && storeData?.storeImage?.length > 0
                  ? storeData.storeImage[0].src
                  : '/images/보쌈사진.jpg'
              }
              alt="store"
              fill
              className="object-cover"
            />
          </div>
          <StoreInformation
            name={storeData.storeInfo.name}
            status={storeData.storeInfo.status}
            address={storeData.storeInfo.address}
            phoneNum={storeData.storeInfo.storePhoneNumber}
            ownerWord={storeData.storeInfo.ownerWord}
            type={storeData.storeInfo.type}
            review={storeData.storeReview}
          />
          <StoreBusinessLocation storeLocations={storeData.storeLocations} />
          <StoreBusinessHours
            businessHours={parseHours(storeData.storeBusinessDays)}
            closedDays={storeData.storeInfo.closedDays}
          />
        </>
      ) : null}
    </main>
  )
}
