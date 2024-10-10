'use client'

import { useState, useEffect, useRef } from 'react'
import useNaverMap from '@/hooks/useNaverMap'

interface AnalysisData {
  districtName: string
  businessDistrictName: string
  ageGroup: { bestGroup: string }
  genderGroup: { bestGroup: string }
  dayGroup: { bestGroup: string }
  timeGroup: { bestGroup: string }
  sameStoreNum: number
}

export default function Analysis() {
  const [analysisData, setAnalysisData] = useState<AnalysisData | null>(null)
  const { map } = useNaverMap('map', { zoom: 16, minZoom: 6 })
  const [marker, setMarker] = useState<naver.maps.Marker | null>(null) // 마커 상태
  const markerRef = useRef<naver.maps.Marker | null>(null)

  const fetchAnalysisData = async (latitude: number, longitude: number) => {
    try {
      const response = await fetch(
        `/services/business-area/area?latitude=${latitude}&longitude=${longitude}`
      )
      if (!response.ok) {
        throw new Error('API 요청 실패')
      }
      const data = await response.json()
      setAnalysisData(data.data) // 데이터를 상태로 저장
    } catch (error) {
      console.error('Error fetching analysis data:', error)
    }
  }

  const mark = (lat: any, lng: any) => {
    if (!map) return
    markerRef.current = new naver.maps.Marker({
      position: new naver.maps.LatLng(lat, lng),
      map,
      icon: {
        content: `<img src="/images/truck.png" class="w-8 h-6"/>`,
      },
    })
  }

  useEffect(() => {
    console.log(map, naver)
    if (map && naver) {
      const latitude = 37.526126
      const longitude = 126.922255

      const initialPosition = new naver.maps.LatLng(latitude, longitude)

      // 마커 생성 및 초기 위치 설정
      const newMarker = new naver.maps.Marker({
        position: initialPosition,
        map,
        title: '클릭해서 상권 분석 데이터를 불러오세요',
      })

      setMarker(newMarker)

      // 지도 클릭 이벤트 설정 (사용자가 클릭한 위치의 데이터를 불러오기)
      naver.maps.Event.addListener(map, 'click', (e: any) => {
        const clickedLat = e.coord.lat()
        const clickedLng = e.coord.lng()

        if (markerRef.current) {
          markerRef.current.setMap(null)
        }

        mark(clickedLat, clickedLng)

        const newPosition = new naver.maps.LatLng(clickedLat, clickedLng)

        map.setCenter(newPosition)

        if (marker) {
          // 마커 위치를 클릭한 좌표로 이동
          marker.setPosition(new naver.maps.LatLng(clickedLat, clickedLng))
        }

        // 클릭한 위치의 상권 분석 데이터를 불러오기
        fetchAnalysisData(clickedLat, clickedLng)
      })
    }
  }, [map])

  return (
    <div className="relative w-full h-screen">
      {/* 지도 컨테이너 */}
      <div id="map" className="w-full h-full" />

      {/* 상권 분석 데이터 표시 */}
      <div className="bottom-20 left-10 bg-white p-4 rounded-lg shadow-md z-10">
        {analysisData ? (
          <div>
            <h2>상권 분석 결과</h2>
            <p>상권 이름: {analysisData?.districtName}</p>
            <p>상권 유형: {analysisData?.businessDistrictName}</p>
            <p>연령대: {analysisData?.ageGroup?.bestGroup}가 가장 많습니다.</p>
            <p>성별: {analysisData?.genderGroup?.bestGroup}이 가장 많습니다.</p>
            <p>요일: {analysisData?.dayGroup?.bestGroup}이 가장 좋습니다.</p>
            <p>시간대: {analysisData?.timeGroup?.bestGroup}가 가장 좋습니다.</p>
            <p>주변 유사 가게 수: {analysisData?.sameStoreNum}개</p>
          </div>
        ) : (
          <p>데이터를 불러오는 중...</p>
        )}
      </div>
    </div>
  )
}
