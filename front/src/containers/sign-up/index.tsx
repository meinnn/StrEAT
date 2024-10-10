'use client'

import { useEffect, useState } from 'react'
import { PiChefHat, PiForkKnife } from 'react-icons/pi'
import { LuMoveRight } from 'react-icons/lu'
import { useRouter } from 'next/navigation'
import { initializeApp } from 'firebase/app'
import { getMessaging, getToken } from 'firebase/messaging'

// Firebase 설정
const firebaseConfig = {
  apiKey: 'AIzaSyAyQpFxfnNdtIN8m_awd1cNzi2ZbLScfoI',
  authDomain: 'streat-c2387.firebaseapp.com',
  projectId: 'streat-c2387',
  storageBucket: 'streat-c2387.appspot.com',
  messagingSenderId: '164341366312',
  appId: '1:164341366312:web:a51145fa1024cdf3cbff1d',
  measurementId: 'G-5C2TRT8GEB',
}

export default function SignUp() {
  const [selectedRole, setSelectedRole] = useState<
    'owners' | 'customers' | null
  >(null)
  const router = useRouter()

  // FCM 토큰 요청
  useEffect(() => {
    const firebaseApp = initializeApp(firebaseConfig)
    const messaging = getMessaging(firebaseApp)

    // VAPID Key 설정: Firebase Console에서 제공하는 VAPID 공개 키 사용
    const vapidKey =
      'BGB9kZH-Dwk7iDUgpUCBbItWYHGQVNeHDy5Na7uRazXfYIRo86CHfZmaTnNFYJE1q7ewN0z6On1TVor8cYgpJXA' // 이 키는 Firebase 콘솔에서 찾을 수 있습니다.

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

    // 브라우저 알림 권한 요청 및 FCM 토큰 얻기
    async function requestPermissionAndGetToken() {
      try {
        const permission = await Notification.requestPermission()
        if (permission === 'granted') {
          console.log('Notification permission granted.')

          const currentToken = await getToken(messaging, { vapidKey })
          if (currentToken) {
            console.log('FCM Token:', currentToken)
            sendFCMToken(currentToken).then()
          } else {
            console.log('No registration token available.')
          }
        } else {
          console.log('Notification permission denied.')
        }
      } catch (error) {
        console.error('An error occurred while retrieving token.', error)
      }
    }

    requestPermissionAndGetToken().then()
  }, [])

  const handleSignUp = async () => {
    const response = await fetch('/services/users/sign-up', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ userType: selectedRole }), // userType을 요청 본문에 포함
    })

    const data = await response.json()
    if (response.ok) {
      if (selectedRole === 'customers') router.push('/customers')
      else router.push('/owner')
    }

    console.error(data.message)
  }

  return (
    <div className="h-screen flex flex-col items-center justify-center space-y-10">
      <h1 className="text-5xl font-bold">StrEAT</h1>
      <p className="font-xl font-medium">어떤 회원으로 가입할까요?</p>
      <div className="flex space-x-4">
        {/* 사장님 버튼 */}
        <button
          type="button"
          className={`border size-40 rounded-lg flex flex-col space-y-2 items-center justify-center transition-colors ${
            selectedRole === 'owners'
              ? 'bg-primary-500 text-white'
              : 'border-primary-500 text-primary-500'
          }`}
          onClick={() => setSelectedRole('owners')}
        >
          <PiChefHat size={48} />
          <p>사장님</p>
        </button>

        {/* 손님 버튼 */}
        <button
          type="button"
          className={`border size-40 rounded-lg flex flex-col space-y-2 items-center justify-center transition-colors ${
            selectedRole === 'customers'
              ? 'bg-primary-500 text-white'
              : 'border-primary-500 text-primary-500'
          }`}
          onClick={() => setSelectedRole('customers')}
        >
          <PiForkKnife size={48} />
          <p>손님</p>
        </button>
      </div>
      <button type="button" onClick={handleSignUp}>
        <LuMoveRight
          size={48}
          className={`fixed bottom-6 right-6 transition-colors ${
            selectedRole ? 'text-primary-950' : 'text-gray-medium'
          }`}
        />
      </button>
    </div>
  )
}
