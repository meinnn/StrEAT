'use client'

import { useRouter } from 'next/navigation'
import { FaAngleLeft } from 'react-icons/fa6'
import Image from 'next/image'
import { useEffect, useState } from 'react'

export default function BackButtonWithImage({
  src,
  alt,
  title,
}: {
  src: string
  alt: string
  title: string
}) {
  const router = useRouter()
  const [showTopBar, setShowTopBar] = useState(false)

  // 스크롤 이벤트를 감지해서 상태 업데이트
  useEffect(() => {
    const handleScroll = () => {
      // 스크롤이 일정 위치 이상 내려갔을 때 showTopBar를 true로 설정
      if (window.scrollY > 176) {
        setShowTopBar(true)
      } else {
        setShowTopBar(false)
      }
    }

    window.addEventListener('scroll', handleScroll)

    return () => {
      window.removeEventListener('scroll', handleScroll)
    }
  }, [])

  const handleBack = () => {
    router.back() // 이전 페이지로 이동
  }

  return (
    <div>
      <button
        type="button"
        onClick={() => router.back()}
        className="fixed top-4 left-4"
      >
        <FaAngleLeft size={24} className="text-white" />
      </button>

      <div className="w-full h-48 relative -z-10">
        <Image src={src} alt={alt} fill className="object-cover" priority />
      </div>

      {/* 스크롤에 따라 상단 바와 뒤로가기 버튼을 표시 */}
      {showTopBar && (
        <div className="fixed top-0 inset-x-0 h-12 bg-white shadow-md flex items-center px-4 z-20 text-primary-950">
          <button type="button" onClick={handleBack}>
            <FaAngleLeft size={24} />
          </button>
          <h2 className="ms-2 font-bold text-lg">{title}</h2>
        </div>
      )}
    </div>
  )
}
