/* eslint-disable no-alert */

'use client'

import Image from 'next/image'
import { useState } from 'react'
import { GoChevronRight } from 'react-icons/go'
import { FaStar } from 'react-icons/fa'
import Link from 'next/link'
import { useRouter } from 'next/navigation'
import ReviewImageUploader, {
  ImageFile,
} from '@/containers/customer/orders/ReviewImageUploader'

const TASTE_LIST = [
  '맛없어요😫',
  '그저그래요😶',
  '보통이에요🐸',
  '괜찮아요😊',
  '정말 맛있어요🥰',
]

type ApiResponse<T> = {
  message?: string
  error?: string
  data?: T
}

export default function CustomerCreateReview({
  orderId,
  store,
  storeImage,
  orders,
}: {
  orderId: string
  store: string
  storeImage: string
  orders: string[]
}) {
  const router = useRouter()
  const [review, setReview] = useState({
    score: 0,
    content: '',
  })
  const [reviewImageList, setReviewImageList] = useState<ImageFile[]>([])
  const isReviewButtonEnabled = review.score > 0 && review.content.length > 0

  const handleSubmitReview = async () => {
    const formData = new FormData()
    formData.append('score', String(review.score))
    formData.append('content', review.content)
    reviewImageList.forEach((file: { preview: string; file: File }) => {
      formData.append('image', file.file, file.file.name)
    })

    try {
      const response = await fetch(`/services/review?orderId=${orderId}`, {
        method: 'POST',
        body: formData,
      })

      if (response.ok) {
        const data: ApiResponse<{ message: string }> = await response.json()
        router.back()
      } else {
        const statusCode = response.status
        const errorData: ApiResponse<null> = await response.json()
        console.error('Error:', errorData)

        if (statusCode === 400) {
          alert('이미 작성한 리뷰입니다. 리뷰를 작성할 수 없습니다.')
          router.back()
        } else if (statusCode === 401) {
          alert('리뷰를 작성할 권한이 없습니다.')
          router.back()
        } else if (statusCode === 404) {
          alert('주문이 존재하지 않습니다. 다시 시도해주세요')
          router.back()
        }
      }
    } catch (error) {
      console.error('Error:', error)
    }
  }

  return (
    <main className="bg-secondary-light h-full pb-32">
      <section className="flex pt-7 pb-7 px-7 items-center gap-3 border-b-4 border-gray-medium text-text">
        <Link
          href="/customer/stores/1"
          className="relative w-16 shrink-0 aspect-square rounded-md overflow-hidden border border-gray-medium bg-gray-light"
        >
          <Image
            src={storeImage}
            alt="store"
            fill
            className="object-cover "
            priority
          />
        </Link>
        <div className="flex flex-col items-start gap-[2px] w-full justify-center">
          <Link
            href={`/customer/stores/${orderId}`}
            className="flex items-center"
          >
            <h5 className="text-sm font-normal">{store}</h5>
            <GoChevronRight className="text-text w-4 h-4" />
          </Link>
          <div className="text-[10px] flex flex-col">
            {orders.map((order) => {
              return <p key={order}>{order}</p>
            })}
          </div>
        </div>
      </section>
      <div className="px-7 pt-9 flex flex-col gap-8">
        <section className="flex flex-col gap-2 text-text">
          <h4 className="pl-1 font-medium text-lg">음식은 어땠나요?</h4>
          <div className="flex gap-1">
            {[1, 2, 3, 4, 5].map((star, i) => (
              <FaStar
                key={star}
                className={`cursor-pointer h-11 w-11 ${review.score >= star ? 'text-[#FBC400]' : 'text-gray-medium'}`}
                onClick={() => {
                  setReview((prev) => ({ ...prev, score: i + 1 }))
                }}
              />
            ))}
          </div>
          <p className="text-xs pl-1">{TASTE_LIST[review.score - 1]}</p>
        </section>
        <section className="flex flex-col  text-text">
          <h4 className="pl-1 font-medium text-lg mb-4">
            음식에 대한 리뷰를 남겨주세요
          </h4>
          <textarea
            onChange={(e: any) => {
              setReview((prev) => ({ ...prev, content: e.target.value }))
            }}
            className="p-3 mb-2 bg-white border outline-none border-gray-dark rounded-lg w-full resize-none h-48"
          />
          <div className="self-end pr-2">
            <p className="text-text">{review.content.length} 자</p>
          </div>
        </section>
        <section className="flex flex-col gap-4 text-text">
          <h4 className="pl-1 font-medium text-lg">음식 사진을 등록해주세요</h4>
          <ReviewImageUploader
            reviewImageList={reviewImageList}
            setReviewImageList={setReviewImageList}
          />
        </section>
      </div>
      <div className="bg-white w-full bottom-0 fixed p-4 z-10">
        <button
          type="button"
          className={`${isReviewButtonEnabled ? 'bg-primary-500  text-secondary-light' : 'text-gray-dark bg-gray-medium'} z-50 w-full py-4  rounded-lg font-normal`}
          onClick={handleSubmitReview}
        >
          리뷰 등록하기
        </button>
      </div>
    </main>
  )
}
