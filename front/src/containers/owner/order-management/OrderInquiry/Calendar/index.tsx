import React from 'react'
import DatePicker from 'react-datepicker'
import { ko } from 'date-fns/locale'
import { FaChevronLeft, FaChevronRight } from 'react-icons/fa'
import '@/containers/owner/order-management/OrderInquiry/Calendar/calendarStyles.css'

interface CalendarProps {
  startDate: Date | null
  endDate: Date | null
  selectsRange: boolean // 날짜 범위 선택 여부
  onDateChange: (dates: [Date | null, Date | null] | null) => void // 날짜 변경 핸들러
}

export default function Calendar({
  startDate,
  endDate = null,
  selectsRange = false, // 기본값은 단일 날짜 선택
  onDateChange,
}: CalendarProps) {
  return (
    <div>
      <DatePicker
        selected={startDate ?? undefined} // undefined 처리
        onChange={(dates: [Date | null, Date | null] | null) => {
          if (!dates) return
          const [start, end] = dates
          onDateChange([start, end]) // 부모 컴포넌트로 날짜 반영
        }}
        startDate={startDate ?? undefined} // undefined 처리
        endDate={endDate ?? undefined} // undefined 처리
        selectsRange
        dateFormat="yyyy.MM.dd" // 날짜 포맷 설정
        inline // 달력 항상 보이기
        locale={ko} // 한국어 로케일 설정
        maxDate={new Date()} // 오늘 날짜까지만 선택 가능
        renderCustomHeader={({ date, decreaseMonth, increaseMonth }) => (
          <div className="flex justify-between items-center px-4 pb-4">
            <button onClick={decreaseMonth}>
              <FaChevronLeft size={20} color="#ff4081" />
            </button>
            <span className="text-lg font-semibold">
              {date.toLocaleDateString('ko-KR', {
                year: 'numeric',
                month: 'long',
              })}
            </span>
            <button onClick={increaseMonth}>
              <FaChevronRight size={20} color="#ff4081" />
            </button>
          </div>
        )}
      />
    </div>
  )
}
