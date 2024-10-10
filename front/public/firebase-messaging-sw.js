importScripts('https://www.gstatic.com/firebasejs/8.10.0/firebase-app.js')
importScripts('https://www.gstatic.com/firebasejs/8.10.0/firebase-messaging.js')

// Firebase 초기화
const firebaseApp = firebase.initializeApp({
  apiKey: 'AIzaSyAyQpFxfnNdtIN8m_awd1cNzi2ZbLScfoI',
  authDomain: 'streat-c2387.firebaseapp.com',
  projectId: 'streat-c2387',
  storageBucket: 'streat-c2387.appspot.com',
  messagingSenderId: '164341366312',
  appId: '1:164341366312:web:a51145fa1024cdf3cbff1d',
  measurementId: 'G-5C2TRT8GEB',
})

const messaging = firebase.messaging()

messaging.setBackgroundMessageHandler(function (payload) {
  console.log(
    '[firebase-messaging-sw.js] Received background message ',
    payload
  )
  const notificationTitle = 'Background Message Title'
  const notificationOptions = {
    body: 'Background Message body.',
    icon: '/firebase-logo.png',
  }

  return self.registration.showNotification(
    notificationTitle,
    notificationOptions
  )
})

// Service Worker 등록
if ('serviceWorker' in navigator) {
  console.log('serviceWorker is valid')
  navigator.serviceWorker
    .register('/firebase-messaging-sw.js')
    .then((registration) => {
      console.log('Service Worker registered with scope:', registration.scope)
      // 메시징 서비스에서 사용할 Service Worker 설정
      messaging.useServiceWorker(registration)
    })
    .catch((err) => {
      console.error('Service Worker registration failed:', err)
    })
} else {
  console.log('Service Workers are not supported in this browser.')
}
