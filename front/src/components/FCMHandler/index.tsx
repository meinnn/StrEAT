'use client'

import { useEffect } from 'react'
import { onMessage, getMessaging } from 'firebase/messaging'
import { getApps, initializeApp } from 'firebase/app'

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

export default function FCMHandler() {
  useEffect(() => {
    if (typeof window !== 'undefined' && 'serviceWorker' in navigator) {
      // Firebase 앱이 이미 초기화되어 있는지 확인 후 초기화
      const firebaseApp = !getApps().length
        ? initializeApp(firebaseConfig)
        : getApps()[0]
      const messagingInstance = getMessaging(firebaseApp)
      console.log(messagingInstance)

      // 포그라운드 메시지 수신 처리
      onMessage(messagingInstance, (payload) => {
        console.log('Foreground message received: ', payload)
        // 포그라운드 메시지 처리 로직 추가
      })
    }
  }, [])

  return null // UI 요소가 필요 없으므로 null 반환
}
