import { FaLocationArrow, FaStar } from 'react-icons/fa'
import Image from 'next/image'
import Link from 'next/link'
import { Store } from '@/types/store'
import StoreLikeButton from '@/components/StoreLikeButton'
import { useCallback, useEffect, useState } from 'react'

export interface ReviewSummary {
  reviewTotalCount: number
  averageScore: number
}

export default function StoreListItem({ store }: { store: Store }) {
  const [reviewSummary, setReviewSummary] = useState<ReviewSummary | null>(null)

  // 리뷰 요약을 가져오는 함수
  const fetchReviewSummary = useCallback(async () => {
    try {
      const response = await fetch(`/services/stores/${store.id}/reviews`, {
        method: 'GET',
      })
      const result = await response.json()

      if (response.ok) {
        setReviewSummary(result.data.data)
      } else {
        console.error(result.message)
      }
    } catch (error) {
      console.error('리뷰 요약을 가져오는 중 오류 발생:', error)
    }
  }, [store.id])

  useEffect(() => {
    fetchReviewSummary().then()
  }, [fetchReviewSummary, store.id])

  return (
    <div className="flex items-center justify-between p-4 h-full w-full">
      <Link
        href={`/customer/stores/${store.id}`}
        className="flex items-center overflow-hidden"
      >
        {/* 음식점 이미지 */}
        <Image
          src={store.src || '/images/default_img.jpg'}
          alt={store.storeName}
          width={64}
          height={64}
          className="object-cover w-16 h-16 rounded-full mr-4"
        />

        {/* 음식점 정보 */}
        <div className="truncate">
          {store.categories.map((category) => (
            <span key={category} className="text-xs text-gray-dark me-1">
              #{category}
            </span>
          ))}
          <h3 className="text-lg font-semibold">{store.storeName}</h3>
          <div className="flex items-center text-xs mt-1">
            <FaStar className="text-yellow-400 mr-1" />
            <span>
              {reviewSummary && reviewSummary.averageScore?.toFixed(1)}
            </span>
            <span className="mx-2">|</span>
            <FaLocationArrow className="mr-1" size={12} />
            <span>{store.distance}m</span>
          </div>
        </div>
      </Link>

      <div className="ml-4 mb-auto">
        <StoreLikeButton storeId={store.id} />
      </div>
    </div>
  )
}
