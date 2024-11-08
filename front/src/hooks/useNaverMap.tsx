import { useEffect, useRef, useState } from 'react'
import { useMapCenter } from '@/contexts/MapCenterContext'

export default function useNaverMap(
  mapElementId: string,
  options: Partial<naver.maps.MapOptions> = {}
) {
  const [currentLocation, setCurrentLocation] = useState<{
    lat: number
    lng: number
  } | null>(null)

  const mapRef = useRef<naver.maps.Map | null>(null)
  const markerRef = useRef<naver.maps.Marker | null>(null)

  const { center, setCenter } = useMapCenter()

  // 현재 위치 추적 및 실시간 업데이트
  useEffect(() => {
    if (navigator.geolocation) {
      const watchId = navigator.geolocation.watchPosition(
        (position) => {
          const { latitude, longitude } = position.coords
          setCurrentLocation({ lat: latitude, lng: longitude })
        },
        (error) => {
          console.error('Error getting current location', error)
        },
        {
          enableHighAccuracy: true,
          maximumAge: 0,
          timeout: 5000,
        }
      )

      // 컴포넌트 언마운트 시 위치 추적 중지
      return () => navigator.geolocation.clearWatch(watchId)
    }
    console.error('Geolocation is not supported by this browser.')
    return () => {}
  }, [])

  // 지도 초기화 및 마커 생성
  useEffect(() => {
    const initMap = () => {
      if (mapRef.current || !currentLocation) return

      // 사용자가 전달한 center 값을 사용하거나 기본 값 사용
      let initialCenter
      if (options.center) initialCenter = options.center
      else
        initialCenter =
          center ||
          new naver.maps.LatLng(currentLocation.lat, currentLocation.lng)

      // 지도 생성
      mapRef.current = new naver.maps.Map(mapElementId, {
        center: initialCenter,
        zoom: 16,
        minZoom: 10,
        scaleControl: false,
        mapDataControl: false,
        logoControlOptions: {
          position: naver.maps.Position.BOTTOM_LEFT,
        },
        ...options,
      })

      // 현재 위치에 마커 표시
      markerRef.current = new naver.maps.Marker({
        position: new naver.maps.LatLng(
          currentLocation.lat,
          currentLocation.lng
        ),
        map: mapRef.current,
        icon: {
          content: `
          <span class="relative flex h-5 w-5">
            <span class="animate-ping absolute inline-flex h-full w-full rounded-full bg-primary-400 opacity-75"></span>
            <span class="relative inline-flex rounded-full h-4 w-4 bg-primary-500 border-2 border-white outline outline-2 outline-primary-500 top-0.5 left-0.5"></span>
          </span>
        `,
        },
      })

      // Context에 초기 지도 center 저장
      const mapCenter = mapRef.current.getCenter()
      if (mapCenter) {
        setCenter(mapCenter)
      }
    }

    // 스크립트 로드 후 지도 초기화
    if (window.naver && window.naver.maps && window.naver.maps.Service) {
      initMap() // 이미 스크립트가 로드된 경우 바로 지도 초기화
    } else {
      const mapScript = document.createElement('script')
      mapScript.src = `https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${process.env.NEXT_PUBLIC_NAVER_MAP_CLIENT_ID}&submodules=geocoder`
      mapScript.onload = () => {
        naver.maps.onJSContentLoaded = () => initMap()
      }
      document.head.appendChild(mapScript)
    }
  }, [mapElementId, currentLocation, options, center, setCenter])

  // 사용자의 위치가 업데이트될 때 마커만 이동
  useEffect(() => {
    if (currentLocation && markerRef.current) {
      const newPosition = new naver.maps.LatLng(
        currentLocation.lat,
        currentLocation.lng
      )
      markerRef.current.setPosition(newPosition) // 마커 위치만 업데이트
      // 지도 중심은 고정되어 있음
    }
  }, [currentLocation])

  return { map: mapRef.current, currentLocation }
}
