'use client'

import { HiOutlineBellAlert } from 'react-icons/hi2'
import { getMessaging, getToken, isSupported } from 'firebase/messaging'
import { firebaseApp } from '@/firebase'
import { useRouter } from 'next/navigation'

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

const sendFCMToken = async (fcmToken: string) => {
  const response = await fetch('/services/users/fcm-token', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ fcmToken }),
  })

  const data = await response.json()
  if (!response.ok) {
    console.error(data.message)
  }
}

export default function Permission({ userType }: { userType: string }) {
  const router = useRouter()

  const requestPermission = async () => {
    const messagingResolve = await messaging()
    if (!('Notification' in window)) {
      console.warn('This browser does not support notifications.')
      return
    }
    if (messagingResolve) {
      const fcmToken = await getToken(messagingResolve, {
        vapidKey: process.env.NEXT_PUBLIC_VAPID_KEY,
      })
      if (fcmToken) {
        sendFCMToken(fcmToken).then()
      }
    }

    if (userType === 'NOT_SELECTED') {
      router.push('/sign-up')
    } else if (userType === 'CUSTOMER') {
      router.push('/customer')
    } else if (userType === 'OWNER') {
      router.push('/owner')
    }
  }

  return (
    <div className="h-screen flex flex-col items-center justify-center">
      <h1 className="text-5xl font-bold">StrEAT</h1>
      <HiOutlineBellAlert size={48} className="text-primary-500 my-6" />
      <div className="font-semibold text-xl text-center my-2">
        <p>주문 현황, 단골 가게 알림 등을 위해서는</p>
        <p>푸시 알림 동의가 필요해요</p>
      </div>
      <p className="text-gray-dark">설정은 마이페이지에서 변경할 수 있어요</p>
      <button
        type="button"
        className="bg-primary-500 text-white px-4 py-2 rounded-xl text-lg font-medium my-8"
        onClick={requestPermission}
      >
        푸시 알림 켜기
      </button>
    </div>
  )
}
