'use client'

import { useState, useEffect } from 'react'
import useNaverMap from '@/hooks/useNaverMap'

interface Store {
  id: number
  nickname: string
  address: string
  latitude: number
  longitude: number
  orderCount: number
  rank: number
}

// 더미 데이터를 설정
const dummyData: Store[] = [
  {
    id: 1,
    nickname: '역삼역 앞',
    address: '경기도',
    latitude: 37.2938,
    longitude: 127.97389,
    orderCount: 127,
    rank: 3,
  },
  {
    id: 2,
    nickname: '서울역 근처',
    address: '서울특별시',
    latitude: 37.5563,
    longitude: 126.9723,
    orderCount: 200,
    rank: 2,
  },
  {
    id: 3,
    nickname: '남산 타워',
    address: '서울특별시',
    latitude: 37.5511694,
    longitude: 126.9882266,
    orderCount: 300,
    rank: 1,
  },
]

// rank에 따라 원 크기 설정 (rank 1: 가장 큰 원, rank 3: 가장 작은 원)
const getCircleSizeByRank = (rank: number) => {
  switch (rank) {
    case 1:
      return 300 // 가장 큰 원
    case 2:
      return 150 // 중간 크기 원
    case 3:
      return 75 // 가장 작은 원
    default:
      return 50 // 기본값
  }
}

export default function TopThree() {
  const [storeData, setStoreData] = useState<Store[]>(dummyData) // 더미 데이터를 상태로 설정
  const { map } = useNaverMap('store-info', { zoom: 16 })

  useEffect(() => {
    if (map && storeData.length > 0) {
      const bounds = new naver.maps.LatLngBounds()

      // 매장 데이터에 대해 마커와 원을 모두 표시
      storeData.forEach((store) => {
        const { latitude, longitude, nickname, rank } = store
        const position = new naver.maps.LatLng(latitude, longitude)

        // 지도에 마커 추가
        const marker = new naver.maps.Marker({
          position,
          map,
        })

        // 마커에 정보창 추가
        const infoWindow = new naver.maps.InfoWindow({
          content: `<div style="padding:10px;min-width:150px;">${nickname}</div>`,
        })

        // 마커 클릭 시 정보창 열기
        naver.maps.Event.addListener(marker, 'click', () => {
          infoWindow.open(map, marker)
        })

        // 지도에 원 추가 (rank에 따라 원의 반경 크기 설정)
        const circle = new naver.maps.Circle({
          map: map,
          center: position,
          radius: getCircleSizeByRank(rank), // rank에 따라 반경 설정
          fillColor: 'rgba(255, 99, 132, 0.5)', // 원의 색상
          fillOpacity: 0.5,
          strokeColor: '#FF4365', // 원의 경계 색상
          strokeWeight: 2,
        })

        // bounds에 현재 마커 위치 추가
        bounds.extend(position)
      })

      // 지도 범위를 bounds에 맞게 설정
      map.fitBounds(bounds)
    }
  }, [storeData, map])

  return (
    <div>
      <div
        id="store-info"
        className="w-full h-screen object-cover bg-gray-medium"
      />
      <div className="p-4">
        {storeData.map((store) => (
          <div key={store.id} className="mb-2">
            <div className="font-bold">{store.nickname}</div>
            <div>{store.address}</div>
            <div>주문 수: {store.orderCount}</div>
            <div>랭킹: {store.rank}</div>
          </div>
        ))}
      </div>
    </div>
  )
}
