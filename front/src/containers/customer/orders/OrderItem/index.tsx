import Image from 'next/image'
import Link from 'next/link'
import { GoChevronRight } from 'react-icons/go'

export default function OrderItem({
  id,
  date,
  imageUrl,
  title,
  orderList,
  progress,
  review,
}: {
  id: number
  date: string
  imageUrl: string
  title: string
  orderList: string[]
  progress: number
  review: boolean
}) {
  return (
    <section className="pt-3 pb-2 flex flex-col  shadow-md bg-white rounded-lg overflow-hidden">
      <div className="px-6">
        <div className="flex gap-2 items-center pb-3 justify-between">
          <p className="text-xs  text-text">{date}</p>
          <Link href={`/customer/orders/${id}`}>
            <button className="text-xs border border-primary-500 py-0 px-2 h-6 rounded-full text-primary-500">
              주문 상세
            </button>
          </Link>
        </div>
        <div className="flex justify-between w-full items-center pb-4">
          <div className="flex items-center gap-3">
            <p className="relative flex-shrink-0 w-16 h-16 aspect-square rounded-lg overflow-hidden bg-gray-medium">
              <Image
                src={imageUrl}
                alt="가게 사진"
                fill
                className="object-cover"
                priority
              />
            </p>
            <div className="flex flex-col w-full">
              <div className="flex flex-col justify-center items-start gap-[2px]">
                <Link
                  href={`/customer/stores/${id}`}
                  className="flex items-center gap-2 text-text"
                >
                  <h3 className="font-normal">{title}</h3>
                  <GoChevronRight className="w-4 h-4 flex-shrink-0" />
                </Link>
                <div className="flex flex-col">
                  {orderList.slice(0, 2).map((order) => {
                    return (
                      <p
                        key={order}
                        className="text-gray-dark text-[0.625rem] font-normal"
                      >
                        {order}
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
          {progress !== 4 ? (
            <div className="flex flex-col gap-2 items-center">
              <span className="rounded-full w-8 h-8 font-normal bg-primary-500 flex justify-center items-center text-secondary-light">
                {progress}
              </span>
              <p className="text-xs font-semibold text-primary-500">
                주문 확인
              </p>
            </div>
          ) : null}
        </div>
      </div>
      {progress === 4 ? (
        <div className="w-full px-2">
          <button
            className={`${review ? ' bg-gray-medium text-text opacity-50' : ' bg-primary-500 text-secondary-light'} font-normal py-2 w-full rounded-lg`}
          >
            {review ? '리뷰 작성완료' : '리뷰 작성하기'}
          </button>
        </div>
      ) : null}
    </section>
  )
}
