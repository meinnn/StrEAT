'use client'

import { useEffect, useState } from 'react'
import Link from 'next/link'
import { IoCloseOutline } from 'react-icons/io5'
import { GoChevronRight } from 'react-icons/go'

interface Order {
  ordersId: number
  status:
    | 'REJECTED'
    | 'WAITING_FOR_PROCESSING'
    | 'PROCESSING'
    | 'WAITING_FOR_RECEIPT'
    | 'RECEIVED'
  storeName: string
}

const STATUS_MAP: { [key in Order['status']]: string } = {
  REJECTED: '주문 거절',
  WAITING_FOR_PROCESSING: '주문 요청',
  PROCESSING: '주문 확인',
  WAITING_FOR_RECEIPT: '픽업 대기',
  RECEIVED: '픽업 완료',
}

async function fetchOngoingOrders(): Promise<Order[]> {
  const response = await fetch('/services/order/ongoing')

  if (!response.ok) {
    throw new Error('Failed to fetch ongoing orders')
  }

  const data = await response.json()
  return data.orderList
}

export default function OngoingOrder() {
  const [isVisible, setIsVisible] = useState(true)
  const [orders, setOrders] = useState<Order[]>([])

  useEffect(() => {
    async function getOrders() {
      try {
        const ongoingOrders = await fetchOngoingOrders()
        setOrders(ongoingOrders)
      } catch (error) {
        console.error('Error fetching ongoing orders:', error)
      }
    }

    getOrders().then()
  }, [])

  const handleClose = () => {
    setIsVisible(false)
  }

  if (!isVisible || orders.length === 0) {
    return null
  }

  return (
    <div className="m-6 p-6 fixed top-0 inset-x-0 z-[300] bg-white shadow-lg rounded-lg">
      {/* 닫기 버튼 */}
      <button
        type="button"
        className="float-end hover:text-gray-dark"
        onClick={handleClose}
      >
        <IoCloseOutline size={18} />
      </button>

      {/* 진행 중 메시지 */}
      <p className="text-sm text-primary-950">
        현재 진행 중인 주문 내역이 있어요
      </p>

      {/* 가게 이름과 주문 상태 */}
      {orders.map((order) => (
        <Link
          key={order.ordersId}
          href={`/customer/orders/${order.ordersId}`}
          className="flex justify-between items-center mt-4"
        >
          <div>
            <p className="text-xl font-bold">{order.storeName}</p>
            <p className="text-primary-500 font-semibold">
              {STATUS_MAP[order.status]}
            </p>
          </div>
          <GoChevronRight size={32} />
        </Link>
      ))}
    </div>
  )
}
