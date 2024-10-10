'use client'

import { useEffect } from 'react'
import { getMessaging, isSupported, onMessage } from 'firebase/messaging'
import { firebaseApp } from '@/firebase'

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
  useEffect(() => {
    const onMessageListener = async () => {
      const messagingResolve = await messaging()
      if (messagingResolve) {
        onMessage(messagingResolve, (payload) => {
          if (!('Notification' in window)) {
            return
          }
          const { permission } = Notification
          const title = `${payload.notification?.title} foreground`
          const redirectUrl = payload.data?.url
          const body = payload.notification?.body
          if (permission === 'granted') {
            if (payload.notification) {
              console.log(payload)
              const notification = new Notification(title, {
                body,
                icon: '/web-app-manifest-192x192.png',
              })
              notification.onclick = () => {
                window.open(redirectUrl, '_blank')?.focus()
              }
            }
          }
        })
      }
    }
    onMessageListener().then()
  }, [])
  return null
}
