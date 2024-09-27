import Image from 'next/image'
import { FaStar } from 'react-icons/fa'
import { TbReceipt } from 'react-icons/tb'

export default function ReviewItem({
  user: { profileUrl, nickname },
  createdAt,
  score,
  reviewImageList,
  content,
  orderList,
}: {
  user: {
    profileUrl: string
    nickname: string
  }
  createdAt: string
  score: number
  reviewImageList: string[]
  content: string
  orderList: string[]
}) {
  return (
    <section className="border-b border-gray-medium px-6 py-3 last:border-b-0">
      <div className="flex flex-col">
        <div className="flex justify-between mb-2">
          <div className="flex gap-1 items-center">
            <p className="relative w-6 aspect-square rounded-full overflow-hidden">
              <Image
                src={profileUrl}
                alt="사용자 프로필"
                className="object-cover"
                fill
              />
            </p>
            <p className="text-xs text-text">{nickname}</p>
          </div>
          <p className="text-xs">{createdAt}</p>
        </div>
        <div className="flex mb-2 ml-1">
          {[1, 2, 3, 4, 5].map((star) => (
            <FaStar
              key={star}
              className={`cursor-pointer h-5 w-5 ${score >= star ? 'text-[#FBC400]' : 'text-gray-medium'}`}
            />
          ))}
        </div>
        <p className="relative w-full aspect-video rounded-lg overflow-hidden mb-2">
          <Image
            src={reviewImageList[0]}
            alt="음식 리뷰 사진"
            className="object-cover"
            fill
            priority
          />
        </p>
        <div className="mb-4">
          <p className="font-normal text-text">{content}</p>
        </div>
        <div className="flex gap-1 items-start">
          <TbReceipt className="w-6 h-6 flex-shrink-0 mt-1 text-primary-500" />
          <div className="flex flex-wrap gap-2">
            {orderList.map((order) => {
              return (
                <span
                  key={order}
                  className="rounded-full text-sm border py-1 px-3 border-primary-500 text-primary-500"
                >
                  {order}
                </span>
              )
            })}
          </div>
        </div>
      </div>
    </section>
  )
}
