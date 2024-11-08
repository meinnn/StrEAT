import Link from 'next/link'

export default function OrderInfoBtn({ orderId }: { orderId: number }) {
  return (
    <div>
      <Link href={`/owner/order-management/${orderId}`}>
        <button
          type="button"
          className="whitespace-nowrap flex justify-center items-center bg-white h-20 w-14 text-sm font-bold text-black px-3 py-2 rounded-md shadow-md"
        >
          주문 정보
          <br />
          보기
        </button>
      </Link>
    </div>
  )
}
