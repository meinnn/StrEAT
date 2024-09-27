// 주문정보 보기

import { useState } from 'react'
import OrderInfoModal from '@/containers/owner/order-management/OrderInfo/OrderInfoModal'

export default function OrderInfoBtn() {
  const [isModalOpen, setIsModalOpen] = useState(false)

  const openOrderInfoModal = () => {
    setIsModalOpen(true)
  }

  const closeOrderInfoModal = () => {
    setIsModalOpen(false)
  }

  return (
    <div>
      <button
        type="button"
        onClick={openOrderInfoModal}
        className="whitespace-nowrap flex justify-center items-center bg-white h-20 w-14 text-sm font-bold text-black px-3 py-2 rounded-md shadow-md"
      >
        주문 정보
        <br />
        보기
      </button>

      {isModalOpen && <OrderInfoModal onClose={closeOrderInfoModal} />}
    </div>
  )
}
