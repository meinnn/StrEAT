'use client'

import Image from 'next/image'
import { GoPencil } from 'react-icons/go'
import { useEffect, useState } from 'react'
import Link from 'next/link'
import AppBar from '@/components/AppBar'
import { useMyStoreInfo } from '@/hooks/useMyStoreInfo'
import { useOwnerInfo } from '@/hooks/useOwnerInfo'

const STORE_TYPE = [
  {
    id: 1,
    name: '고정형',
  },
  {
    id: 2,
    name: '이동형',
  },
]

export default function OwnerStoreSettingPage() {
  const {
    data: ownerInfo,
    error: ownerInfoError,
    isLoading: ownerInfoLoading,
  } = useOwnerInfo()
  const {
    data: storeInfo,
    error,
    isLoading,
  } = useMyStoreInfo(ownerInfo?.storeId)
  const [storeType, setStoreType] = useState<string | null | undefined>(
    storeInfo?.storeInfo?.type
  )

  if (ownerInfoLoading || isLoading) return <p>로딩중</p>
  if (ownerInfoError || error) return <p>에러 발생</p>

  // eslint-disable-next-line react-hooks/rules-of-hooks
  useEffect(() => {
    setStoreType(storeInfo?.storeInfo?.type)
  }, [storeInfo?.storeInfo?.type])

  return (
    <div>
      <AppBar title="점포 설정" />
      <form>
        <div className="relative w-full h-40">
          <Image
            src={
              storeInfo?.storeImage[0].src
                ? storeInfo?.storeImage[0].src
                : '/images/보쌈사진.jpg'
            }
            alt="점포 사진"
            fill
            className="object-cover"
            draggable={false}
          />
          <button
            type="button"
            className="absolute bottom-3 right-3 bg-white flex gap-1 items-center px-2 rounded-lg py-1 text-xs font-medium"
          >
            <GoPencil />
            점포 이미지 변경
          </button>
        </div>
        <div className="text-text flex flex-col gap-5 px-7 py-7">
          <div className="flex flex-col gap-2">
            <h2 className="text-xl font-medium">점포명</h2>
            <input
              type="text"
              className="border rounded-lg py-2 px-3"
              value={storeInfo?.storeInfo.name}
            />
          </div>
          <div className="flex flex-col gap-2">
            <h3 className="text-xl text-text font-medium">점포유형</h3>
            <div className="flex gap-3 w-full">
              {STORE_TYPE.map((type) => {
                return (
                  <button
                    key={type.id}
                    onClick={() => {
                      setStoreType(type.name)
                    }}
                    type="button"
                    className={`${type.name === storeType ? 'border-primary-400 text-primary-400' : 'border-gray-dark text-primary-950'} border rounded-2xl w-full py-4`}
                  >
                    {type.name}
                  </button>
                )
              })}
            </div>
          </div>
          <div className="flex flex-col gap-2">
            <h2 className="text-xl font-medium">점포 번호</h2>
            <input
              type="text"
              className="border rounded-lg py-2 px-3"
              value={storeInfo?.storeInfo.storePhoneNumber}
            />
          </div>
          <button className="bg-primary-500 text-secondary-light py-4 w-full rounded-lg">
            점포 정보 수정하기
          </button>
        </div>
      </form>
      <hr className="bg-gray-medium border-none h-1" />
      <section className="flex flex-col items-start text-text px-7 gap-8 mt-10">
        <Link href="/owner/store/setting/business-location">
          <button type="button" className="text-xl font-medium">
            영업 위치 수정
          </button>
        </Link>
        <Link href="/owner/store/setting/business-hours">
          <button type="button" className="text-xl font-medium">
            영업일 및 영업시간 수정
          </button>
        </Link>
      </section>
    </div>
  )
}
