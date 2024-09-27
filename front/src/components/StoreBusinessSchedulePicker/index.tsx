/* eslint-disable jsx-a11y/control-has-associated-label */

'use client'

import { useState } from 'react'

const DAYS = [
  {
    id: 'mon',
    label: '월',
    start: '오전 09:30',
    end: '오후 08:40',
    order: 1,
  },
  {
    id: 'tue',
    label: '화',
    start: '오전 09:30',
    end: '오후 08:40',
    order: 2,
  },
  {
    id: 'wed',
    label: '수',
    start: '오전 09:30',
    end: '오후 08:40',
    order: 3,
  },
  {
    id: 'thu',
    label: '목',
    start: '오전 09:30',
    end: '오후 08:40',
    order: 4,
  },
  {
    id: 'fri',
    label: '금',
    start: '오전 09:30',
    end: '오후 08:40',
    order: 5,
  },
  {
    id: 'sat',
    label: '토',
    start: '오전 09:30',
    end: '오후 08:40',
    order: 6,
  },
  {
    id: 'sun',
    label: '일',
    start: '오전 09:30',
    end: '오후 08:40',
    order: 7,
  },
]

interface Day {
  id: string
  label: string
  start: string
  end: string
  order: number
}

export default function StoreBusinessSchedulePicker() {
  const [selectedDays, setSelectedDays] = useState<Day[]>([])

  const toggleDay = (day: Day) => {
    setSelectedDays((prevSelectedDays) =>
      prevSelectedDays.some((selectedDay) => selectedDay.id === day.id)
        ? prevSelectedDays.filter((selectedDay) => selectedDay.id !== day.id)
        : [...prevSelectedDays, day]
    )
  }

  return (
    <section className="flex flex-col w-full">
      <h3 className="font-medium text-xl text-text mb-3">영업일 및 영업시간</h3>
      <div className="flex justify-between mb-7">
        {DAYS.map((day) => {
          return (
            <button
              type="button"
              key={day.id}
              className={`w-10 h-10 flex items-center justify-center border rounded-2xl ${
                selectedDays.find(
                  (selectedDay) => day.label === selectedDay.label
                )
                  ? 'text-primary-500 border-primary-500'
                  : 'border-text text-text'
              }`}
              onClick={() => toggleDay(day)}
            >
              {day.label}
            </button>
          )
        })}
      </div>
      {selectedDays.length > 0 ? (
        <table className="table-auto border-collapse w-full text-center">
          <thead>
            <tr>
              <th className="w-1/5" />
              <th className="w-2/5 text-xs ">영업 시작 시간</th>
              <th className="w-2/5 text-xs ">영업 끝 시간</th>
            </tr>
          </thead>
          <tbody>
            {selectedDays
              .toSorted((a, b) => a.order - b.order)
              .map((day) => {
                return (
                  <tr key={day.id}>
                    <td className="text-xl font-normal">{day.label}</td>
                    <td className="p-2">
                      <input
                        type="time"
                        className="w-full outline-none py-2 rounded-lg border border-gray-dark text-center"
                      />
                    </td>
                    <td className="p-2">
                      <input
                        type="time"
                        className="w-full outline-none py-2 rounded-lg border border-gray-dark text-center"
                      />
                    </td>
                  </tr>
                )
              })}
          </tbody>
        </table>
      ) : null}
    </section>
  )
}
