'use client'

import { useState } from 'react'
import DatePicker from 'react-datepicker'
import { ko } from 'date-fns/locale'
import '@/containers/owner/store-management/AssetCalendar/SingleDatePicker/singleDatePicker.css'

// 입출금 더미 데이터
const transactionData: {
  [key: string]: {
    income: number
    outcome: number
  }
} = {
  '2024-10-01': {
    income: 50000,
    outcome: 30000,
  },
  '2024-10-02': {
    income: 45000,
    outcome: 32000,
  },
  '2024-10-03': {
    income: 47000,
    outcome: 35000,
  },
  // 더미 데이터 추가...
}

// dayContents 함수 외부 정의
const dayContents = (day: number, date: Date) => {
  const dateString = date.toISOString().split('T')[0]
  const transaction = transactionData[dateString]
  const total = transaction ? transaction.income - transaction.outcome : null
  return (
    <div className="day-container">
      <span>{day}</span>
      {total !== null && (
        <div className="transaction-amount">
          {total > 0
            ? `+${total.toLocaleString()}`
            : `${total.toLocaleString()}`}{' '}
          원
        </div>
      )}
    </div>
  )
}

export default function SingleDatePicker() {
  const [selectedDate, setSelectedDate] = useState<Date | null>(new Date())

  return (
    <div className="calendar-container">
      <DatePicker
        selected={selectedDate}
        onChange={(date) => setSelectedDate(date)}
        dateFormat="yyyy.MM.dd"
        inline
        locale={ko}
        maxDate={new Date()}
        renderCustomHeader={({ date, decreaseMonth, increaseMonth }) => (
          <div className="flex justify-between items-center px-6 pb-4">
            <button onClick={decreaseMonth}>&lt;</button>
            <span className="text-lg font-semibold">
              {date.toLocaleDateString('ko-KR', {
                year: 'numeric',
                month: 'long',
              })}
            </span>
            <button onClick={increaseMonth}>&gt;</button>
          </div>
        )}
        dayContents={dayContents} // dayContents 함수를 전달
      />
    </div>
  )
}
