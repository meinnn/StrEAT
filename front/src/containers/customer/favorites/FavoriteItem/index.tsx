import Image from 'next/image'
import Link from 'next/link'
import { FaStar } from 'react-icons/fa'
import { GoChevronRight } from 'react-icons/go'
import {
  IoNotificationsOutline,
  IoNotificationsOffOutline,
} from 'react-icons/io5'
import { RiHeart3Fill, RiHeart3Line } from 'react-icons/ri'

export default function FavoriteItem({
  id,
  imageUrl,
  title,
  score,
  favorite,
  notification,
}: {
  id: number
  imageUrl: string
  title: string
  score: number
  favorite: boolean
  notification: boolean
}) {
  return (
    <section className="flex py-6 px-8 gap-3">
      <Link href={`/customer/stores/${id}`}>
        <p className="relative flex-shrink-0 w-16 h-16 aspect-square rounded overflow-hidden bg-gray-medium">
          <Image src={imageUrl} alt="가게 사진" fill className="object-cover" />
        </p>
      </Link>
      <div className="flex flex-col w-full gap-1 pt-1">
        <div className="flex items-center justify-between w-full">
          <Link href={`/customer/stores/${id}`}>
            <div className="flex items-center gap-2 text-text">
              <h3 className="font-normal">{title}</h3>
              <GoChevronRight className="w-4 h-4 flex-shrink-0" />
            </div>
          </Link>
          <div className="flex gap-4 text-text">
            {notification ? (
              <IoNotificationsOutline className="w-5 h-5 cursor-pointer" />
            ) : (
              <IoNotificationsOffOutline className="w-5 h-5 cursor-pointer" />
            )}
            {favorite ? (
              <RiHeart3Fill className="w-5 h-5 text-primary-500 cursor-pointer" />
            ) : (
              <RiHeart3Line className="w-5 h-5 cursor-pointer  text-primary-500" />
            )}
          </div>
        </div>
        <div className="flex items-center gap-[2px]">
          <FaStar className="w-4 h-4 text-yellow-400" />
          <span className="inline-block text-sm">{score}</span>
        </div>
      </div>
    </section>
  )
}
