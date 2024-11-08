import StoreSearchHeader from '@/components/StoreSearchHeader'
import { FiList } from 'react-icons/fi'
import useNaverMap from '@/hooks/useNaverMap'
import { useCallback, useEffect, useRef, useState } from 'react'
import { useMapCenter } from '@/contexts/MapCenterContext'
import { TbCurrentLocation } from 'react-icons/tb'
import { Store } from '@/types/store'
import StoreListItem from '@/containers/customer/home/StoreListItem'

const fetchStores = async (lat: number, lng: number): Promise<Store[]> => {
  const response = await fetch(`/services/stores?lat=${lat}&lng=${lng}`)
  if (!response.ok) {
    throw new Error('Failed to fetch store list')
  }
  return response.json()
}

export default function MapView({
  setView,
  currentAddress,
  setCurrentAddress,
  storeList,
  setStoreList,
}: {
  setView: (view: 'map' | 'list') => void
  currentAddress: string
  setCurrentAddress: (address: string) => void
  storeList: Store[]
  setStoreList: (stores: Store[]) => void
}) {
  const { center, setCenter } = useMapCenter()
  // 지도 생성 및 초기화
  const { map, currentLocation } = useNaverMap('map', { zoom: 16 })
  const markersRef = useRef<naver.maps.Marker[]>([]) // 마커 배열
  const itemRefs = useRef<(HTMLDivElement | null)[]>([]) // StoreListItem의 각 요소 참조
  const isInitialRender = useRef(true) // 처음 렌더링 여부를 추적하는 useRef
  const [showAlert, setShowAlert] = useState(false) // 알림 메시지 상태
  const [isLocationAvailable, setIsLocationAvailable] = useState(true) // 위치 정보 사용 가능 여부

  // 마커 클릭 시 해당 가게로 스크롤 이동 함수
  const scrollToStore = useCallback(
    (storeId: number) => {
      const storeElement =
        itemRefs.current[storeList.findIndex((store) => store.id === storeId)]
      if (storeElement) {
        // Tailwind로 색상 강조 효과를 추가
        storeElement.classList.add('scale-105')

        // 일정 시간이 지난 후 색상 클래스를 제거
        setTimeout(() => {
          storeElement.classList.remove('scale-105')
        }, 700) // 0.7초 동안 색상 변경

        // 스크롤 이동
        storeElement.scrollIntoView({
          behavior: 'smooth',
          block: 'nearest',
          inline: 'start',
        })
      }
    },
    [storeList]
  )

  // 위치 권한 확인 및 버튼 표시 여부 결정
  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        () => setIsLocationAvailable(true),
        () => setIsLocationAvailable(false)
      )
    } else {
      setIsLocationAvailable(false)
    }
  }, [])

  // 위치 권한 요청 함수
  const requestLocationPermission = () => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          console.log('위치 권한 허용됨', position)
          setIsLocationAvailable(true) // 위치 정보 사용 가능 상태로 변경
        },
        (error) => {
          console.error('위치 권한 거부됨', error)
          setIsLocationAvailable(false) // 위치 정보 사용 불가 상태로 유지
        }
      )
    } else {
      console.warn('이 브라우저에서는 위치 서비스를 지원하지 않습니다.')
    }
  }

  // 좌표로부터 주소를 가져오는 함수
  const fetchAddressFromCoords = useCallback(
    (coords: naver.maps.Coord) => {
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
    },
    [setCurrentAddress]
  )

  // 가게 목록을 재검색하는 함수
  const onReSearch = useCallback(async () => {
    if (center) {
      const latitude = center.y
      const longitude = center.x
      try {
        const stores = await fetchStores(latitude, longitude)
        setStoreList(stores)

        if (stores.length === 0) {
          // storeList가 빈 배열일 경우, 알림 메시지를 3초 동안 표시
          setShowAlert(true)
          setTimeout(() => setShowAlert(false), 3000)
        }
      } catch (error) {
        console.error('Error fetching store list:', error)
      }
    }
  }, [center, setStoreList])

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

  // storeList를 기반으로 마커 추가
  useEffect(() => {
    if (map && storeList.length > 0) {
      // 이전 마커 제거
      markersRef.current.forEach((marker) => marker.setMap(null))
      markersRef.current = []

      // 새로운 마커 추가
      storeList.forEach((store) => {
        const markerColor = store.status === '영업중' ? '#FF4264' : '#371B1B' // 상태에 따른 색상 설정

        const marker = new naver.maps.Marker({
          position: new naver.maps.LatLng(store.latitude, store.longitude),
          map,
          title: store.storeName,
          icon: {
            content: `
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100" width="50" height="50">
                <!-- 외부 도형 -->
                <path fill="${markerColor}" stroke="${markerColor}" d="M50,10.417c-15.581,0-28.201,12.627-28.201,28.201c0,6.327,2.083,12.168,5.602,16.873L45.49,86.823
                c0.105,0.202,0.21,0.403,0.339,0.588l0.04,0.069l0.011-0.006c0.924,1.278,2.411,2.111,4.135,2.111c1.556,0,2.912-0.708,3.845-1.799
                l0.047,0.027l0.179-0.31c0.264-0.356,0.498-0.736,0.667-1.155L72.475,55.65c3.592-4.733,5.726-10.632,5.726-17.032
                C78.201,23.044,65.581,10.417,50,10.417z"/>
                <!-- 가운데 원 -->
                <circle cx="50" cy="38" r="13.895" fill="white" />
              </svg>`,
            anchor: new naver.maps.Point(25, 50), // 마커의 기준점 설정
          },
        })

        // 마커 클릭 시 해당 마커 위치로 지도 중심 이동
        marker.addListener('click', () => {
          const markerPosition = marker.getPosition()
          if (markerPosition) {
            map.setCenter(markerPosition) // 지도 중심을 마커 위치로 설정
            setCenter(markerPosition) // Context의 center도 업데이트
            scrollToStore(store.id) // 클릭된 마커에 해당하는 가게로 스크롤 이동
          }
        })

        markersRef.current.push(marker)
      })
    }
  }, [map, storeList, setCenter, scrollToStore])

  // 처음 렌더링 시만 onReSearch 실행
  useEffect(() => {
    if (center && isInitialRender.current) {
      onReSearch().then() // 처음 렌더링 시 실행
      isInitialRender.current = false // 첫 렌더링 이후로는 false로 변경
    }
  }, [center, onReSearch])

  return (
    <div className="relative h-screen w-full">
      {showAlert && (
        <div
          className={`z-[300] fixed top-1/2 left-1/2 transform -translate-x-1/2 bg-white text-primary-500 font-semibold border-2 border-primary-500 py-2 px-4 rounded-xl shadow-lg transition-all duration-300 ease-in-out ${
            showAlert ? 'opacity-100' : 'opacity-0'
          }`}
        >
          검색 결과가 없습니다
        </div>
      )}

      <div
        id="map"
        className="fixed inset-0 w-full flex items-center justify-center text-center"
        style={{ height: 'calc(100vh - 4rem)' }}
      >
        {!isLocationAvailable && (
          <button
            type="button"
            className="border-2 border-primary-500 text-primary-500 rounded-xl px-3 py-2"
            onClick={requestLocationPermission} // 위치 권한 요청
          >
            위치 정보 켜기
          </button>
        )}
      </div>

      <div className="fixed top-0 inset-x-0 z-[200]">
        <StoreSearchHeader
          view="map"
          currentAddress={currentAddress}
          onReSearch={onReSearch}
        />
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

        <div className="px-4 relative w-full snap-x snap-mandatory my-2 flex overflow-x-auto whitespace-nowrap gap-4">
          {storeList.map((store, index) => (
            <div
              key={store.id}
              ref={(el) => {
                itemRefs.current[index] = el
              }}
              className="snap-start scroll-mx-4 my-2 shrink-0 relative bg-[#371B1B] text-white rounded-xl min-w-72 h-28 px-1 transition-transform duration-500 ease-in-out"
            >
              <StoreListItem store={store} />
            </div>
          ))}
        </div>
      </div>
    </div>
  )
}
