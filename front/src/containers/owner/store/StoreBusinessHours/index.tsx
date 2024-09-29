import Link from 'next/link'
import { IoSettingsOutline } from 'react-icons/io5'

const DAY_LIST = ['월', '화', '수', '목', '금', '토', '일']
const BUSINESS_TIME_LIST = [
  {
    day: '화',
    startTime: '11:00',
    endTime: '22:00',
  },
  {
    day: '수',
    startTime: '11:00',
    endTime: '22:00',
  },
  {
    day: '목',
    startTime: '11:00',
    endTime: '22:00',
  },
  {
    day: '금',
    startTime: '11:00',
    endTime: '22:00',
  },
  {
    day: '토',
    startTime: '11:00',
    endTime: '22:00',
  },
  {
    day: '일',
    startTime: '11:00',
    endTime: '22:00',
  },
]

export default function StoreBusinessHours() {
  return (
    <section className="mt-11 px-6 flex flex-col gap-6">
      <div className="flex items-center gap-1">
        <h3 className="text-xl font-medium">영업일 및 영업시간</h3>
        <Link href="/owner/store/setting/business-hours">
          <IoSettingsOutline className="cursor-pointer" />
        </Link>
      </div>

      <div className="flex flex-col gap-4">
        <div className="flex gap-12 text-sm">
          <h6 className="w-16">운영 시간</h6>
          <div className="flex flex-col gap-1">
            {DAY_LIST.map((day) => {
              const businessDay = BUSINESS_TIME_LIST.filter(
                (time) => day === time.day
              )
              return (
                <p key={day} className="flex gap-2">
                  <span>{day}</span>
                  {businessDay.length
                    ? `${businessDay[0].startTime} - ${businessDay[0].endTime}`
                    : '정기 휴무'}
                </p>
              )
            })}
          </div>
        </div>
        <div className="flex gap-12 text-sm">
          <h6 className="w-16">휴무일</h6>
          <div className="flex flex-col gap-1">
            <p>매주 월요일</p>
            <p>09/16-09/18 추석 연휴 휴무</p>
          </div>
        </div>
      </div>
    </section>
  )
}
