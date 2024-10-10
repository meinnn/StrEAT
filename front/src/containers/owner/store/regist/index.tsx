'use client'

import React, { useState } from 'react'
import Image from 'next/image'
import { FaArrowRight } from 'react-icons/fa6'
import { useRouter } from 'next/navigation'
import { GoPencil } from 'react-icons/go'
import StoreBusinessSchedulePicker from '@/components/StoreBusinessSchedulePicker'
import AppBar from '@/components/AppBar'
import { ImageFile } from '@/containers/customer/orders/ReviewImageUploader'
import { useStoreLocation } from '@/contexts/StoreLocationContext'
import { useStoreReigstInfo } from '@/contexts/storeRegistContext'

type ApiResponse<T> = {
  message?: string
  error?: string
  data?: T
}

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

const STORE_INDUSTRY = [
  '휴게음식점영업',
  '일반음식점영업',
  '단란주점영업',
  '유흥주점영업',
  '위탁급식영업',
  '제과점',
]

interface Store {
  businessRegistrationNumber: string | null
  name: string
  address: string
  latitude?: number
  longitude?: number
  type: string
  bankAccount: string
  bankName: string
  ownerWord: string
  storePhoneNumber: string
  closedDays: string
  status: string
  subcategoryId: number
}

export default function StoreRegist() {
  const router = useRouter()
  const { storeRegistInfo } = useStoreReigstInfo()

  const [storeInfo, setStoreInfo] = useState<Store>({
    businessRegistrationNumber: storeRegistInfo.businessRegistrationNumber,
    name: '',
    address: '',
    latitude: undefined,
    longitude: undefined,
    type: '',
    bankAccount: '1234-5678-9012-3456',
    bankName: '신한은행',
    ownerWord: '',
    storePhoneNumber: '',
    closedDays: '',
    status: '준비중',
    subcategoryId: 1,
  })
  const [storeImage, setStoreImage] = useState<ImageFile | null>(null)
  const { storeLocation } = useStoreLocation()
  const isNext =
    storeImage &&
    storeInfo.businessRegistrationNumber &&
    storeInfo.name.length > 0 &&
    storeLocation.address &&
    storeLocation.latitude &&
    storeLocation.longitude &&
    storeInfo.bankAccount.length > 0 &&
    storeInfo.bankName.length > 0 &&
    storeInfo.subcategoryId

  const [businessDays, setBusinessDays] = useState<any>(null) // 영업일 및 영업시간
  const isCorrect = false // 점포 생성을 모두 완료했는지 여부
  const businessDaysCount = // 영업일 및 영업시간 선택한 개수 -> 2이상일 경우 선택한것임
    businessDays &&
    (Object.values(businessDays) as string[]).filter(
      (value) => value && value.length > 1
    ).length

  const handleChangeFile = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0]
    if (file) {
      const preview = URL.createObjectURL(file)
      setStoreImage({ preview, file })
    }
  }
  // 점포 등록 (기본 점포 정보 + 영업일 및 영업시간)
  const registStore = async () => {
    if (
      !storeLocation ||
      !storeLocation.latitude ||
      !storeLocation.longitude ||
      !storeLocation.address
    )
      return

    storeInfo.address = storeLocation.address
    storeInfo.latitude = storeLocation.latitude
    storeInfo.longitude = storeLocation.longitude

    const response = await fetch(`/services/store`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        storeInfo,
        businessDays,
      }),
    })

    if (response.ok) {
      console.log('점포 생성에 성공했습니다.')
    } else {
      const statusCode = response.status
      const errorData: ApiResponse<null> = await response.json()
      console.error('Error:', errorData.error)

      if (statusCode === 400) {
        console.error('점포가 이미 존재합니다.')
      } else {
        console.error('에러가 발생했습니다. 다시 시도해주세요')
      }
    }
  }
  // 점포 이미지 등록
  const uploadStorePhoto = async () => {
    if (!storeImage) return
    const formData = new FormData()
    formData.append('images', storeImage.file, storeImage.file.name)

    const response = await fetch(`/services/store/store-photo`, {
      method: 'POST',
      body: formData,
    })

    if (response.ok) {
      console.log('점포 이미지 업로드에 성공')
      router.push('/owner/store')
    } else {
      console.log('점포 이미지 업로드에 실패했습니다.')
    }
  }

  // 점포 등록할 때 필수값
  // 점포명, 업종, 유형, 위치(고정형일 때만), 영업일 및 영업시간

  return (
    <div>
      <AppBar title="" />
      <main className="pt-6 flex flex-col pb-20">
        <h1 className="text-primary-950 text-2xl font-semibold px-8 mb-5">
          점포를 등록해주세요
        </h1>
        <section className="flex flex-col">
          <div className="flex flex-col">
            {storeImage ? (
              <div className="relative">
                <div className="w-full h-40 relative">
                  <Image
                    src={storeImage.preview || ''}
                    className="object-cover"
                    alt="점포 등록 사진"
                    fill
                  />
                </div>
                <div className="flex absolute bottom-2 right-2 gap-1">
                  <label
                    htmlFor="storeImage"
                    className="flex gap-1 items-center cursor-pointer text-xs py-1 px-5 rounded-lg bg-white text-text border border-text"
                  >
                    <GoPencil className="h-3 w-3" />
                    이미지 변경
                  </label>
                  <input
                    type="file"
                    id="storeImage"
                    className="hidden text-text"
                    onChange={handleChangeFile}
                  />
                  <button
                    onClick={() => setStoreImage(null)}
                    type="button"
                    className="text-xs py-1 px-5 rounded-lg bg-white text-text border border-text"
                  >
                    제거
                  </button>
                </div>
              </div>
            ) : null}

            <div className="flex flex-col gap-2 px-7 pt-5 pb-8">
              <div className="flex justify-between items-center">
                <h3 className="text-xl font-medium text-text">점포명</h3>
                <label
                  htmlFor="storeImage"
                  className="bg-primary-950 text-white py-1 px-2 rounded-lg text-xs cursor-pointer"
                >
                  점포 이미지 등록
                </label>
                <input
                  type="file"
                  id="storeImage"
                  className="hidden text-text"
                  onChange={handleChangeFile}
                />
              </div>
              <input
                onChange={(e) => {
                  setStoreInfo((pre) => ({ ...pre, name: e.target.value }))
                }}
                type="text"
                className="outline-none border border-gray-dark rounded-lg py-2 px-3 font-normal text-text"
              />
            </div>
          </div>
        </section>
        <hr className="h-1 bg-gray-medium border-none mb-7" />
        <div className="flex flex-col px-7 h-full gap-6">
          <section className="flex flex-col gap-2">
            <h3 className="text-xl font-medium text-text">점포 전화번호</h3>
            <input
              onChange={(e) => {
                setStoreInfo((pre) => ({
                  ...pre,
                  storePhoneNumber: e.target.value,
                }))
              }}
              type="text"
              className="outline-none border border-gray-dark rounded-lg py-2 px-3 font-normal text-text"
            />
          </section>
          <section className="flex flex-col gap-2">
            <h3 className="text-xl text-text font-medium">점포 업종</h3>
            <div className="flex justify-start gap-3 w-full flex-wrap">
              {STORE_INDUSTRY.map((industry) => {
                return (
                  <span
                    key={industry}
                    className="border rounded-2xl py-1 px-2 border-gray-dark cursor-pointer"
                  >
                    {industry}
                  </span>
                )
              })}
            </div>
          </section>
          <section className="flex flex-col gap-2">
            <h3 className="text-xl text-text font-medium">점포유형</h3>
            <div className="flex gap-3 w-full">
              {STORE_TYPE.map((type) => {
                return (
                  <button
                    key={type.id}
                    onClick={() => {
                      setStoreInfo((pre) => ({ ...pre, type: type.name }))
                    }}
                    type="button"
                    className={`${type.name === storeInfo.type ? 'border-primary-400 text-primary-400' : 'border-gray-dark text-primary-950'} border rounded-2xl w-full py-4`}
                  >
                    {type.name}
                  </button>
                )
              })}
            </div>
          </section>
          {storeInfo.type === '고정형' ? (
            <section className="flex flex-col gap-2">
              <h3 className="text-xl text-text font-medium">위치</h3>
              <button
                onClick={() => router.push('/business-location')}
                type="button"
                className="border border-gray-dark text-gray-dark py-3 px-3 rounded-lg text-left"
              >
                {storeLocation.address
                  ? storeLocation.address
                  : '위치를 선택해주세요'}
              </button>
            </section>
          ) : null}

          <StoreBusinessSchedulePicker setBusinessDays={setBusinessDays} />
          <section className="flex flex-col gap-2 w-full">
            <h3 className="text-xl text-text font-medium">휴무일</h3>
            <textarea
              onChange={(e) => {
                setStoreInfo((pre) => ({ ...pre, closedDays: e.target.value }))
              }}
              className="resize-none w-full border border-gray-dark text-text py-3 px-3 rounded-lg text-left h-20"
            />
          </section>
        </div>
      </main>
      <button
        onClick={() => {
          registStore()
          uploadStorePhoto()
        }}
        type="button"
        className={`${isNext ? 'text-secondary-light bg-primary-500' : 'opacity-70 text-gray-dark bg-gray-medium cursor-default'} gap-1  text-xs flex flex-col fixed bottom-8 right-6 justify-center items-center rounded-full w-20 aspect-square`}
      >
        <FaArrowRight className="w-7 h-7" />
        점포 생성
      </button>
    </div>
  )
}
