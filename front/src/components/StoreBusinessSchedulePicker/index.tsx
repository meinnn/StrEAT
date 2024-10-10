/* eslint-disable jsx-a11y/control-has-associated-label */
// eslint-disable-next-line react-hooks/rules-of-hooks

import { useEffect, useState } from 'react'
import { useMyStoreInfo } from '@/hooks/useMyStoreInfo'
import { useOwnerInfo } from '@/hooks/useOwnerInfo'

const DAYS = [
  {
    id: 'monday',
    label: '월',
    start: '09:00',
    end: '20:00',
    order: 1,
  },
  {
    id: 'tuesday',
    label: '화',
    start: '09:00',
    end: '20:00',
    order: 2,
  },
  {
    id: 'wednesday',
    label: '수',
    start: '09:00',
    end: '20:00',
    order: 3,
  },
  {
    id: 'thursday',
    label: '목',
    start: '09:00',
    end: '20:00',
    order: 4,
  },
  {
    id: 'friday',
    label: '금',
    start: '09:00',
    end: '20:00',
    order: 5,
  },
  {
    id: 'saturday',
    label: '토',
    start: '09:00',
    end: '20:00',
    order: 6,
  },
  {
    id: 'sunday',
    label: '일',
    start: '09:00',
    end: '20:00',
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

export default function StoreBusinessSchedulePicker({
  setBusinessDays,
  defaultBusinessDays,
}: {
  setBusinessDays: React.Dispatch<React.SetStateAction<string[]>>
  defaultBusinessDays: any
}) {
  const {
    data: ownerInfo,
    error: ownerInfoError,
    isLoading: ownerInfoLoading,
  } = useOwnerInfo()
  const {
    data: storeInfo,
    error,
    isLoading,
  } = useMyStoreInfo(ownerInfo?.storeId)
  const [selectedDays, setSelectedDays] = useState<Day[]>([])

  const toggleDay = (day: Day) => {
    setSelectedDays((prevSelectedDays) =>
      prevSelectedDays.some((selectedDay) => selectedDay.id === day.id)
        ? prevSelectedDays.filter((selectedDay) => selectedDay.id !== day.id)
        : [...prevSelectedDays, day]
    )
  }

  const handleTimeChange = (e: any, id: string, type: string) => {
    const { value } = e.target
    setSelectedDays((prevSelectedDays) =>
      prevSelectedDays.map((day) => {
        if (day.id === id) {
          return {
            ...day,
            [type]: value,
          }
        }
        return day
      })
    )
  }

  const generateSchedule = () => {
    const schedule = {
      mondayStart: '',
      mondayEnd: '',
      tuesdayStart: '',
      tuesdayEnd: '',
      wednesdayStart: '',
      wednesdayEnd: '',
      thursdayStart: '',
      thursdayEnd: '',
      fridayStart: '',
      fridayEnd: '',
      saturdayStart: '',
      saturdayEnd: '',
      sundayStart: '',
      sundayEnd: '',
    }

    selectedDays.forEach((day) => {
      switch (day.id) {
        case 'monday':
          schedule.mondayStart = day.start
          schedule.mondayEnd = day.end
          break
        case 'tuesday':
          schedule.tuesdayStart = day.start
          schedule.tuesdayEnd = day.end
          break
        case 'wednesday':
          schedule.wednesdayStart = day.start
          schedule.wednesdayEnd = day.end
          break
        case 'thursday':
          schedule.thursdayStart = day.start
          schedule.thursdayEnd = day.end
          break
        case 'friday':
          schedule.fridayStart = day.start
          schedule.fridayEnd = day.end
          break
        case 'saturday':
          schedule.saturdayStart = day.start
          schedule.saturdayEnd = day.end
          break
        case 'sunday':
          schedule.sundayStart = day.start
          schedule.sundayEnd = day.end
          break
        default:
          break
      }
    })

    return schedule
  }

  useEffect(() => {
    setSelectedDays(defaultBusinessDays || [])
  }, [defaultBusinessDays])

  useEffect(() => {
    setBusinessDays(generateSchedule())
  }, [selectedDays])

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
              <th className="w-2/5 text-xs">영업 시작 시간</th>
              <th className="w-2/5 text-xs">영업 끝 시간</th>
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
                        value={day.start}
                        onChange={(e) => handleTimeChange(e, day.id, 'start')}
                      />
                    </td>
                    <td className="p-2">
                      <input
                        type="time"
                        className="w-full outline-none py-2 rounded-lg border border-gray-dark text-center"
                        value={day.end}
                        onChange={(e) => handleTimeChange(e, day.id, 'end')}
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
