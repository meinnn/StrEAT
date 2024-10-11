'use client'

import { useState } from 'react'
import DatePicker from 'react-datepicker'
import { ko } from 'date-fns/locale'
import '@/containers/owner/store-management/AssetCalendar/SingleDatePicker/singleDatePicker.css'

// 거래 데이터를 기반으로 일별 총 결제 금액을 표시하는 함수
const dayContents = (day: number, date: Date, transactions: any[]) => {
  const adjustedDate = new Date(date) // 날짜 복사
  adjustedDate.setDate(day) // 날짜 그대로 사용
  adjustedDate.setDate(adjustedDate.getDate() - 1) // 하루를 빼서 이전 날짜에 데이터를 맞춤
  const dayIndex = adjustedDate.getDate() // 날짜에서 일(day) 추출
  const transaction = transactions[dayIndex] || null // 해당 일자에 맞는 거래 데이터 찾기

  return (
    <div className="day-container">
      <span>{day}</span>
      {transaction && transaction.dailyTotalPayAmount > 0 && (
        <div style={{ color: 'blue', fontSize: '0.7rem' }}>
          <div>+{transaction.dailyTotalPayAmount.toLocaleString()}</div>
        </div>
      )}
    </div>
  )
}

export default function SingleDatePicker({
  onChange,
  transactions = [], // transactions 기본값을 빈 배열로 설정
}: {
  onChange: (date: string) => void
  transactions: any[]
}) {
  const [selectedDate, setSelectedDate] = useState<Date | null>(new Date())

  const handleDateChange = (date: Date | null) => {
    if (date) {
      setSelectedDate(date)
      const formattedDate = date.toISOString().split('T')[0] // UTC 변환 없이 날짜 형식만 추출
      onChange(formattedDate)
    }
  }

  return (
    <div>
      <DatePicker
        selected={selectedDate}
        onChange={handleDateChange}
        dateFormat="yyyy.MM.dd"
        inline
        locale={ko}
        maxDate={new Date()}
        renderCustomHeader={({ date, decreaseMonth, increaseMonth }) => (
          <div className="flex justify-between items-center px-20 pb-4">
            {/* 이전 달 버튼 */}
            <button
              onClick={decreaseMonth}
              aria-label="이전 달"
              className="text-primary-400"
            >
              ◀
            </button>

            {/* 가운데 월 표시 */}
            <span className="text-lg font-semibold">
              {date.toLocaleDateString('ko-KR', {
                year: 'numeric',
                month: 'long',
              })}
            </span>

            {/* 다음 달 버튼 */}
            <button
              onClick={increaseMonth}
              aria-label="다음 달"
              className="text-primary-400"
            >
              ▶
            </button>
          </div>
        )}
        renderDayContents={(day, date) => dayContents(day, date, transactions)} // dayContents를 사용하여 일별 데이터를 렌더링
      />
    </div>
  )
}
