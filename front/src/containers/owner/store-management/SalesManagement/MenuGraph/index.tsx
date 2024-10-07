'use client'

import { PieChart, Pie, Cell, Tooltip, ResponsiveContainer } from 'recharts'

const menuData = {
  daily: [
    { id: 1, name: '후라이드', value: 65, percentage: 0.4 },
    { id: 2, name: '양념', value: 1556, percentage: 37 },
    { id: 3, name: '트러플', value: 1003, percentage: 33 },
    { id: 4, name: '뿌링클', value: 265, percentage: 16 },
    { id: 5, name: '굽네', value: 1026, percentage: 10 },
  ],
  weekly: [
    { id: 1, name: '후라이드', value: 320, percentage: 2 },
    { id: 2, name: '양념', value: 850, percentage: 50 },
    { id: 3, name: '트러플', value: 600, percentage: 35 },
    { id: 4, name: '뿌링클', value: 130, percentage: 8 },
    { id: 5, name: '굽네', value: 100, percentage: 5 },
  ],
  monthly: [
    { id: 1, name: '후라이드', value: 1200, percentage: 3 },
    { id: 2, name: '양념', value: 15000, percentage: 40 },
    { id: 3, name: '트러플', value: 12000, percentage: 32 },
    { id: 4, name: '뿌링클', value: 4500, percentage: 12 },
    { id: 5, name: '굽네', value: 3500, percentage: 13 },
  ],
  yearly: [
    { id: 1, name: '후라이드', value: 15000, percentage: 4 },
    { id: 2, name: '양념', value: 175000, percentage: 38 },
    { id: 3, name: '트러플', value: 125000, percentage: 27 },
    { id: 4, name: '뿌링클', value: 50000, percentage: 11 },
    { id: 5, name: '굽네', value: 80000, percentage: 20 },
  ],
}

const COLORS = ['#F5B63F', '#61AF3E', '#D8D8D8', '#246FB7', '#FF4365']

const RADIAN = Math.PI / 180

const renderCustomizedLabel = ({
  cx,
  cy,
  midAngle,
  innerRadius,
  outerRadius,
  percent,
  index,
}: {
  cx: number
  cy: number
  midAngle: number
  innerRadius: number
  outerRadius: number
  percent: number
  index: number
}) => {
  const radius = innerRadius + (outerRadius - innerRadius) * 0.5
  const x = cx + radius * Math.cos(-midAngle * RADIAN)
  const y = cy + radius * Math.sin(-midAngle * RADIAN)

  return (
    <text
      x={x}
      y={y}
      fill="black"
      textAnchor={x > cx ? 'start' : 'end'}
      dominantBaseline="central"
      fontSize="12"
    >
      {`${(percent * 100).toFixed(0)}%`}
    </text>
  )
}

export default function MenuGraph() {
  const data = menuData.daily

  return (
    <div
      className="w-full flex flex-col items-center mb-4"
      style={{
        padding: '20px',
        backgroundColor: '#FFFBF3',
        borderRadius: '15px',
        maxWidth: '600px',
      }}
    >
      <ResponsiveContainer width="100%" height={270}>
        <PieChart>
          <Pie
            data={data}
            cx="50%"
            cy="50%"
            labelLine={false}
            label={false}
            outerRadius={100}
            fill="#8884d8"
            dataKey="value"
          >
            {data.map((entry) => (
              <Cell
                key={`cell-${entry.id}`}
                fill={COLORS[entry.id % COLORS.length]}
              />
            ))}
          </Pie>
          <Tooltip />
        </PieChart>
      </ResponsiveContainer>
      <div
        className="w-full grid grid-cols-2 gap-2"
        style={{
          display: 'grid',
          gridTemplateColumns: 'repeat(2, 1fr)',
          gap: '20px',
          maxWidth: '500px',
        }}
      >
        {data.map((entry) => (
          <div
            key={entry.id}
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
                  backgroundColor: COLORS[entry.id % COLORS.length],
                  marginRight: '8px',
                }}
              />
              <span>{entry.name}</span>
            </div>
            <span>{entry.percentage}%</span>
            <span>{entry.value}</span>
          </div>
        ))}
      </div>
    </div>
  )
}
