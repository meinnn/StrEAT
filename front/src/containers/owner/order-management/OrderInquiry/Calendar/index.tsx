import DatePicker from 'react-datepicker'
import '@/containers/owner/order-management/OrderInquiry/Calendar/calendarStyles.css'

interface CalendarProps {
  startDate: Date | null
  endDate?: Date | null
  selectsRange?: boolean // 날짜 범위 선택 여부
  onDateChange: (dates: [Date | null, Date | null] | Date | null) => void // 날짜 변경 핸들러
}

export default function CalendarComponent({
  startDate,
  endDate,
  selectsRange = false, // 기본값은 단일 날짜 선택
  onDateChange,
}: CalendarProps) {
  return (
    <div className="px-4">
      <DatePicker
        selected={startDate}
        onChange={onDateChange} // 날짜 선택 시 onDateChange를 통해 부모 컴포넌트로 값 전달
        startDate={startDate} // 시작 날짜
        endDate={endDate} // 종료 날짜 (있을 경우)
        selectsRange={selectsRange} // 날짜 범위 선택 옵션
        dateFormat="yyyy.MM.dd" // 날짜 포맷 설정
        inline // 달력 항상 보이기
      />
    </div>
  )
}

// defaultProps 설정
CalendarComponent.defaultProps = {
  endDate: null,
  selectsRange: false,
}
