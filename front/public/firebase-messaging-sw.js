importScripts('https://www.gstatic.com/firebasejs/8.10.0/firebase-app.js')
importScripts('https://www.gstatic.com/firebasejs/8.10.0/firebase-messaging.js')

const firebaseConfig = {
  apiKey: 'AIzaSyAyQpFxfnNdtIN8m_awd1cNzi2ZbLScfoI',
  authDomain: 'streat-c2387.firebaseapp.com',
  projectId: 'streat-c2387',
  storageBucket: 'streat-c2387.appspot.com',
  messagingSenderId: '164341366312',
  appId: '1:164341366312:web:a51145fa1024cdf3cbff1d',
  measurementId: 'G-5C2TRT8GEB',
}

firebase.initializeApp(firebaseConfig)
const messaging = firebase.messaging()

messaging.onBackgroundMessage((payload) => {
  const title = payload.notification?.title || 'Default Title'
  const notificationOptions = {
    body: payload.notification.body,
    icon: '/web-app-manifest-192x192.png',
    data: {
      url: payload.notification?.click_action || '/', // 알림 클릭 시 이동할 URL 설정
    },
  }

  self.registration.showNotification(title, notificationOptions)
})

self.addEventListener('notificationclick', function (event) {
  const url = event.notification.data.url || '/' // 알림에 설정된 URL이 없다면 기본 루트로 이동
  event.notification.close() // 알림 닫기

  event.waitUntil(
    clients.openWindow(url) // 새로운 창이나 탭으로 이동
  )
})
