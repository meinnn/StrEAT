'use client'

import Image from 'next/image'
import Link from 'next/link'
import { useRouter } from 'next/navigation'
import { GoChevronRight } from 'react-icons/go'

const PROCESS_ID = {
  REJECTED: 0,
  WAITING_FOR_PROCESSING: 1,
  PROCESSING: 2,
  WAITING_FOR_RECEIPT: 3,
  RECEIVED: 4,
}

const PROCESS = [
  {
    id: 'REJECTED',
    orderNum: '0',
    label: '거절',
  },
  {
    id: 'WAITING_FOR_PROCESSING',
    orderNum: '1',
    label: '주문 요청',
  },
  {
    id: 'PROCESSING',
    orderNum: '2',
    label: '주문 확인',
  },
  {
    id: 'WAITING_FOR_RECEIPT',
    orderNum: '3',
    label: '픽업 대기',
  },
  {
    id: 'RECEIVED',
    orderNum: '4',
    label: '픽업 완료',
  },
]

type ProcessIdKeys = keyof typeof PROCESS_ID

export default function OrderItem({
  id,
  date,
  storeImageUrl,
  storeName,
  storeId,
  orderList,
  orderStatus,
  isReviewed,
}: {
  id: number
  date: string
  storeImageUrl: string
  storeName: string
  storeId: number
  orderList: { productName: string; orderProductCount: number }[]
  orderStatus: ProcessIdKeys
  isReviewed: boolean
}) {
  const router = useRouter()

  return (
    <section className="pt-3 pb-2 flex flex-col relative shadow-md bg-white rounded-lg overflow-hidden">
      {PROCESS_ID[orderStatus] === 0 ? (
        <div className="absolute top-0 left-0 rounded-lg flex flex-col justify-center items-center w-full bg-[rgba(255,236,199,0.8)] h-full z-10">
          <Image
            src="/images/no_content_illustration.png"
            className="object-cover"
            alt="내용이 없다는 일러스트"
            width={60}
            height={60}
          />
          <p className="text-text">주문 거절</p>
          <Link href={`/customer/orders/${id}`}>
            <button
              type="button"
              className="absolute top-3 right-6 text-xs py-0 px-2 h-6 rounded-full bg-text text-secondary-light"
            >
              주문 상세
            </button>
          </Link>
        </div>
      ) : null}
      <div className="px-6">
        <div className="flex gap-2 items-center pb-3 justify-between">
          <p className="text-xs text-text">{date}</p>
          {PROCESS_ID[orderStatus] !== 0 ? (
            <Link href={`/customer/orders/${id}`}>
              <button
                type="button"
                className="text-xs border border-primary-500 py-0 px-2 h-6 rounded-full text-primary-500"
              >
                주문 상세
              </button>
            </Link>
          ) : null}
        </div>
        <div className="flex justify-between w-full items-center pb-4">
          <div className="flex items-center gap-3">
            <p className="relative flex-shrink-0 w-16 h-16 aspect-square rounded-lg overflow-hidden bg-gray-medium">
              <Image
                src={storeImageUrl || '/images/보쌈사진.jpg'}
                alt="가게 사진"
                fill
                className="object-cover"
                priority
              />
            </p>
            <div className="flex flex-col w-full">
              <div className="flex flex-col justify-center items-start gap-[2px]">
                <Link
                  href={`/customer/stores/${storeId}`}
                  className="flex items-center gap-2 text-text"
                >
                  <h3 className="font-normal">{storeName}</h3>
                  <GoChevronRight className="w-4 h-4 flex-shrink-0" />
                </Link>
                <div className="flex flex-col">
                  {orderList.slice(0, 2).map((order) => {
                    return (
                      <p
                        key={order.productName}
                        className="text-gray-dark text-[0.625rem] font-normal"
                      >
                        {order.productName}&nbsp;{order.orderProductCount}개
                      </p>
                    )
                  })}
                  {orderList.length > 2 ? (
                    <p className="text-gray-dark text-[0.625rem] font-normal">
                      외 {orderList.length - 2}개
                    </p>
                  ) : null}
                </div>
              </div>
            </div>
          </div>
          <div className="flex flex-col gap-2 items-center">
            {PROCESS_ID[orderStatus] && PROCESS_ID[orderStatus] !== 0 ? (
              <>
                <span className="rounded-full w-8 h-8 font-normal bg-primary-500 flex justify-center items-center text-secondary-light">
                  {PROCESS_ID[orderStatus]}
                </span>
                <p className="text-xs font-semibold text-primary-500">
                  {PROCESS[PROCESS_ID[orderStatus]].label}
                </p>
              </>
            ) : null}
          </div>
        </div>
      </div>
      {orderStatus === 'RECEIVED' ? (
        <div className="w-full px-2">
          <button
            onClick={() => router.push('/customer/orders/1/review/new')}
            type="button"
            className={`${isReviewed ? ' bg-gray-medium text-text opacity-50' : ' bg-primary-500 text-secondary-light'} font-normal py-2 w-full rounded-lg`}
          >
            {isReviewed ? '리뷰 작성완료' : '리뷰 작성하기'}
          </button>
        </div>
      ) : null}
    </section>
  )
}
