'use client'

import { useEffect, useState } from 'react'
import { NOTIFICATIONS } from '@/containers/customer/notifications'

export default function InAppNotification() {
  const notification = NOTIFICATIONS[2]

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

  // 애니메이션을 제어하는 상태
  const [isVisible, setIsVisible] = useState(false)

  useEffect(() => {
    setIsVisible(true)

    // 3초 후에 애니메이션을 트리거
    const timer = setTimeout(() => {
      setIsVisible(false)
    }, 3000)

    // 타이머 정리
    return () => clearTimeout(timer)
  }, [])

  return (
    <div
      className={`fixed top-0 inset-x-0 m-4 z-50 p-4 rounded-lg shadow transition-all duration-500 ease-in-out ${
        isVisible
          ? 'transform translate-y-0 opacity-100 bg-secondary-medium'
          : 'transform -translate-y-full opacity-0'
      }`}
    >
      <div>
        <p className="font-semibold">{`${notification.icon} ${notification.title}`}</p>
        <p className="text-sm">옐로우 키친 치킨</p>
        {/* 현재 시간 표시 */}
        <p className="mt-2 text-xs text-gray-dark">{getCurrentTime()}</p>
      </div>
    </div>
  )
}
