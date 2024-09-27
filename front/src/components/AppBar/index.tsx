'use client'

import { FaAngleLeft } from 'react-icons/fa6'
import { useRouter } from 'next/navigation'

export default function AppBar({
  title,
  option,
}: {
  title: string
  // eslint-disable-next-line react/require-default-props
  option?: string
}) {
  const router = useRouter()

  const handleBack = () => {
    router.back() // 뒤로 가기
  }

  return (
    <div className="sticky top-0 w-full h-tabbar bg-white flex items-center justify-between px-4 z-50">
      <button
        type="button"
        onClick={handleBack}
        className="text-primary-950 flex items-center"
      >
        <FaAngleLeft size={24} className="text-primary-950" />
      </button>
      <h1 className="text-2xl font-bold text-primary-950">
        {title}
        {option ? <span className="text-xl font-medium">{option}</span> : null}
      </h1>
      {/* 오른쪽에 비어있는 공간 */}
      <div className="w-6" />
    </div>
  )
}
