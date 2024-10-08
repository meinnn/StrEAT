'use client'

import { useState } from 'react'
import { RiHeart3Fill, RiHeart3Line } from 'react-icons/ri'
import { FaStar } from 'react-icons/fa'
import { GoChevronRight } from 'react-icons/go'
import { GrLocation } from 'react-icons/gr'
import { useRouter } from 'next/navigation'
import BackButtonWithImage from '@/components/BackButtonWithImage'

interface Store {
  id: string
  name: string
  address: string
  ownerWord: string
  status: string
  storePhotos: string[]
  categories: string[]
}

export default function StoreDetails({ store }: { store: Store }) {
  const [isLiked, setIsLiked] = useState(false)
  const router = useRouter()

  const toggleLike = () => {
    setIsLiked(!isLiked) // 좋아요 상태 토글
  }

  return (
    <div>
      <BackButtonWithImage
        src={store.storePhotos[0] || '/images/default_img.jpg'}
        alt="가게 사진"
        title={store.name}
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
      <div className="m-5">
        <div>
          {/* 음식 카테고리 API에서 받아와야 함 */}
          {store.categories.map((category) => (
            <span key={category} className="text-xs mb-1 me-1">
              #{category}
            </span>
          ))}
          <h1 className="text-2xl font-bold">{store.name}</h1>
          <div className="flex items-center mt-1 gap-2">
            <div className="flex items-center gap-0.5">
              <FaStar className="text-yellow-400" />
              <span>4.9</span> {/* 별점 데이터도 API에서 받아와야 함 */}
            </div>
            <span className="text-gray-dark">·</span>
            <div className="flex items-center">
              <span>리뷰 333개</span> {/* 리뷰 수 API에서 받아와야 함 */}
              <GoChevronRight />
            </div>
          </div>
          <div className="flex gap-2 mt-2">
            {store.status === '영업중' ? (
              <span className="px-3 py-1 bg-green-100 text-green-700 rounded-lg text-sm font-medium">
                영업 중
              </span>
            ) : (
              <span className="px-3 py-1 bg-rose-100 text-rose-700 rounded-lg text-sm font-medium">
                준비 중
              </span>
            )}
          </div>
          <div className="flex justify-between items-center mt-2 ">
            <div className="flex items-center">
              <GrLocation size={14} className="text-primary-500 mr-1" />
              <p className="text-xs">{store.address}</p> {/* 가게의 주소 */}
            </div>
            <button
              type="button"
              onClick={() => router.push(`/customer/stores/${store.id}/info`)}
              className="border border-primary-200 text-primary-500 rounded-full px-4 py-0.5 text-xs"
            >
              가게 정보 보기
            </button>
          </div>
        </div>

        <div className="mt-6 bg-secondary p-3 rounded-lg">
          <p className="text-sm">
            <span className="mx-1">📢</span>
            {store.ownerWord}
          </p>
        </div>
      </div>
    </div>
  )
}
