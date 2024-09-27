import Image from 'next/image'
import { GoChevronRight } from 'react-icons/go'

export default function ReviewListItem({
  store,
  storeImage,
  date,
  order,
  content,
}: {
  store: string
  storeImage: string
  date: string
  order: string
  content: string
}) {
  return (
    <section className=" px-5 pb-5 border-b border-gray-medium bg-white">
      <div className="flex py-3 items-center gap-3">
        <p className="relative w-12 aspect-square rounded-md overflow-hidden border border-gray-medium bg-gray-light">
          <Image src={storeImage} alt="음식점" fill className="object-cover" />
        </p>
        <div className="flex flex-col gap-[2px] w-full">
          <div className="flex justify-between items-center w-full">
            <div className="flex items-center">
              <h5 className="text-sm font-normal">{store}</h5>
              <GoChevronRight className="text-text w-4 h-4" />
            </div>
            <p className="text-xs">{date}</p>
          </div>
          <p className="text-xs text-gray-dark">{order}</p>
        </div>
      </div>
      <div>
        <p className="font-normal">{content}</p>
      </div>
    </section>
  )
}
