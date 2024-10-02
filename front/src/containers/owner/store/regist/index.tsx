'use client'

import React, { useState } from 'react'
import Image from 'next/image'
import { FaArrowRight } from 'react-icons/fa6'
import StoreBusinessSchedulePicker from '@/components/StoreBusinessSchedulePicker'
import AppBar from '@/components/AppBar'

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

export default function StoreRegist() {
  const [storeType, setStoreType] = useState<number | null>(null)
  const [isNext] = useState(false)

  return (
    <div>
      <AppBar title="" />
      <main className="pt-6 flex flex-col pb-20">
        <h1 className="text-primary-950 text-2xl font-semibold px-8 mb-5">
          점포를 등록해주세요
        </h1>
        <section className="flex flex-col">
          <div className="flex flex-col">
            <div className="relative">
              <div className="w-full h-40 relative">
                <Image
                  src="/images/보쌈사진.jpg"
                  className="object-cover"
                  alt="점포 등록 사진"
                  fill
                />
              </div>
              <div className="flex absolute bottom-2 right-2 gap-1">
                <button
                  type="button"
                  className="text-xs py-1 px-5 rounded-lg bg-primary-500 opacity-70 text-secondary-light"
                >
                  이미지 변경
                </button>
                <button
                  type="button"
                  className="text-xs py-1 px-5 rounded-lg bg-primary-500 opacity-70 text-secondary-light"
                >
                  제거
                </button>
              </div>
            </div>
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
                />
              </div>
              <input
                type="text"
                className="outline-none border border-gray-dark rounded-lg py-2 px-3 font-normal text-text"
              />
            </div>
          </div>
        </section>
        <hr className="h-1 bg-gray-medium border-none mb-7" />
        <div className="flex flex-col px-7 h-full gap-6">
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
                      setStoreType(type.id)
                    }}
                    type="button"
                    className={`${type.id === storeType ? 'border-primary-400 text-primary-400' : 'border-gray-dark text-primary-950'} border rounded-2xl w-full py-4`}
                  >
                    {type.name}
                  </button>
                )
              })}
            </div>
          </section>
          <section className="flex flex-col gap-2">
            <h3 className="text-xl text-text font-medium">위치</h3>
            <button
              type="button"
              className="border border-gray-dark text-gray-dark py-3 px-3 rounded-lg text-left"
            >
              위치를 선택해주세요
            </button>
          </section>
          <StoreBusinessSchedulePicker />
          <section className="flex flex-col gap-2 w-full">
            <h3 className="text-xl text-text font-medium">휴무일</h3>
            <textarea className="w-full border border-gray-dark text-gray-dark py-3 px-3 rounded-lg text-left h-20" />
          </section>
        </div>
      </main>
      <button
        type="button"
        className={`${isNext ? 'text-secondary-light bg-primary-500' : 'opacity-70 text-gray-dark bg-gray-medium cursor-default'} gap-1  text-xs flex flex-col fixed bottom-8 right-6 justify-center items-center rounded-full w-20 aspect-square`}
      >
        <FaArrowRight className="w-7 h-7" />
        점포 생성
      </button>
    </div>
  )
}
