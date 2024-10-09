import type { MetadataRoute } from 'next'

export default function manifest(): MetadataRoute.Manifest {
  return {
    name: 'StrEAT',
    short_name: 'StrEAT',
    description: '스마트 푸드트럭 주문 예약 서비스',
    start_url: '/',
    display: 'standalone',
    background_color: '#ff4262',
    theme_color: '#ffffff',
    icons: [
      {
        src: '/web-app-manifest-192x192.png',
        sizes: '192x192',
        type: 'image/png',
      },
      {
        src: '/web-app-manifest-512x512.png',
        sizes: '512x512',
        type: 'image/png',
      },
    ],
  }
}
