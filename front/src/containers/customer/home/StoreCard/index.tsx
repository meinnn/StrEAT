'use client'

import { FaStar, FaLocationArrow } from 'react-icons/fa'
import { RiHeart3Line, RiHeart3Fill } from 'react-icons/ri'
import Image from 'next/image'
import { useState } from 'react'
import Link from 'next/link'

export default function StoreCard() {
  const [isLiked, setIsLiked] = useState(false)

  const toggleLike = () => {
    setIsLiked(!isLiked) // 좋아요 상태 토글
  }

  return (
    <Link
      href="/customer/stores/1"
      className="relative bg-[#371B1B] text-white rounded-xl p-5 min-w-64 h-28"
    >
      {/* 음식점 이미지 */}
      <div className="h-full w-full flex items-center">
        <div className="w-16 h-16 rounded-full overflow-hidden bg-gray-medium">
          <Image
            src="/store.jpg"
            alt="음식점 이미지"
            width={64}
            height={64}
            className="object-cover"
          />
        </div>

        <div className="ml-4 flex-grow">
          <p className="text-xs">#분식</p>
          <h3 className="text-lg font-semibold">조은식당</h3>
          <div className="flex justi items-center text-xs mt-1">
            <FaStar className="text-yellow-400 mr-1" />
            <span>4.8</span>
            <span className="mx-2">|</span>
            <FaLocationArrow className="mr-1" size={10} />
            <span>10m</span>
          </div>
        </div>
      </div>

      <button
        type="button"
        onClick={toggleLike}
        className="absolute top-3 right-3"
      >
        {isLiked ? (
          <RiHeart3Fill className="text-primary-500" size={24} />
        ) : (
          <RiHeart3Line className="text-primary-500" size={24} />
        )}
      </button>
    </Link>
  )
}
