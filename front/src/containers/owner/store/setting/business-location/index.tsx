'use client'

import Image from 'next/image'
import { useState } from 'react'
import { HiOutlineLocationMarker } from 'react-icons/hi'
import Link from 'next/link'
import { useQueryClient } from '@tanstack/react-query'
import AppBar from '@/components/AppBar'
import { useOwnerInfo } from '@/hooks/useOwnerInfo'
import { useBusinessLocation } from '@/hooks/useBusinessLocation'

export default function OwnerStoreSettingBusinessLocation() {
  const queryClient = useQueryClient()
  const [checkedItems, setCheckedItems] = useState(new Array(8).fill(false))
  const checkedCount = checkedItems.filter(Boolean).length
  const [selectedLocations, setSelectedLocations] = useState<number[]>([])

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

  const handleCheckboxChange = (locationId: number, index: number) => {
    const updatedCheckedItems = [...checkedItems]
    updatedCheckedItems[index] = !updatedCheckedItems[index]
    setCheckedItems(updatedCheckedItems)

    const nSelectedLocations = [...selectedLocations]

    if (nSelectedLocations.includes(locationId)) {
      const selectedIndex = nSelectedLocations.indexOf(locationId)
      nSelectedLocations.splice(selectedIndex, 1)
    } else {
      nSelectedLocations.push(locationId)
    }

    setSelectedLocations(nSelectedLocations)
  }

  const handleClickDeleteBtn = async () => {
    const response = await fetch(
      `/services/store/${ownerInfo?.storeId}/business-location`,
      {
        method: 'DELETE',
        body: JSON.stringify(selectedLocations),
      }
    )
    if (!response.ok) {
      console.error('영업 위치 리스트 삭제에 실패했습니다.')
    }

    queryClient.invalidateQueries({
      queryKey: ['/store/business-location', ownerInfo?.storeId],
    })
    setCheckedItems((pre) => pre.fill(false))
  }

  return (
    <section>
      <AppBar title="점포 설정" />
      <main className="flex flex-col gap-3 px-6 py-5">
        <div className="flex justify-between items-center">
          <h3 className="pl-2 text-xl font-medium ">영업 위치</h3>
          <Link href="/register-business-location">
            <button className="text-xs px-4 py-1 font-normal bg-primary-500 rounded-md text-secondary-light">
              영업 위치 추가하기
            </button>
          </Link>
        </div>
        <div className="flex justify-center items-start w-full">
          <div className="flex flex-col w-full gap-2">
            {storeBusinessLocationData &&
              storeBusinessLocationData.length > 0 &&
              storeBusinessLocationData.map((storeBusinessLocation, index) => {
                return (
                  <div
                    key={storeBusinessLocation.id}
                    onClick={() =>
                      handleCheckboxChange(storeBusinessLocation.id, index)
                    }
                    className="hover:bg-secondary-medium cursor-pointer p-4 shadow-md relative w-full overflow-hidden border border-gray-medium bg-white rounded-lg"
                  >
                    <input
                      type="checkbox"
                      checked={checkedItems[index]}
                      onChange={() =>
                        handleCheckboxChange(storeBusinessLocation.id, index)
                      }
                      className="cursor-pointer accent-primary-500 bg-white border-gray-dark w-6 h-6 absolute z-10 rounded-md top-2 left-2"
                    />
                    <div className="w-full flex items-center gap-4">
                      <div className="border border-gray-medium relative w-16 aspect-square rounded-md overflow-hidden">
                        <Image
                          src={
                            storeBusinessLocation.locationPhotos[0]
                              ? storeBusinessLocation.locationPhotos[0].src
                              : '/images/보쌈사진.jpg'
                          }
                          alt="store-location"
                          fill
                          className="absolute object-cover"
                          priority
                        />
                      </div>
                      <div className="flex flex-col gap-2">
                        <p className="font-semibold">
                          {storeBusinessLocation.nickname}
                        </p>
                        <div className="flex gap-1 items-start">
                          <HiOutlineLocationMarker className="w-5 h-5 text-primary-500" />
                          <p>{storeBusinessLocation.address}</p>
                        </div>
                      </div>
                    </div>
                  </div>
                )
              })}
          </div>
          <div className="flex gap-2 self-center p-4 fixed bottom-0 w-full bg-white z-10">
            {checkedCount >= 1 &&
              (checkedCount > 1 ? (
                <button
                  onClick={handleClickDeleteBtn}
                  className="w-full py-4 bg-primary-500 text-secondary-light rounded-lg font-normal"
                >
                  삭제하기
                </button>
              ) : (
                <>
                  <button className="w-full py-4 outline outline-primary-500 text-primary-500 rounded-lg font-normal">
                    수정하기
                  </button>
                  <button
                    onClick={handleClickDeleteBtn}
                    className="w-full py-4  bg-primary-500 text-secondary-light rounded-lg font-normal"
                  >
                    삭제하기
                  </button>
                </>
              ))}
          </div>
        </div>
      </main>
    </section>
  )
}
