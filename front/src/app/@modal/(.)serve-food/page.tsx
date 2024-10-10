'use client'

import { useRouter } from 'next/navigation'
import ModalLayout from '@/components/ModalLayout'

export default function ServeFoodModal() {
  const router = useRouter()
  return (
    <ModalLayout>
      <div className="bg-white px-3 pt-5 pb-2 flex flex-col items-center w-full max-w-80 rounded-xl overflow-hidden">
        <span className="text-text font-semibold text-xl">손님</span>
        <p className="text-primary-500 text-9xl my-7 font-bold">25</p>
        <p className="text-text mb-7">음식을 제공하세요</p>
        <button
          onClick={() => router.back()}
          type="button"
          className="py-3 w-full bg-primary-500 text-secondary-light rounded-md"
        >
          확인
        </button>
      </div>
    </ModalLayout>
  )
}
