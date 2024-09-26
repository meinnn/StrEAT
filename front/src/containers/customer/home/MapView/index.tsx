import StoreSearchHeader from '@/components/StoreSearchHeader'
import StoreCard from '@/containers/customer/home/StoreCard'
import { FiList } from 'react-icons/fi'
import useNaverMap from '@/hooks/useNaverMap'
import { useCallback, useEffect, useState } from 'react'

export default function MapView({
  setView,
  currentAddress,
  setCurrentAddress,
}: {
  setView: (view: 'map' | 'list') => void
  currentAddress: string
  setCurrentAddress: (address: string) => void
}) {
  // 지도 생성 및 초기화
  const map = useNaverMap('map', { zoom: 16 })

  // 좌표로부터 주소를 가져오는 함수
  const fetchAddressFromCoords = useCallback(
    (coords: naver.maps.Coord) => {
      naver.maps.Service.reverseGeocode({ coords }, (status, response) => {
        if (status === naver.maps.Service.Status.OK) {
          const { jibunAddress } = response.v2.address
          const { roadAddress } = response.v2.address
          setCurrentAddress(roadAddress || jibunAddress)
        } else {
          console.error('Failed to reverse geocode center coordinates')
        }
      })
    },
    [setCurrentAddress]
  )

  useEffect(() => {
    if (map) {
      // 처음 로드 시 초기 중심 좌표의 주소 가져오기
      const initialCenter = map.getCenter()
      if (initialCenter) {
        fetchAddressFromCoords(initialCenter) // 주소 가져오기 함수 호출
      }

      // 지도 드래그 이벤트 처리
      naver.maps.Event.addListener(map, 'center_changed', () => {
        const newCenter = map?.getCenter()
        if (newCenter) {
          fetchAddressFromCoords(newCenter) // 중심 좌표를 주소로 변환
        }
      })
    }
  }, [fetchAddressFromCoords, map])

  return (
    <div className="relative h-screen w-full">
      <div
        id="map"
        className="fixed inset-0 w-full flex items-center justify-center text-center"
        style={{ height: 'calc(100vh - 4rem)' }}
      />

      <div className="fixed top-0 inset-x-0">
        <StoreSearchHeader view="map" currentAddress={currentAddress} />
      </div>

      <div className="absolute bottom-20 w-full">
        <button
          type="button"
          onClick={() => setView('list')} // 버튼 클릭 시 'list'로 상태 변경
          className="ml-auto flex items-center bg-[#371B1B] rounded-full px-3 py-2 text-white text-xs"
        >
          <FiList className="mr-1.5" size={14} />
          <span>리스트 뷰</span>
        </button>

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
