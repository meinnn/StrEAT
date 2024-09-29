import StoreSearchHeader from '@/components/StoreSearchHeader'
import StoreCard from '@/containers/customer/home/StoreCard'
import { FiList } from 'react-icons/fi'
import useNaverMap from '@/hooks/useNaverMap'
import { useCallback, useEffect } from 'react'
import { useMapCenter } from '@/contexts/MapCenterContext'
import { TbCurrentLocation } from 'react-icons/tb'

export default function MapView({
  setView,
  currentAddress,
  setCurrentAddress,
}: {
  setView: (view: 'map' | 'list') => void
  currentAddress: string
  setCurrentAddress: (address: string) => void
}) {
  const { center, setCenter } = useMapCenter()
  // 지도 생성 및 초기화
  const { map, currentLocation } = useNaverMap('map', { zoom: 16 })

  // 좌표로부터 주소를 가져오는 함수
  const fetchAddressFromCoords = useCallback((coords: naver.maps.Coord) => {
    if (window.naver.maps.Service) {
      naver.maps.Service.reverseGeocode({ coords }, (status, response) => {
        if (status === naver.maps.Service.Status.OK) {
          const { jibunAddress, roadAddress } = response.v2.address
          const address = roadAddress || jibunAddress
          setCurrentAddress(address) // 주소 상태 업데이트
        } else {
          console.error('Failed to reverse geocode coordinates')
        }
      })
    } else {
      console.error('Naver maps Service is not available.')
    }
  }, [])

  // 현 위치 버튼 클릭했을 때
  const handleCurrentLocationClick = () => {
    if (currentLocation) {
      const current = new naver.maps.LatLng(
        currentLocation.lat,
        currentLocation.lng
      )
      map?.setCenter(current)
      setCenter(current)
    }
  }

  useEffect(() => {
    if (map) {
      // 처음 로드 시 초기 중심 좌표의 주소 가져오기
      if (center) fetchAddressFromCoords(center)

      // 지도 드래그 이벤트 처리
      const listener = naver.maps.Event.addListener(
        map,
        'center_changed',
        () => {
          const newCenter = map?.getCenter()
          if (newCenter) {
            fetchAddressFromCoords(newCenter) // 중심 좌표를 주소로 변환
            setCenter(newCenter) // 중심 좌표를 Context에 저장
          }
        }
      )

      // Cleanup 함수로 기존 이벤트 리스너 제거
      return () => {
        naver.maps.Event.removeListener(listener)
      }
    }
    return () => {}
  }, [map, fetchAddressFromCoords, setCenter, center])

  return (
    <div className="relative h-screen w-full">
      <div
        id="map"
        className="fixed inset-0 w-full flex items-center justify-center text-center"
        style={{ height: 'calc(100vh - 4rem)' }}
      />

      <div className="fixed top-0 inset-x-0 z-[200]">
        <StoreSearchHeader view="map" currentAddress={currentAddress} />
      </div>

      <div className="absolute bottom-20 w-full">
        <div className="flex items-end m-3">
          {/* 현 위치 버튼 */}
          <button
            type="button"
            className="absolute bg-white rounded-full p-2 border border-gray-medium shadow-lg flex items-center justify-center text-primary-500"
            onClick={handleCurrentLocationClick}
          >
            <TbCurrentLocation size={24} />
          </button>
          {/* 리스트 뷰 버튼 */}
          <button
            type="button"
            onClick={() => setView('list')} // 버튼 클릭 시 'list'로 상태 변경
            className="ml-auto flex items-center bg-[#371B1B] rounded-full px-3 py-2 text-white text-xs"
          >
            <FiList className="mr-1.5" size={14} />
            <span>리스트 뷰</span>
          </button>
        </div>

        <div className="px-4 my-2 flex overflow-x-auto whitespace-nowrap gap-4">
          <StoreCard />
          <StoreCard />
          <StoreCard />
          <StoreCard />
        </div>
      </div>
    </div>
  )
}
