'use client'

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

const defaultData = {
  daily: {},
  weekly: {},
  monthly: {},
  yearly: {},
}

export default function DateGraph({
  salesData = defaultData, // 부모로부터 받은 데이터
  selectedRange, // 부모로부터 받은 필터 상태
}: {
  salesData: any
  selectedRange: string
}) {
  // 선택된 범위에 따른 데이터를 반환하는 함수
  const getDisplayData = () => {
    switch (selectedRange) {
      case 'daily':
        return Object.entries(salesData.daily).map(([date, value]) => ({
          date,
          sales: value,
        }))
      case 'weekly':
        return Object.entries(salesData.weekly).map(([week, value]) => ({
          week,
          sales: value,
        }))
      case 'monthly':
        return Object.entries(salesData.monthly).map(([month, value]) => ({
          month,
          sales: value,
        }))
      case 'yearly':
      default:
        return Object.entries(salesData.yearly).map(([year, value]) => ({
          year,
          sales: value,
        }))
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
    </div>
  )
}
