'use client'

import Image from 'next/image'
import { useState } from 'react'
import { GoChevronRight } from 'react-icons/go'
import { FaStar } from 'react-icons/fa'
import Link from 'next/link'
import ReviewImageUploader from '@/containers/customer/orders/ReviewImageUploader'

const TASTE_LIST = [
  '맛없어요😫',
  '그저그래요😶',
  '보통이에요🙂',
  '괜찮아요😊',
  '정말 맛있어요🥰',
]

export default function CustomerCreateReview({
  store,
  storeImage,
  orders,
}: {
  store: string
  storeImage: string
  orders: string[]
}) {
  const [reviewScore, setReviewScore] = useState(1)

  return (
    <main className="bg-secondary-light h-full pb-32">
      <section className="flex pt-7 pb-7 px-7 items-center gap-3 border-b-4 border-gray-medium text-text">
        <Link
          href="/customer/stores/1"
          className="relative w-20 aspect-square rounded-md overflow-hidden border border-gray-medium bg-gray-light"
        >
          <Image
            src={storeImage}
            alt="store"
            fill
            className="object-cover"
            priority
          />
        </Link>
        <div className="flex flex-col items-start gap-[2px] w-full justify-center">
          <Link href="/customer/stores/1" className="flex items-center">
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
                className={`cursor-pointer h-11 w-11 ${reviewScore >= star ? 'text-[#FBC400]' : 'text-gray-medium'}`}
                onClick={() => {
                  setReviewScore(i + 1)
                }}
              />
            ))}
          </div>
          <p className="text-xs pl-1">{TASTE_LIST[reviewScore - 1]}</p>
        </section>
        <section className="flex flex-col gap-4 text-text">
          <h4 className="pl-1 font-medium text-lg">
            음식에 대한 리뷰를 남겨주세요
          </h4>
          <textarea className="p-3 bg-white border outline-none border-gray-dark rounded-lg w-full resize-none h-48" />
        </section>
        <section className="flex flex-col gap-4 text-text">
          <h4 className="pl-1 font-medium text-lg">음식 사진을 등록해주세요</h4>
          <ReviewImageUploader />
        </section>
      </div>
      <div className="bg-white w-full bottom-0 fixed p-4">
        <button
          type="button"
          className="z-50 w-full py-4 bg-primary-500 rounded-lg text-secondary-light font-normal"
        >
          리뷰 등록하기
        </button>
      </div>
    </main>
  )
}
