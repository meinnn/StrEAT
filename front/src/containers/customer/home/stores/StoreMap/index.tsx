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
      })
    }
  }, [lat, lng, map])

  return (
    <div id="store-info" className="w-full h-48 object-cover bg-gray-medium" />
  )
}
