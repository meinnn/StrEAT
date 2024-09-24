import Image from 'next/image'
import { IoSettingsOutline } from 'react-icons/io5'
import { IoIosMore } from 'react-icons/io'

const BUSINESS_LOCATION_LIST = [
  {
    imageUrl: '/images/보쌈사진.jpg',
  },
  {
    imageUrl: '/images/보쌈사진.jpg',
  },
  {
    imageUrl: '/images/보쌈사진.jpg',
  },
  {
    imageUrl: '/images/보쌈사진.jpg',
  },
  {
    imageUrl: '/images/보쌈사진.jpg',
  },
  {
    imageUrl: '/images/보쌈사진.jpg',
  },
]

export default function StoreBusinessLocation() {
  const isMore = BUSINESS_LOCATION_LIST.length > 5

  return (
    <section className="mt-11 px-6 flex flex-col gap-2">
      <div className="flex items-center gap-1">
        <h3 className="text-xl font-medium">영업 위치</h3>
        <IoSettingsOutline className="cursor-pointer" />
      </div>
      <div className="flex gap-3">
        {BUSINESS_LOCATION_LIST.slice(
          0,
          isMore ? 4 : BUSINESS_LOCATION_LIST.length
        ).map((location) => {
          return (
            <p className="relative w-16 h-16 aspect-square rounded overflow-hidden bg-gray-medium">
              <Image
                src={location.imageUrl}
                alt="location"
                fill
                className="object-cover"
              />
            </p>
          )
        })}
        {isMore ? (
          <div className="cursor-pointer flex flex-col justify-center items-center w-16 h-16 aspect-square rounded overflow-hidden bg-gray-medium">
            <IoIosMore className="w-6 h-6" />
            <p className="text-xs">더보기</p>
          </div>
        ) : null}
      </div>
    </section>
  )
}
