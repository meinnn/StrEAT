'use client'

import { useEffect, useState } from 'react'
import { useRouter } from 'next/navigation'

interface NotificationProps {
  notification: {
    title: string
    body: string
    url: string
  }
}

export default function InAppNotification({ notification }: NotificationProps) {
  // 애니메이션을 제어하는 상태
  const [isVisible, setIsVisible] = useState(false)
  const router = useRouter()

  useEffect(() => {
    setIsVisible(true)

    // 3초 후에 애니메이션 종료
    const timer = setTimeout(() => {
      setIsVisible(false)
    }, 3000)

    return () => clearTimeout(timer)
  }, [notification])

  // 현재 시간을 가져오는 함수
  const getCurrentTime = () => {
    const now = new Date()
    return now.toLocaleString('ko-KR', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: 'numeric',
      minute: 'numeric',
    })
  }

  return (
    <div
      role="presentation"
      onClick={() => router.push(notification.url)}
      className={`fixed top-0 inset-x-0 m-4 z-[500] p-4 rounded-lg shadow transition-all duration-500 ease-in-out ${
        isVisible
          ? 'transform translate-y-0 opacity-100 bg-secondary-medium'
          : 'transform -translate-y-full opacity-0'
      }`}
    >
      <div>
        <p className="font-semibold">{notification.title}</p>
        <p className="text-sm">{notification.body}</p>
        <p className="mt-2 text-xs text-gray-dark">{getCurrentTime()}</p>
      </div>
    </div>
  )
}
