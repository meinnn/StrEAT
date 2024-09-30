'use client'

import { useRouter } from 'next/navigation'
import { useEffect } from 'react'
import { IoIosClose } from 'react-icons/io'

export default function LocationConfirmCancelModal() {
  const router = useRouter()

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
        <p className="text-xs">서울특별시 강남구 테헤란로 212</p>
      </div>
      <div className="w-full flex justify-between gap-2">
        <button
          type="button"
          onClick={() => router.push('/business-location')}
          className="py-2 text-xs font-normal w-full bg-gray-medium rounded-md"
        >
          아니에요
        </button>
        <button
          type="button"
          onClick={() => router.back()}
          className="py-2 text-xs font-normal w-full bg-primary-500 text-secondary-light rounded-md"
        >
          맞아요
        </button>
      </div>
    </div>
  )
}
