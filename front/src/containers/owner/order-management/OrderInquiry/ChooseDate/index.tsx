import React, { useState } from 'react'
import { SlCalender } from 'react-icons/sl'
import Calendar from '@/containers/owner/order-management/OrderInquiry/Calendar' // 절대 경로로 Calendar 불러오기

export default function ChooseDate() {
  const today = new Date()
  const [startDate, setStartDate] = useState<Date | null>(
    new Date(today.setDate(today.getDate() - 7))
  )
  const [endDate, setEndDate] = useState<Date | null>(new Date())

  const [modalIsOpen, setModalIsOpen] = useState(false) // 모달 상태

  // 임시 상태 (모달에서 선택된 값을 저장)
  const [tempStartDate, setTempStartDate] = useState<Date | null>(startDate)
  const [tempEndDate, setTempEndDate] = useState<Date | null>(endDate)

  const openModal = () => {
    setTempStartDate(startDate) // 모달 열릴 때 현재 선택된 값을 임시 상태에 복사
    setTempEndDate(endDate)
    setModalIsOpen(true)
  }

  const closeModal = () => {
    setModalIsOpen(false)
  }

  // 확인 버튼 클릭 시 부모 상태에 값 반영
  const handleConfirm = () => {
    setStartDate(tempStartDate) // 선택된 임시 날짜를 부모 상태에 반영
    setEndDate(tempEndDate)
    closeModal()
  }

  return (
    <div className="mt-4 mx-2">
      {/* 상단 아이콘과 제목 */}
      <div
        className="text-gray-800 mx-2 flex items-center cursor-pointer"
        onClick={openModal}
      >
        <SlCalender className="mr-4 text-xl" />
        <span className="font-bold text-base">날짜 직접 선택</span>
      </div>

      {/* 날짜 범위 표시 */}
      <div className="text-gray-500 mt-1 text-sm ml-11">
        {startDate ? startDate.toLocaleDateString() : '날짜 선택 안됨'} ~{' '}
        {endDate ? endDate.toLocaleDateString() : '날짜 선택 안됨'}
      </div>

      {/* 하단 구분선 */}
      <div className="border-t border-gray-400 my-4 mx-2" />

      {/* 모달 */}
      {modalIsOpen && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-end z-50">
          <div className="bg-white w-full h-1/2 rounded-t-3xl p-4 transition-transform duration-300">
            <div className="flex justify-between pb-4">
              <button
                onClick={closeModal} // 취소 시 임시 상태만 초기화, 부모 상태는 변경되지 않음
                className="text-red-500 font-semibold"
              >
                취소
              </button>
              <button
                onClick={handleConfirm} // 확인 시 부모 상태에 값 반영
                className="text-blue-500 font-semibold"
              >
                확인
              </button>
            </div>

            {/* Calendar 컴포넌트 사용 */}
            <div>
              <Calendar
                startDate={tempStartDate}
                endDate={tempEndDate}
                selectsRange
                onDateChange={(update) => {
                  const [start, end] = update as [Date | null, Date | null]
                  setTempStartDate(start)
                  setTempEndDate(end)
                }}
              />
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
