'use client'

import { useState, useEffect } from 'react'
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from 'recharts'
import '@/containers/owner/store-management/SalesManagement/DateGraph/graphStyles.css'

// 가짜 데이터 대신 사용할 기본값 (로딩 전 상태)
const defaultData = {
  daily: [],
  weekly: [],
  monthly: [],
  yearly: [],
}

export default function DateGraph() {
  const [selectedRange, setSelectedRange] = useState('monthly')
  const [salesData, setSalesData] = useState(defaultData) // API로 불러온 데이터를 저장
  const [loading, setLoading] = useState(true)

  // 범위 선택 변경 시 호출되는 함수
  const handleRangeChange = (range: string) => {
    setSelectedRange(range)
  }

  // 데이터 불러오는 함수
  const fetchSalesData = async () => {
    try {
      // storeId를 쿼리 파라미터로 전달
      const storeId = 60 // storeId를 변수로 설정
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
      setSalesData({
        daily: result.daily,
        weekly: result.weekly,
        monthly: result.monthly,
        yearly: result.yearly,
      })
      setLoading(false)
    } catch (error) {
      console.error('Error fetching sales data:', error)
      setLoading(false)
    }
  }

  // 컴포넌트 마운트 시 데이터 불러오기
  useEffect(() => {
    fetchSalesData().then()
  }, [])

  // 선택된 범위에 따른 데이터를 반환하는 함수
  const getDisplayData = () => {
    if (loading) return [] // 로딩 중일 때는 빈 데이터 반환
    switch (selectedRange) {
      case 'daily':
        return salesData.daily.slice(0, 7) // 일주일치 데이터
      case 'weekly':
        return salesData.weekly.slice(0, 6) // 주간 데이터 6개
      case 'monthly':
        return salesData.monthly.slice(0, 6) // 월간 데이터 6개
      case 'yearly':
      default:
        return salesData.yearly // 년간 데이터
    }
  }

  const getDataKey = () => {
    switch (selectedRange) {
      case 'yearly':
        return 'year'
      case 'monthly':
        return 'month'
      case 'weekly':
        return 'week'
      default:
        return 'date'
    }
  }

  const formatYAxis = (value: number) => {
    if (value >= 1000000) return `${(value / 1000000).toFixed(1)}m`
    if (value >= 1000) return `${(value / 1000).toFixed(1)}k`
    return value.toString()
  }

  return (
    <div className="custom-graph p-2">
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

      {loading ? (
        <p>Loading...</p> // 데이터가 로딩 중일 때 표시
      ) : (
        <ResponsiveContainer width="100%" height={300}>
          <BarChart data={getDisplayData()} barCategoryGap="20%">
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey={getDataKey()} tick={{ fontSize: 12 }} />
            <YAxis tickFormatter={formatYAxis} tick={{ fontSize: 12 }} />
            <Tooltip />
            <Legend />
            <Bar
              dataKey="sales"
              className="bar"
              background={{ className: 'bar-background' }}
              radius={[20, 20, 0, 0]}
            />
          </BarChart>
        </ResponsiveContainer>
      )}
    </div>
  )
}
