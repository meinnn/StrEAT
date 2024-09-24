'use client'

import { useState } from 'react'
import Link from 'next/link'
import { IoCloseOutline } from 'react-icons/io5'
import { GoChevronRight } from 'react-icons/go'

const ONGOING_ORDER = [
  {
    store_name: '옐로우 치킨 키친',
    status: '주문 확인',
  },
]

export default function OngoingOrder() {
  const [isVisible, setIsVisible] = useState(true)

  const handleClose = () => {
    setIsVisible(false)
  }

  if (!isVisible) {
    return null
  }

  return (
    <div className="m-6 p-6 fixed top-0 inset-x-0 z-50 bg-white shadow-lg rounded-lg">
      {/* 닫기 버튼 */}
      <button className="float-end hover:text-gray-dark" onClick={handleClose}>
        <IoCloseOutline size={18} />
      </button>

      {/* 진행 중 메시지 */}
      <p className="text-sm text-gray-500 mb-4">
        현재 진행 중인 주문 내역이 있어요
      </p>

      {/* 가게 이름과 주문 상태 */}
      <Link
        href="/customer/orders/1"
        className="flex justify-between items-center"
      >
        <div>
          <p className="text-xl font-bold">{ONGOING_ORDER[0].store_name}</p>
          <p className="text-primary-500 font-semibold">
            {ONGOING_ORDER[0].status}
          </p>
        </div>
        <GoChevronRight size={32} />
      </Link>
    </div>
  )
}
