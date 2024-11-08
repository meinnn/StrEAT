'use client'

import { PieChart, Pie, Cell, Tooltip, ResponsiveContainer } from 'recharts'
import { useEffect, useState } from 'react'

const COLORS = ['#F5B63F', '#61AF3E', '#D8D8D8', '#246FB7', '#FF4365']

interface MenuGraphProps {
  salesData: Record<string, any>
  selectedRange: string
}

export default function MenuGraph({
  salesData,
  selectedRange,
}: MenuGraphProps) {
  const [menuData, setMenuData] = useState<
    { name: string; value: number; percentage: number }[]
  >([])

  useEffect(() => {
    const getProductData = () => {
      if (!salesData) return []

      const rangeData = salesData[`${selectedRange}Product`] || {}
      return Object.entries(rangeData).map(([name, value]: any) => ({
        name,
        value: value.quantity,
        percentage: value.percent,
      }))
    }

    setMenuData(getProductData())
  }, [salesData, selectedRange])

  return (
    <div className="w-full flex flex-col items-center mb-4 mt-6 p-5 bg-yellow-50 rounded-lg max-w-md">
      <ResponsiveContainer width="100%" height={280}>
        <PieChart>
          <Pie
            data={menuData}
            cx="50%"
            cy="50%"
            labelLine={false}
            label={({ percent }) => `${Math.floor(percent * 100)}%`} // 소수점 아래 버림
            outerRadius={100}
            fill="#8884d8"
            dataKey="value"
          >
            {menuData.map((entry) => (
              <Cell
                key={entry.name} // 'name' 필드를 사용해 고유한 key 지정
                fill={COLORS[menuData.indexOf(entry) % COLORS.length]}
              />
            ))}
          </Pie>
          <Tooltip />
        </PieChart>
      </ResponsiveContainer>

      <div className="w-full grid grid-cols-2 gap-5 max-w-xs">
        {menuData.map((entry) => (
          <div key={entry.name} className="flex items-center justify-between">
            <div className="flex items-center">
              <div
                className="w-3 h-3 rounded-full mr-2"
                style={{
                  backgroundColor:
                    COLORS[menuData.indexOf(entry) % COLORS.length],
                }}
              />
              <span>{entry.name}</span>
            </div>
            <span>{Math.floor(entry.percentage)}%</span>{' '}
            {/* 소수점 아래 버림 */}
            <span>{entry.value}개</span>
          </div>
        ))}
      </div>
    </div>
  )
}
