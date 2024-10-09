'use client'

import { FaStar } from 'react-icons/fa'
import { GoChevronRight } from 'react-icons/go'
import { GrLocation } from 'react-icons/gr'
import { useRouter } from 'next/navigation'
import BackButtonWithImage from '@/components/BackButtonWithImage'
import StoreLikeButton from '@/components/StoreLikeButton'
import Link from 'next/link'

interface Store {
  id: number
  name: string
  address: string
  ownerWord: string
  status: string
  storePhotos: string[]
  categories: string[]
  reviewTotalCount: number
  averageScore: number
}

export default function StoreDetails({ store }: { store: Store }) {
  const router = useRouter()

  return (
    <div>
      <BackButtonWithImage
        src={store.storePhotos[0] || '/images/default_img.jpg'}
        alt="가게 사진"
        title={store.name}
      />
      <div className="relative">
        <div className="absolute top-8 right-4 bg-white p-2">
          <StoreLikeButton storeId={store.id} />
        </div>
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
              <span>{store.averageScore?.toFixed(1)}</span>
            </div>
            <span className="text-gray-dark">·</span>
            <div className="flex items-center">
              {/* 리뷰 목록 조회로 이동 */}
              <Link href={`/customer/stores/${store.id}/reviews`}>
                {`리뷰 ${store.reviewTotalCount}개`}
              </Link>
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
