import { useEffect, useRef, useState } from 'react'

export default function useNaverMap(
  mapElementId: string,
  options: Partial<naver.maps.MapOptions> = {}
) {
  const [currentLocation, setCurrentLocation] = useState<{
    lat: number
    lng: number
  } | null>(null)
  const mapRef = useRef<naver.maps.Map | null>(null)

  // 현재 위치 가져오기
  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const { latitude, longitude } = position.coords
          setCurrentLocation({ lat: latitude, lng: longitude })
        },
        (error) => {
          console.error('Error getting current location', error)
        }
      )
    } else {
      console.error('Geolocation is not supported by this browser.')
    }
  }, [])

  // 지도 초기화 및 마커 생성
  useEffect(() => {
    const initMap = () => {
      if (mapRef.current || !currentLocation) return

      // 지도 생성
      mapRef.current = new naver.maps.Map(mapElementId, {
        center: new naver.maps.LatLng(currentLocation.lat, currentLocation.lng),
        zoom: 16,
        minZoom: 10,
        ...options,
      })

      // 현재 위치에 마커 표시
      const marker = new naver.maps.Marker({
        position: new naver.maps.LatLng(
          currentLocation.lat,
          currentLocation.lng
        ),
        map: mapRef.current,
        icon: {
          content: `
            <div class="w-6 h-6 bg-primary-400 rounded-full opacity-75 animate-ping"></div>
            <div class="w-4 h-4 bg-primary-500 rounded-full border-2 border-white outline outline-primary-500 absolute top-1 left-1"></div>
          `, // HTML 마커 적용
        },
        zIndex: 999,
      })
    }

    // 스크립트 로드 후 지도 초기화
    if (window.naver && window.naver.maps) {
      initMap() // 이미 스크립트가 로드된 경우 바로 지도 초기화
    } else {
      const mapScript = document.createElement('script')
      mapScript.src = `https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${process.env.NEXT_PUBLIC_NAVER_MAP_CLIENT_ID}`
      mapScript.onload = () => initMap()
      document.head.appendChild(mapScript)
    }
  }, [mapElementId, currentLocation, options])
}
