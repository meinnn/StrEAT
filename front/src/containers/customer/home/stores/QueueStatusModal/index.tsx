import ModalLayout from '@/components/ModalLayout'
import { IoCloseCircleOutline } from 'react-icons/io5'
import Link from 'next/link'

export default function QueueStatusModal({ storeId }: { storeId: string }) {
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
            현재 대기 <span className="font-bold text-xl">5팀</span>,
          </p>
          <p>
            <span className="font-bold text-xl text-primary-500">
              메뉴 10개
            </span>
            가 준비 중이에요
          </p>
        </div>
      </div>
    </ModalLayout>
  )
}
