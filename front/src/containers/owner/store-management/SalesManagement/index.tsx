import { useState, useEffect } from 'react'
import DateGraph from '@/containers/owner/store-management/SalesManagement/DateGraph'
import MenuGraph from '@/containers/owner/store-management/SalesManagement/MenuGraph'

interface MenuGraphProps {
  salesData: Record<string, any> // 구체적인 타입으로 수정 가능
  selectedRange: string
}

export default function SalesManagement() {
  const [salesData, setSalesData] = useState<Record<string, any> | null>(null)
  const [loading, setLoading] = useState(true)
  const [selectedRange, setSelectedRange] = useState('monthly') // 필터 상태 추가

  // API 요청을 보내서 데이터 받아오기
  const fetchSalesData = async () => {
    try {
      const storeId = 60 // storeId를 설정
      const response = await fetch(`/services/graph?storeId=${storeId}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      })

      if (!response.ok) {
        throw new Error('Failed to fetch sales data')
      }

      const result = await response.json()
      setSalesData(result.data) // 응답 데이터의 data 필드만 저장
      setLoading(false) // 로딩 상태 업데이트
    } catch (error) {
      console.error('Error fetching sales data:', error)
      setLoading(false)
    }
  }

  // 컴포넌트 마운트 시 데이터 불러오기
  useEffect(() => {
    fetchSalesData()
  }, []) // fetchSalesData는 의존성 배열에 추가할 필요 없음

  // 필터 버튼 클릭 시 호출되는 함수
  const handleRangeChange = (range: string) => {
    setSelectedRange(range)
  }

  return (
    <div>
      {/* 필터 버튼 UI */}
      <div className="flex justify-start space-x-2 mb-4 mt-2 ml-2">
        <button
          onClick={() => handleRangeChange('daily')}
          className={`w-20 px-4 py-2 rounded-full ${
            selectedRange === 'daily'
              ? 'bg-[#471515] text-white'
              : 'bg-[#f5f2ea] text-black'
          }`}
        >
          Daily
        </button>
        <button
          onClick={() => handleRangeChange('weekly')}
          className={`w-20 px-4 py-2 rounded-full ${
            selectedRange === 'weekly'
              ? 'bg-[#471515] text-white'
              : 'bg-[#f5f2ea] text-black'
          }`}
        >
          Weekly
        </button>
        <button
          onClick={() => handleRangeChange('monthly')}
          className={`w-20 px-3 py-2 rounded-full ${
            selectedRange === 'monthly'
              ? 'bg-[#471515] text-white'
              : 'bg-[#f5f2ea] text-black'
          }`}
        >
          Monthly
        </button>
        <button
          onClick={() => handleRangeChange('yearly')}
          className={`w-20 px-4 py-2 rounded-full ${
            selectedRange === 'yearly'
              ? 'bg-[#471515] text-white'
              : 'bg-[#f5f2ea] text-black'
          }`}
        >
          Yearly
        </button>
      </div>

      {/* 자식 컴포넌트로 데이터 및 필터 상태 전달 */}
      {loading ? (
        <p>Loading...</p> // 데이터 로딩 중일 때 메시지 출력
      ) : (
        salesData && (
          <>
            <DateGraph salesData={salesData} selectedRange={selectedRange} />
            <MenuGraph salesData={salesData} selectedRange={selectedRange} />
          </>
        )
      )}
    </div>
  )
}
