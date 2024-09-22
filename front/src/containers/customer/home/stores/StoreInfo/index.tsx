'use client'

import { useState } from 'react'
import { RiHeart3Fill, RiHeart3Line } from 'react-icons/ri'
import Image from 'next/image'
import { FaStar } from 'react-icons/fa'
import { GoChevronRight } from 'react-icons/go'
import { GrLocation } from 'react-icons/gr'
import MenuList from '@/containers/customer/home/stores/MenuList'
import CartButton from '@/containers/customer/home/stores/CartButton'
import BackButton from '@/components/BackButton'

export default function StoreInfo() {
  const [isLiked, setIsLiked] = useState(false)

  const toggleLike = () => {
    setIsLiked(!isLiked) // 좋아요 상태 토글
  }

  return (
    <div>
      <BackButton />
      {/* 가게 정보 */}
      <Image
        src="/images/store_image.png"
        alt="가게 사진"
        width={0}
        height={0}
        className="w-full h-48 object-cover bg-gray-medium"
      />

      <div className="relative">
        <button
          type="button"
          onClick={toggleLike}
          className="absolute top-8 right-4 bg-white p-2 rounded-full"
        >
          {isLiked ? (
            <RiHeart3Fill className="text-primary-500" size={24} />
          ) : (
            <RiHeart3Line className="text-primary-500" size={24} />
          )}
        </button>
      </div>

      <div className="p-5">
        <div>
          <p className="text-xs mb-1">#치킨</p>
          <h1 className="text-2xl font-bold">옐로우 키친 치킨</h1>

          <div className="flex items-center mt-1 gap-2">
            <div className="flex items-center gap-0.5">
              <FaStar className="text-yellow-400" />
              <span>4.9</span>
            </div>
            <span className="text-gray-dark">·</span>
            <div className="flex items-center">
              <span>리뷰 333개</span>
              <GoChevronRight />
            </div>
          </div>

          <div className="flex gap-2 mt-2">
            <span className="px-3 py-1 bg-green-100 text-green-700 rounded-lg text-sm font-medium">
              영업 중
            </span>
            <span className="px-3 py-1 bg-rose-100 text-rose-700 rounded-lg text-sm font-medium">
              준비 중
            </span>
          </div>

          <div className="flex justify-between items-center mt-2 ">
            <div className="flex items-center">
              <GrLocation size={14} className="text-primary-500 mr-1" />
              <p className="text-xs">서울특별시 강남구 테헤란로 212</p>
            </div>
            <button
              type="button"
              className="border border-primary-200 text-primary-500 rounded-full px-4 py-0.5 text-xs"
            >
              가게 정보 · 원산지
            </button>
          </div>
        </div>

        <div className="mt-6 bg-secondary p-3 rounded-lg">
          <p className="text-sm">
            <span className="mx-1">📢</span>
            역삼역 1번 출구 앞 건물 뒤에 있습니다! 오늘 주방장 폼 미침
          </p>
        </div>

        <MenuList />
      </div>
    </div>
  )
}
