'use client'

import { useState } from 'react'
import DatePicker from 'react-datepicker'
import { ko } from 'date-fns/locale'
import '@/containers/owner/store-management/AssetCalendar/SingleDatePicker/singleDatePicker.css'

// 거래 데이터를 기반으로 입출금 금액을 표시하는 함수
const dayContents = (day: number, date: Date, transactions: any[]) => {
  const dateString = date
    .toLocaleDateString('ko-KR', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
    })
    .replace(/. /g, '-')
    .replace('.', '') // 날짜를 로컬 문자열로 변환
  const transaction = transactions.find((t) => t.date === dateString) // 문자열로 비교

  return (
    <div className="day-container">
      <span>{day}</span>
      {transaction && (
        <>
          <div style={{ color: 'blue', fontSize: '0.8rem' }}>
            +{transaction.income.toLocaleString()} 원
          </div>
          <div style={{ color: 'red', fontSize: '0.8rem' }}>
            -{transaction.outcome.toLocaleString()} 원
          </div>
        </>
      )}
    </div>
  )
}

export default function SingleDatePicker({
  onChange,
  transactions,
}: {
  onChange: (date: string) => void
  transactions: any[]
}) {
  const [selectedDate, setSelectedDate] = useState<Date | null>(new Date())

  const handleDateChange = (date: Date | null) => {
    if (date) {
      console.log('날짜 변경됨: ', date)
      setSelectedDate(date)
      const formattedDate = date
        .toLocaleDateString('ko-KR', {
          year: 'numeric',
          month: '2-digit',
          day: '2-digit',
        })
        .replace(/. /g, '-')
        .replace('.', '') // 로컬 날짜로 변환 후 문자열로 전달
      onChange(formattedDate)
    }
  }

  const renderDayContents = (day: number, date: Date) => {
    const dateString = date
      .toLocaleDateString('ko-KR', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
      })
      .replace(/. /g, '-')
      .replace('.', '')
    const transaction = transactions.find((t) => t.date === dateString)

    return (
      <div>
        <span>{day}</span>
        {transaction && (
          <div style={{ fontSize: '0.7rem', color: 'blue' }}>
            +{transaction.income.toLocaleString()}
          </div>
        )}
        {transaction && (
          <div style={{ fontSize: '0.7rem', color: 'red' }}>
            -{transaction.outcome.toLocaleString()}
          </div>
        )}
      </div>
    )
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
        renderDayContents={renderDayContents}
      />
    </div>
  )
}
