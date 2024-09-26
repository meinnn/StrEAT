'use client'

import LocationPicker from '@/components/LocationPicker'
import StoreBusinessSchedulePicker from '@/components/StoreBusinessSchedulePicker'
import React, { useState } from 'react'
import { HiArrowLongLeft } from 'react-icons/hi2'

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

export default function StoreRegist() {
  const [storeType, setStoreType] = useState<number | null>(null)

  return (
    <div className="bg-white h-screen">
      <div className="min-h-screen relative pt-16 flex flex-col gap-8 font-medium mx-auto  max-w-xl w-full bg-white h-full">
        <HiArrowLongLeft className="text-primary-950 w-6 h-6 absolute top-4 left-4" />
        <div className="flex flex-col">
          <h1 className="text-primary-950 text-2xl font-semibold px-8 pb-5">
            점포를 등록해주세요
          </h1>
          <div className="flex flex-col gap-6 pt-4">
            <img
              src="/images/보쌈사진.jpg"
              className="h-40 w-full object-cover"
            />
            <div className="flex flex-col gap-2 px-8">
              <div className="flex justify-between items-center">
                <h3 className="text-xl font-medium">점포명</h3>
                <label
                  htmlFor="storeImage"
                  className="bg-primary-950 text-white py-1 px-2 rounded-lg text-xs"
                >
                  점포 이미지 등록
                </label>
                <input type="file" id="storeImage" className="hidden"></input>
              </div>
              <input
                type="text"
                className="outline-none border border-gray-dark rounded-lg py-2 px-3 font-normal text-text"
              />
            </div>
          </div>
        </div>
        <hr className="h-1 bg-gray-medium border-none" />
        <div className="flex flex-col px-8 h-full gap-6">
          <div className="flex flex-col gap-2">
            <h3 className="text-xl">점포유형</h3>
            <div className="flex gap-3 w-full">
              {STORE_TYPE.map((type) => {
                return (
                  <button
                    key={type.id}
                    onClick={() => {
                      setStoreType(type.id)
                    }}
                    type="button"
                    className={`${type.id === storeType ? 'border-primary-400 text-primary-400' : 'border-primary-950 text-primary-950'} border rounded-2xl w-full py-4`}
                  >
                    {type.name}
                  </button>
                )
              })}
            </div>
          </div>
          <div className="flex flex-col gap-2">
            <h3 className="text-xl">위치</h3>
            <button className="border border-gray-dark text-gray-dark py-3 px-3 rounded-lg text-left">
              위치를 선택해주세요
            </button>
          </div>
        </div>

        <StoreBusinessSchedulePicker />
      </div>

      <LocationPicker />
    </div>
  )
}
