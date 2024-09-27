import React, { useState } from 'react'
import { SlCalender } from 'react-icons/sl'
import CalendarComponent from '@/containers/owner/order-management/OrderInquiry/Calendar' // 절대 경로로 Calendar 불러오기

export default function ChooseDate() {
  const today = new Date()
  const [startDate, setStartDate] = useState<Date | null>(
    new Date(today.setDate(today.getDate() - 7))
  )
  const [endDate, setEndDate] = useState<Date | null>(new Date())

  const [modalIsOpen, setModalIsOpen] = useState(false) // 모달 상태

  const openModal = () => setModalIsOpen(true)
  const closeModal = () => setModalIsOpen(false)

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
                onClick={closeModal}
                className="text-red-500 font-semibold"
              >
                취소
              </button>
              <button
                onClick={closeModal}
                className="text-blue-500 font-semibold"
              >
                확인
              </button>
            </div>

            {/* Calendar 컴포넌트 사용 */}
            <div className="px-4">
              <CalendarComponent
                startDate={startDate}
                endDate={endDate}
                selectsRange
                onDateChange={(update) => {
                  const [start, end] = update as [Date | null, Date | null]
                  setStartDate(start)
                  setEndDate(end)
                }}
              />
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
