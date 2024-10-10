/* eslint-disable react-hooks/rules-of-hooks */
// eslint-disable-next-line react-hooks/rules-of-hooks

'use client'

import { useEffect, useState } from 'react'
import { useQueryClient } from '@tanstack/react-query'
import { useRouter } from 'next/navigation'
import AppBar from '@/components/AppBar'
import StoreBusinessSchedulePicker from '@/components/StoreBusinessSchedulePicker'
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

interface storeImage {
  createdAt: string
  id: number
  src: string
  storeId: number
}

interface StoreInfo {
  address: string
  bankAccount: string
  bankName: string
  businessRegistrationNumber: string
  closedDays: string
  id: number
  industryCategroyId: number
  latitude: number
  longitude: number
  name: string
  ownerWord: string
  status: string
  storePhoneNumber: string
  type: string
  useId: number
}

interface StoreBusinessDays {
  id: number
  storeId: number
  fridayEnd: string
  fridayStart: string
  mondayEnd: string
  mondayStart: string
  saturdayEnd: string
  saturdayStart: string
  sundayEnd: string
  sundayStart: string
  thursdayEnd: string
  thursdayStart: string
  tuesdayEnd: string
  tuesdayStart: string
  wednesdayEnd: string
  wednesdayStart: string
}

export default function OwnerStoreSettingBusinessHours() {
  const router = useRouter()
  const queryClient = useQueryClient()
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
  const [defaultBusinessDays, setDefaultBusinessDays] = useState<any>(null)
  const [businessDays, setBusinessDays] = useState<any>(null)

  const updateStoreBusinessHours = async () => {
    if (!ownerInfo?.storeId) return
    const response = await fetch(
      // 점포 영업일 및 영업 시간 변경하는 API
      `/services/store/${ownerInfo?.storeId}/business-hours`,
      {
        method: 'PUT',
        body: JSON.stringify({
          ...businessDays,
          id: storeInfo?.storeBusinessDays?.id,
        }),
      }
    )
    if (!response.ok) {
      console.error('영업일 및 영업 시간 변경에 실패했습니다.')
      return
    }
    queryClient.invalidateQueries({ queryKey: ['/store', ownerInfo?.storeId] })
    router.back()
  }

  const mergeTimeData = () => {
    return DAYS.map((day) => {
      const dayKeyStart = `${day.id}Start` as keyof StoreBusinessDays
      const dayKeyEnd = `${day.id}End` as keyof StoreBusinessDays

      const dayStart = storeInfo?.storeBusinessDays[dayKeyStart] as string
      const dayEnd = storeInfo?.storeBusinessDays[dayKeyEnd] as string

      if (dayStart.length === 0 && dayEnd.length === 0) return null
      return {
        ...day,
        start: dayStart,
        end: dayEnd,
      }
    }).filter((day) => day !== null)
  }

  useEffect(() => {
    if (!storeInfo || !storeInfo?.storeBusinessDays) return
    setDefaultBusinessDays(mergeTimeData())
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [storeInfo])

  if (ownerInfoLoading || isLoading) return <p>로딩중</p>
  if (ownerInfoError || error) return <p>에러 발생</p>

  return (
    <div>
      <AppBar title="점포 설정" />
      <main className=" mt-7 flex flex-col gap-7">
        <div className="px-7">
          <StoreBusinessSchedulePicker
            setBusinessDays={setBusinessDays}
            defaultBusinessDays={defaultBusinessDays}
          />
          <section className="flex flex-col gap-2 mt-5">
            <h3 className="text-xl font-medium">휴무일</h3>
            <textarea
              value={storeInfo?.storeInfo.closedDays}
              className="px-3 py-2 h-20 resize-none border rounded-lg border-gray-dark "
            />
          </section>
        </div>
        <div className="p-4 fixed bottom-0 w-full bg-white">
          <button
            onClick={updateStoreBusinessHours}
            className="py-4 w-full bg-primary-500 text-secondary-light rounded-lg"
          >
            수정하기
          </button>
        </div>
      </main>
    </div>
  )
}
