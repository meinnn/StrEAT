'use client'

import { useState } from 'react'
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

const sampleData = {
  daily: [
    { date: '2024-10-01', sales: 4000 },
    { date: '2024-10-02', sales: 3000 },
    { date: '2024-10-03', sales: 2000 },
    { date: '2024-10-04', sales: 4500 },
    { date: '2024-10-05', sales: 3200 },
    { date: '2024-10-06', sales: 2800 },
    { date: '2024-10-07', sales: 3800 },
  ],
  weekly: [
    { week: 'Week 1', sales: 21000 },
    { week: 'Week 2', sales: 17000 },
    { week: 'Week 3', sales: 25000 },
    { week: 'Week 4', sales: 23000 },
    { week: 'Week 5', sales: 24000 },
    { week: 'Week 6', sales: 22000 },
  ],
  monthly: [
    { month: 'January', sales: 43000 },
    { month: 'February', sales: 39000 },
    { month: 'March', sales: 50000 },
    { month: 'April', sales: 47000 },
    { month: 'May', sales: 52000 },
    { month: 'June', sales: 49000 },
    { month: 'July', sales: 51000 },
    { month: 'August', sales: 48000 },
    { month: 'September', sales: 53000 },
    { month: 'October', sales: 49000 },
    { month: 'November', sales: 47000 },
    { month: 'December', sales: 52000 },
  ],
  yearly: [
    { year: '2022', sales: 450000 },
    { year: '2023', sales: 380000 },
    { year: '2024', sales: 570000 },
  ],
}

export default function DateGraph() {
  const [selectedRange, setSelectedRange] = useState('monthly')

  const handleRangeChange = (range: string) => {
    setSelectedRange(range)
  }

  const getDisplayData = () => {
    switch (selectedRange) {
      case 'daily':
        return sampleData.daily.slice(0, 7) // 일주일치 데이터
      case 'weekly':
        return sampleData.weekly.slice(0, 6) // 주간 데이터 6개
      case 'monthly':
        return sampleData.monthly.slice(0, 6) // 월간 데이터 6개
      case 'yearly':
      default:
        return sampleData.yearly // 년간 데이터
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
