import type { Metadata } from 'next'
import localFont from 'next/font/local'
import './globals.css'
import Script from 'next/script'
import { MapCenterProvider } from '@/contexts/MapCenterContext'
import FCMHandler from '@/components/FCMHandler'
import { StoreRegistProvider } from '@/contexts/storeRegistContext'
import { StoreLocationProvider } from '@/contexts/StoreLocationContext'

const pretendard = localFont({
  src: '../../public/fonts/woff2/PretendardVariable.woff2',
  weight: '100 900',
  display: 'swap',
})

export const metadata: Metadata = {
  title: 'StrEAT',
  description: '주문결제를 간편하게 StrEAT!',
  icons: {
    icon: [
      {
        rel: 'icon',
        type: 'image/png',
        sizes: '48x48',
        url: '/favicon-48x48.png',
      },
      { rel: 'icon', type: 'image/svg+xml', url: '/favicon.svg' },
    ],
    apple: '/apple-touch-icon.png', // Apple Touch Icon 설정
    shortcut: '/favicon.ico', // Shortcut Icon 설정
  },
}

export default function RootLayout({
  children,
  modal,
}: Readonly<{
  children: React.ReactNode
  modal: React.ReactNode
}>) {
  return (
    <html lang="ko" className={pretendard.className}>
      <body className="font-pretendard antialiased">
        <StoreLocationProvider>
          <StoreRegistProvider>
            <MapCenterProvider>
              {modal}
              <main className="min-h-screen h-full">{children}</main>
              <FCMHandler />
            </MapCenterProvider>
          </StoreRegistProvider>
        </StoreLocationProvider>
        <Script
          type="text/javascript"
          src={`https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${process.env.NEXT_PUBLIC_NAVER_MAP_CLIENT_ID}&submodules=geocoder`}
        />
        <Script src="/service-worker.js" />
      </body>
    </html>
  )
}
