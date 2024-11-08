'use client'

import useNaverMap from '@/hooks/useNaverMap'
import { useEffect } from 'react'

export default function StoreMap({ lat, lng }: { lat: number; lng: number }) {
  const { map } = useNaverMap('store-info', { zoom: 16 })

  useEffect(() => {
    if (map) {
      map.setCenter(new naver.maps.LatLng(lat, lng))

      const marker = new naver.maps.Marker({
        position: new naver.maps.LatLng(lat, lng),
        map,
        icon: {
          content: `
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100" width="50" height="50">
              <!-- 외부 도형 -->
              <path fill="#FF4264" stroke="#FF4264" d="M50,10.417c-15.581,0-28.201,12.627-28.201,28.201c0,6.327,2.083,12.168,5.602,16.873L45.49,86.823
              c0.105,0.202,0.21,0.403,0.339,0.588l0.04,0.069l0.011-0.006c0.924,1.278,2.411,2.111,4.135,2.111c1.556,0,2.912-0.708,3.845-1.799
              l0.047,0.027l0.179-0.31c0.264-0.356,0.498-0.736,0.667-1.155L72.475,55.65c3.592-4.733,5.726-10.632,5.726-17.032
              C78.201,23.044,65.581,10.417,50,10.417z"/>
              <!-- 가운데 원 -->
              <circle cx="50" cy="38" r="13.895" fill="white" />
            </svg>`,
          anchor: new naver.maps.Point(25, 50),
        },
      })
    }
  }, [lat, lng, map])

  return (
    <div id="store-info" className="w-full h-48 object-cover bg-gray-medium" />
  )
}
