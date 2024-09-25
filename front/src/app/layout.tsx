import type { Metadata } from 'next'
import localFont from 'next/font/local'
import './globals.css'

const pretendard = localFont({
  src: '../../public/fonts/woff2/PretendardVariable.woff2',
  weight: '100 900',
  display: 'swap',
})

export const metadata: Metadata = {
  title: 'StrEAT',
  description: '주문결제를 간편하게 StrEAT!',
}

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode
}>) {
  return (
    <html lang="ko" className={pretendard.className}>
      <body className="font-pretendard antialiased">
        <main className="min-h-screen h-full">{children}</main>
      </body>
    </html>
  )
}
