'use client'

import { useRouter } from 'next/navigation'
import { PiWarningCircleBold } from 'react-icons/pi'
import { useCart } from '@/contexts/CartContext'

export default function AddCartAlert() {
  const { reloadCartItems } = useCart()
  const router = useRouter()

  const handleEmptyCart = async () => {
    const response = await fetch('/services/cart/list', {
      method: 'DELETE',
    })

    if (!response.ok) {
      console.error('장바구니 비우기에 실패했습니다')
    }

    reloadCartItems()
    router.back()
  }

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center">
      <div className="bg-white p-6 w-72 rounded-lg shadow-lg text-center flex flex-col gap-y-4">
        <div className="flex justify-center">
          <PiWarningCircleBold className="text-primary-500" size={36} />
        </div>
        <h2 className="text-xl font-semibold">
          같은 가게의 메뉴만 장바구니에 담을 수 있어요
        </h2>
        <p>먼저 장바구니를 비우시겠습니까?</p>
        <div className="flex justify-between gap-2">
          <button
            onClick={() => router.back()}
            className="pt-1 py-2 w-full bg-gray-medium rounded"
          >
            취소
          </button>
          <button
            onClick={handleEmptyCart}
            className="py-2 w-full bg-primary-500 text-white rounded"
          >
            비우기
          </button>
        </div>
      </div>
    </div>
  )
}
