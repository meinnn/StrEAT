import type { Metadata } from 'next'
import localFont from 'next/font/local'
import './globals.css'
import Script from 'next/script'
import { MapProvider } from '@/contexts/MapContext'

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
        <MapProvider>
          <main className="min-h-screen h-full">{children}</main>
        </MapProvider>
        <Script
          type="text/javascript"
          src={`https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${process.env.NEXT_PUBLIC_NAVER_MAP_CLIENT_ID}&submodules=geocoder`}
        />
      </body>
    </html>
  )
}
