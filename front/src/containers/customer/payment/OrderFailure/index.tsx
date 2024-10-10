import { FaExclamation } from 'react-icons/fa6'
import Link from 'next/link'

export default function OrderFailure() {
  return (
    <div className="h-screen z-50 flex flex-col items-center justify-center">
      <div className="size-48 flex items-center justify-center rounded-full bg-primary-500">
        <FaExclamation className="text-white" size={112} />
      </div>
      <p className="mt-8 text-3xl font-bold">주문을 완료하지 못했어요</p>
      <p className="mt-4 text-gray-dark">[toss] 결제 처리 실패</p>

      <Link
        href="/customer"
        className="fixed bottom-0 inset-x-0 flex justify-center m-3 py-4 rounded-lg font-bold bg-primary-500 text-white"
      >
        홈으로 돌아가기
      </Link>
    </div>
  )
}
