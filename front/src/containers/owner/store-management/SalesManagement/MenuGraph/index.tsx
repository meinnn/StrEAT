'use client'

import { PieChart, Pie, Cell, Tooltip, ResponsiveContainer } from 'recharts'
import { useEffect, useState } from 'react'

// 원형 그래프에서 사용할 색상 배열
const COLORS = ['#F5B63F', '#61AF3E', '#D8D8D8', '#246FB7', '#FF4365']

interface MenuGraphProps {
  salesData: any // 부모 컴포넌트에서 전달받는 판매 데이터
  selectedRange: string // 선택된 범위 (daily, weekly, monthly, yearly)
}

export default function MenuGraph({
  salesData,
  selectedRange,
}: MenuGraphProps) {
  const [menuData, setMenuData] = useState<any[]>([]) // 그래프에 표시할 메뉴 데이터를 저장

  // 선택된 범위에 맞는 product 데이터를 설정하는 함수
  const getProductData = () => {
    if (!salesData) return []

    switch (selectedRange) {
      case 'daily':
        return Object.entries(salesData.dailyProduct || {}).map(
          ([name, value]: any) => ({
            name,
            value: value.quantity,
            percentage: value.percent,
          })
        )
      case 'weekly':
        return Object.entries(salesData.weeklyProduct || {}).map(
          ([name, value]: any) => ({
            name,
            value: value.quantity,
            percentage: value.percent,
          })
        )
      case 'monthly':
        return Object.entries(salesData.monthlyProduct || {}).map(
          ([name, value]: any) => ({
            name,
            value: value.quantity,
            percentage: value.percent,
          })
        )
      case 'yearly':
        return Object.entries(salesData.yearlyProduct || {}).map(
          ([name, value]: any) => ({
            name,
            value: value.quantity,
            percentage: value.percent,
          })
        )
      default:
        return []
    }
  }

  // selectedRange가 변경될 때마다 menuData 업데이트
  useEffect(() => {
    setMenuData(getProductData())
  }, [salesData, selectedRange])

  return (
    <div
      className="w-full flex flex-col items-center mb-4 mt-6"
      style={{
        padding: '20px',
        backgroundColor: '#FFFBF3',
        borderRadius: '15px',
        maxWidth: '600px',
      }}
    >
      {/* ResponsiveContainer로 원형 그래프 출력 */}
      <ResponsiveContainer width="100%" height={280}>
        <PieChart>
          <Pie
            data={menuData}
            cx="50%"
            cy="50%"
            labelLine={false}
            label={({ percent }) => `${(percent * 100).toFixed(0)}%`}
            outerRadius={100}
            fill="#8884d8"
            dataKey="value"
          >
            {menuData.map((entry, index) => (
              <Cell
                key={`cell-${index}`}
                fill={COLORS[index % COLORS.length]}
              />
            ))}
          </Pie>
          <Tooltip />
        </PieChart>
      </ResponsiveContainer>

      {/* 메뉴와 퍼센트를 나열해서 보여주기 */}
      <div
        className="w-full grid grid-cols-2 gap-2"
        style={{
          display: 'grid',
          gridTemplateColumns: 'repeat(2, 1fr)',
          gap: '20px',
          maxWidth: '500px',
        }}
      >
        {menuData.map((entry) => (
          <div
            key={entry.name}
            className="flex items-center justify-between"
            style={{
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'space-between',
            }}
          >
            <div className="flex items-center">
              <div
                className="w-3 h-3 rounded-full"
                style={{
                  backgroundColor:
                    COLORS[menuData.indexOf(entry) % COLORS.length],
                  marginRight: '8px',
                }}
              />
              <span>{entry.name}</span>
            </div>
            <span>{entry.percentage}%</span>
            <span>{entry.value}개</span>
          </div>
        ))}
      </div>
    </div>
  )
}
