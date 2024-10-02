'use client'

import {
  createContext,
  useContext,
  useState,
  ReactNode,
  useEffect,
  useMemo,
} from 'react'

// Context 생성
const MapCenterContext = createContext<
  | {
      center: naver.maps.Coord | null // 초기값을 null로 설정
      setCenter: (center: naver.maps.Coord) => void
    }
  | undefined
>(undefined)

// Context Provider 생성
export function MapCenterProvider({ children }: { children: ReactNode }) {
  const [center, setCenter] = useState<naver.maps.Coord | null>(null) // 초기에는 null로 설정

  // 클라이언트 사이드에서만 naver 객체 접근
  useEffect(() => {
    if (typeof window !== 'undefined' && window.naver) {
      setCenter(new naver.maps.LatLng(37.5665, 126.978)) // 기본 좌표 설정 (서울 시청)
    }
  }, [])

  // useMemo로 value 객체를 캐싱
  const value = useMemo(() => ({ center, setCenter }), [center, setCenter])

  return (
    <MapCenterContext.Provider value={value}>
      {children}
    </MapCenterContext.Provider>
  )
}

// Context 사용을 위한 커스텀 Hook
export function useMapCenter() {
  const context = useContext(MapCenterContext)
  if (!context) {
    throw new Error('useMap must be used within a MapProvider')
  }
  return context
}
