'use client'

import { useEffect, useState } from 'react'
import { getMessaging, isSupported, onMessage } from 'firebase/messaging'
import { firebaseApp } from '@/firebase'
import InAppNotification from '@/containers/customer/notifications/InAppNotification'

interface NotificationData {
  title: string
  body: string
  url: string
}

const messaging = async () => {
  try {
    const isSupportedBrowser = await isSupported()
    if (isSupportedBrowser) {
      return getMessaging(firebaseApp)
    }
    return null
  } catch (err) {
    console.error(err)
    return null
  }
}

export default function FCMHandler() {
  const [notifications, setNotifications] = useState<NotificationData[]>([])

  useEffect(() => {
    const onMessageListener = async () => {
      const messagingResolve = await messaging()
      if (messagingResolve) {
        onMessage(messagingResolve, (payload) => {
          if (!('Notification' in window)) {
            return
          }
          console.log(payload)

          const title = payload.notification?.title || '알림'
          const body = payload.notification?.body || '메시지 내용'
          const redirectUrl = payload.data?.url || '/'

          // 브라우저 알림 표시
          // const { permission } = Notification
          // if (permission === 'granted') {
          //   new Notification(title, {
          //     body,
          //     icon: '/web-app-manifest-192x192.png',
          //   }).onclick = () => {
          //     window.open(redirectUrl, '_blank')?.focus()
          //   }
          // }

          // 알림 리스트에 새 알림 추가
          setNotifications((prev) => [
            ...prev,
            { title, body, url: redirectUrl },
          ])

          // 3.5초 후 알림 제거
          setTimeout(() => {
            setNotifications((prev) => prev.slice(1))
          }, 3500)
        })
      }
    }
    onMessageListener().then()
  }, [])

  return (
    <>
      {notifications.map((notification) => (
        <InAppNotification key={notification.url} notification={notification} />
      ))}
    </>
  )
}
