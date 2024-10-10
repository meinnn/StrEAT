import Link from 'next/link'
import { IoSettingsOutline } from 'react-icons/io5'

interface BusinessHours {
  day: string
  startTime: string
  endTime: string
}

export default function StoreBusinessHours({
  businessHours,
  closedDays,
}: {
  businessHours: BusinessHours[]
  closedDays: string
}) {
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
            {businessHours && businessHours.length > 1 ? (
              businessHours.map((day) => {
                return (
                  <p key={day.day} className="flex gap-2">
                    <span>{day.day}</span>
                    {`${day.startTime} - ${day.endTime}`}
                  </p>
                )
              })
            ) : (
              <p>휴무일이 없습니다.</p>
            )}
          </div>
        </div>
        <div className="flex gap-12 text-sm">
          <h6 className="w-16">휴무일</h6>
          <div className="flex flex-col gap-1">{closedDays}</div>
        </div>
      </div>
    </section>
  )
}
