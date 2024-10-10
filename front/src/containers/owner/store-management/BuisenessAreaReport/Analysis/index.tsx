'use clinet'

import { useState, useEffect } from 'react'

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

  useEffect(() => {
    const latitude = 37.526126
    const longitude = 126.922255
    fetchAnalysisData(latitude, longitude)
  }, [])

  return (
    <div>
      {analysisData ? (
        <div>
          <h2>상권 분석 결과</h2>
          <p>상권 이름: {analysisData.districtName}</p>
          <p>상권 유형: {analysisData.businessDistrictName}</p>
          <p>연령대: {analysisData.ageGroup.bestGroup}가 가장 많습니다.</p>
          <p>성별: {analysisData.genderGroup.bestGroup}이 가장 많습니다.</p>
          <p>요일: {analysisData.dayGroup.bestGroup}이 가장 좋습니다.</p>
          <p>시간대: {analysisData.timeGroup.bestGroup}가 가장 좋습니다.</p>
          <p>주변 유사 가게 수: {analysisData.sameStoreNum}개</p>
        </div>
      ) : (
        <p>데이터를 불러오는 중...</p>
      )}
    </div>
  )
}
