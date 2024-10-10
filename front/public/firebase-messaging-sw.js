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

// messaging.onBackgroundMessage((payload) => {
//   const title = `${payload.notification?.title} background`
//   const notificationOptions = {
//     body: payload.notification.body,
//     icon: '/web-app-manifest-192x192.png',
//   }
//
//   self.registration.showNotification(title, notificationOptions)
// })
