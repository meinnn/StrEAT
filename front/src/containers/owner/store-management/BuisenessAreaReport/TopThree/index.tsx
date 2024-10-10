'use client'

import { useState, useEffect } from 'react'
import Image from 'next/image'
import useNaverMap from '@/hooks/useNaverMap'

interface Store {
  id: number
  src: string
  nickname: string
  address: string
  latitude: number
  longitude: number
  orderCount: number
  rank: number
  imageUrl?: string // 이미지 URL 추가
}

// rank에 따라 리스트 항목 길이 설정 (rank 1: 가장 긴 항목, rank 3: 가장 짧은 항목)
const getWidthByRank = (rank: number) => {
  switch (rank) {
    case 1:
      return '100%' // 가장 긴 항목
    case 2:
      return '90%' // 중간 길이 항목
    case 3:
      return '80%' // 가장 짧은 항목
    default:
      return '70%' // 기본값
  }
}

const getCircleSizeByRank = (rank: number) => {
  switch (rank) {
    case 1:
      return 100 // 가장 큰 원
    case 2:
      return 90 // 중간 크기 원
    case 3:
      return 80 // 가장 작은 원
    default:
      return 60 // 기본값
  }
}

export default function TopThree() {
  const [storeData, setStoreData] = useState<Store[]>([]) // 데이터를 저장할 상태
  const { map } = useNaverMap('store-info', { zoom: 16, minZoom: 6 })

  // 서버에서 데이터를 받아오는 함수
  const fetchData = async () => {
    try {
      const response = await fetch('/services/business-area/topthree') // Next 서버로 요청 보내기
      if (!response.ok) {
        throw new Error('Failed to fetch data')
      }
      const data = await response.json()
      console.log('Fetched data:', data.data.data) // 데이터 확인
      setStoreData(data.data.data) // 받아온 데이터 설정
      console.log(storeData)
    } catch (error) {
      console.error('Error fetching data:', error)
    }
  }

  useEffect(() => {
    fetchData() // 컴포넌트가 마운트될 때 데이터 불러오기
  }, [])

  useEffect(() => {
    if (map && storeData.length > 0) {
      const bounds = new naver.maps.LatLngBounds(
        new naver.maps.LatLng(storeData[0].latitude, storeData[0].longitude), // 남서쪽 좌표 (예시로 첫 번째 매장 좌표)
        new naver.maps.LatLng(storeData[0].latitude, storeData[0].longitude) // 북동쪽 좌표 (초기값)
      )

      // 매장 데이터에 대해 마커와 원을 모두 표시
      storeData.forEach((store) => {
        const { latitude, longitude, nickname, rank } = store
        const position = new naver.maps.LatLng(latitude, longitude)

        // 지도에 마커 추가 (SVG 마커 적용)
        const marker = new naver.maps.Marker({
          position: new naver.maps.LatLng(latitude, longitude),
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

        // 지도에 원 추가 (rank에 따라 원의 반경 크기 설정)
        const circle = new naver.maps.Circle({
          map,
          center: position,
          radius: getCircleSizeByRank(rank), // rank에 따라 반경 설정
          fillColor: 'rgba(255, 99, 132, 0.5)', // 원의 색상
          fillOpacity: 0.5,
          strokeColor: '#FF4365', // 원의 경계 색상
          strokeWeight: 2,
        })

        // 마커에 정보창 추가
        const infoWindow = new naver.maps.InfoWindow({
          content: `<div style="padding:10px;min-width:150px;">${nickname}</div>`,
        })

        // 마커 클릭 시 정보창 열기
        naver.maps.Event.addListener(marker, 'click', () => {
          infoWindow.open(map, marker)
        })

        // bounds에 현재 마커 위치 추가
        bounds.extend(position)
      })

      // 지도 범위를 bounds에 맞게 설정
      map.fitBounds(bounds)
    }
  }, [storeData, map])

  // 매장 클릭 시 해당 매장으로 지도 이동 및 줌
  const handleStoreClick = (store: Store) => {
    if (map) {
      const position = new naver.maps.LatLng(store.latitude, store.longitude)
      map.setCenter(position)
      map.setZoom(16) // 클릭 시 줌 레벨 설정
    }
  }

  return (
    <div className="relative w-full h-full">
      {/* 지도 컨테이너 */}
      <div id="store-info" className="w-full h-screen top-0 left-0 z-0" />

      {/* 리스트 컨테이너 */}
      <div className="absolute bottom-16 w-full p-4 z-10 bg-transparent">
        <div className="rounded-lg p-2">
          {storeData.length > 0 ? (
            storeData
              .sort((a, b) => a.rank - b.rank) // rank 순서대로 정렬
              .map((store) => (
                <div
                  key={store.id}
                  className="mb-2 flex items-center bg-primary-400 text-white p-1 rounded-2xl cursor-pointer"
                  onClick={() => handleStoreClick(store)} // 클릭 이벤트 추가
                  style={{ width: getWidthByRank(store.rank), height: '54px' }} // 높이를 약간 늘림
                >
                  {/* 매장 이미지 */}
                  <div className="w-10 h-10 rounded-full overflow-hidden mr-2">
                    <Image
                      src={store.src || '/images/default_img.jpg'}
                      alt={store.nickname}
                      width={40}
                      height={40}
                      className="object-cover w-full h-full mr-1"
                    />
                  </div>
                  {/* 매장 정보 */}
                  <div className="flex-grow">
                    <div className="font-bold text-sm">{store.nickname}</div>
                    <div className="text-xs">{store.address}</div>
                  </div>
                  {/* 주문 수 */}
                  <div className="flex items-center justify-center bg-white text-[#FF4365] w-10 h-10 rounded-full">
                    <span className="text-sm font-semibold">
                      {store.orderCount}
                    </span>
                  </div>
                  <div className="ml-2 text-xs">orders</div>
                </div>
              ))
          ) : (
            <div className="text-center text-white">Loading...</div>
          )}
        </div>
      </div>
    </div>
  )
}
