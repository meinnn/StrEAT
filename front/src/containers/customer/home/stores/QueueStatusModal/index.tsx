import ModalLayout from '@/components/ModalLayout'
import { IoCloseCircleOutline } from 'react-icons/io5'
import Link from 'next/link'

interface WaitingList {
  waitingTeam: number
  waitingMenu: number
}

async function fetchWaitingList(storeId: string): Promise<WaitingList> {
  const response = await fetch(
    `https://j11a307.p.ssafy.io/api/orders/order-request/${storeId}/list/waiting`,
    {
      method: 'GET',
      cache: 'no-store', // 항상 새 정보
    }
  )

  if (!response.ok) {
    throw new Error('Failed to fetch waiting list')
  }

  const result = await response.json()
  return result.data
}

export default async function QueueStatusModal({
  storeId,
}: {
  storeId: string
}) {
  const waitingList = await fetchWaitingList(storeId)

  return (
    <ModalLayout>
      <div className="relative">
        <Link
          href={`/customer/stores/${storeId}`}
          className="absolute top-0 right-0"
        >
          <IoCloseCircleOutline size={24} className="text-white" />
        </Link>
        <div className="bg-white p-6 rounded-full aspect-square flex flex-col items-center justify-center">
          <p>
            현재 대기{' '}
            <span className="font-bold text-xl">
              {waitingList.waitingTeam}팀
            </span>
            ,
          </p>
          <p>
            <span className="font-bold text-xl text-primary-500">
              메뉴 {waitingList.waitingMenu}개
            </span>
            가 준비 중이에요
          </p>
        </div>
      </div>
    </ModalLayout>
  )
}
