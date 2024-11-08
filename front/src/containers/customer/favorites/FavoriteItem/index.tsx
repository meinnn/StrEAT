import { useQueryClient } from '@tanstack/react-query'
import Image from 'next/image'
import Link from 'next/link'
import { FaStar } from 'react-icons/fa'
import { GoChevronRight } from 'react-icons/go'
import {
  IoNotificationsOutline,
  IoNotificationsOffOutline,
} from 'react-icons/io5'
import { RiHeart3Fill } from 'react-icons/ri'

export default function FavoriteItem({
  storeId,
  imageSrc = '/images/보쌈사진.jpg',
  status,
  title,
  score,
  notification,
}: {
  storeId: number
  imageSrc?: string
  status: string
  title: string
  score: number
  notification: boolean
}) {
  const queryClient = useQueryClient()
  const handleClickFavoriteBtn = async () => {
    const response = await fetch(`/services/users/favorite/${storeId}`, {
      method: 'DELETE',
    })

    if (!response.ok) {
      console.error('찜취소에 실패했습니다')
      return
    }

    queryClient.invalidateQueries({
      queryKey: ['/store/favorite'],
    })
  }

  // const handleClickAlarmBtn = () => {
  //   const response = await fetch(`/services/users/favorite/${}`)

  //   console.log(response)

  //   if (!response.ok) {
  //     console.error('조리 완료 처리에 실패했습니다')
  //     return
  //   }

  //   queryClient.invalidateQueries({
  //     queryKey: ['/order/list', ownerInfo?.storeId],
  //   })
  //   queryClient.invalidateQueries({
  //     queryKey: ['/order/list/complete', ownerInfo?.storeId],
  //   })
  // }

  return (
    <section className="flex py-6 px-8 gap-3">
      <Link href={`/customer/stores/${storeId}`}>
        <p className="relative flex-shrink-0 w-16 h-16 aspect-square rounded overflow-hidden bg-gray-medium">
          <Image
            src={imageSrc}
            alt="가게 사진"
            fill
            className="object-cover"
            priority
          />
        </p>
      </Link>
      <div className="flex flex-col w-full gap-1 pt-1">
        <div className="flex items-center justify-between w-full">
          <Link href={`/customer/stores/${storeId}`}>
            <div className="flex items-center gap-2 text-text">
              <h3 className="font-normal">{title}</h3>
              <span
                className={`${status === 'READY' ? 'bg-red-100 text-red-500' : 'bg-green-100 text-green-500'}  text-xs py-[2px] px-2 rounded-md`}
              >
                {status === 'READY' ? '영업전' : '영업중'}
              </span>
              <GoChevronRight className="w-4 h-4 flex-shrink-0" />
            </div>
          </Link>
          <div className="flex gap-4 text-text">
            {notification ? (
              <IoNotificationsOutline className="w-5 h-5 cursor-pointer" />
            ) : (
              <IoNotificationsOffOutline className="w-5 h-5 cursor-pointer" />
            )}
            <button onClick={handleClickFavoriteBtn}>
              <RiHeart3Fill className="w-5 h-5 text-primary-500 cursor-pointer" />
            </button>
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
