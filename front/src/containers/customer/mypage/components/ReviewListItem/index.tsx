'use client'

import { useMutation, useQueryClient } from '@tanstack/react-query'
import { FaStar } from 'react-icons/fa'
import { GoChevronRight } from 'react-icons/go'
import Link from 'next/link'
import Image from 'next/image'
import { TbReceipt } from 'react-icons/tb'
import { useState } from 'react'
import ImageCrousel from '@/components/ImageCarousel'
import DeleteConfirmationModal from '@/containers/customer/mypage/DeleteConfirmationModal'

const deleteReview = async (reviewId: number) => {
  const response = await fetch(`/services/review/${reviewId}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
      'Authorization':
        'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhY2Nlc3MtdG9rZW4iLCJpYXQiOjE3Mjc4MzE0MTQsImV4cCI6MjA4NzgzMTQxNCwidXNlcklkIjoxMn0.UrVrI-WUCXdx017R4uRIl6lzxbktVSfEDjEgYe5J8UQ',
    },
  })

  if (!response.ok) {
    throw new Error('리뷰 삭제에 실패했습니다.')
  }

  return response.json()
}

export default function ReviewListItem({
  reviewId,
  storeId,
  storeName,
  storeImage,
  date,
  orderList,
  content,
  score,
  reviewImageList,
}: {
  reviewId: number
  storeId: number
  storeName: string
  storeImage?: string
  date: string
  orderList: string[]
  content: string
  score: number
  reviewImageList: string[]
}) {
  const queryClient = useQueryClient()

  const mutation = useMutation({
    mutationFn: () => deleteReview(reviewId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['/services/review/me'] })
    },
    onError: (error: Error) => {
      console.error('Error:', error)
      console.error('리뷰 삭제 중 오류가 발생했습니다.')
    },
  })

  const [isModalOpen, setIsModalOpen] = useState(false)

  const handleClickDeleteBtn = () => {
    setIsModalOpen(true)
  }

  const handleConfirmDelete = () => {
    mutation.mutate()
    setIsModalOpen(false)
  }

  const handleCancelDelete = () => {
    setIsModalOpen(false)
  }

  return (
    <section className=" px-5 pb-5 border-b border-gray-medium last:border-b-0 bg-white">
      <DeleteConfirmationModal
        isOpen={isModalOpen}
        onConfirm={handleConfirmDelete}
        onCancel={handleCancelDelete}
      />

      <div className="flex py-3 items-center gap-3 relative">
        <p className="relative shrink-0 w-10 aspect-square rounded-md overflow-hidden border border-gray-medium bg-gray-light">
          <Image
            src={storeImage || '/images/보쌈사진.jpg'}
            alt="음식점"
            fill
            className="object-cover"
          />
        </p>
        <div className="flex flex-col gap-[2px] w-full">
          <div className="flex justify-between items-center w-full">
            <Link
              href={`/customer/stores/${storeId}`}
              className="flex items-center"
            >
              <h5 className="text-sm font-normal">{storeName}</h5>
              <GoChevronRight className="text-text w-4 h-4" />
            </Link>
          </div>
          <div className="flex text-xs text-gray-dark">
            {orderList.slice(0, 1).map((order) => (
              <p
                key={order}
                className="text-gray-dark text-[0.625rem] font-normal"
              >
                {order}
              </p>
            ))}
            {orderList.length > 1 && (
              <p className="text-gray-dark text-[0.625rem] font-normal">
                &nbsp;외 {orderList.length - 1}개
              </p>
            )}
          </div>
        </div>
        <button
          onClick={handleClickDeleteBtn}
          className="absolute right-1 top-5 bg-white text-gray-dark border border-gray-dark px-3 rounded-full text-sm"
        >
          삭제
        </button>
      </div>
      <div className="flex justify-between">
        <div className="flex mb-2">
          {[1, 2, 3, 4, 5].map((star) => (
            <FaStar
              key={star}
              className={`h-5 w-5 ${
                score >= star ? 'text-[#FBC400]' : 'text-gray-medium'
              }`}
            />
          ))}
        </div>
        <p className="text-xs">{date}</p>
      </div>
      {reviewImageList.length > 0 && <ImageCrousel slides={reviewImageList} />}
      <div className="mb-4 mt-2">
        <p className="font-normal text-text">{content}</p>
      </div>
      <div className="flex gap-1 items-start">
        <TbReceipt className="w-6 h-6 flex-shrink-0 mt-1 text-primary-500" />
        <div className="flex flex-wrap gap-2">
          {orderList.map((order) => (
            <span
              key={order}
              className="rounded-full text-sm border py-1 px-3 border-primary-500 text-primary-500"
            >
              {order}
            </span>
          ))}
        </div>
      </div>
    </section>
  )
}
