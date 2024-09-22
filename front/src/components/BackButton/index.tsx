'use client'

import { useRouter } from 'next/navigation'
import { FaAngleLeft } from 'react-icons/fa6'

export default function BackButton() {
  const router = useRouter()

  const handleBack = () => {
    router.back() // 이전 페이지로 이동
  }

  return (
    <button
      type="button"
      onClick={handleBack}
      className="absolute top-4 left-4"
    >
      <FaAngleLeft size={24} className="text-white" />
    </button>
  )
}
