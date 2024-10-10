// Import the functions you need from the SDKs you need
import { initializeApp } from 'firebase/app'

// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: 'AIzaSyAyQpFxfnNdtIN8m_awd1cNzi2ZbLScfoI',
  authDomain: 'streat-c2387.firebaseapp.com',
  projectId: 'streat-c2387',
  storageBucket: 'streat-c2387.appspot.com',
  messagingSenderId: '164341366312',
  appId: '1:164341366312:web:a51145fa1024cdf3cbff1d',
  measurementId: 'G-5C2TRT8GEB',
}

// Initialize Firebase
// eslint-disable-next-line import/prefer-default-export
export const firebaseApp = initializeApp(firebaseConfig)
