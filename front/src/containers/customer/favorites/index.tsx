'use client'

import { useQuery } from '@tanstack/react-query'
import Image from 'next/image'
import AppBar from '@/components/AppBar'
import FavoriteItem from '@/containers/customer/favorites/FavoriteItem'

interface Favorite {
  alertOn: boolean
  averageScore: number
  status: string
  storeId: number
  storeName: string
  imageSrc: string
}

export default function Favorites() {
  const getFavoriteList = async () => {
    const response = await fetch(`/services/users/favorite/1`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    })

    if (!response.ok) {
      console.error('찜 리스트 조회에 실패했습니다')
    }
    return response.json()
  }

  const {
    data: favoriteListData = [],
    error: favoriteError,
    isLoading: favoriteLoading,
  } = useQuery<Favorite[], Error>({
    queryKey: ['/store/favorite'],
    queryFn: getFavoriteList,
  })

  console.log('favoriteListData:', favoriteListData)

  if (favoriteListData?.length === 0) {
    return (
      <div>
        <AppBar title="찜목록" />
        <div className="flex justify-center items-center h-80 flex-col gap-1">
          <Image
            width={80}
            height={80}
            src="/images/no_content_illustration.png"
            className="object-cover"
            alt="내용이 없다는 일러스트"
            priority
          />
          <p className="text-text font-bold">찜목록이 없습니다</p>
        </div>
      </div>
    )
  }

  if (favoriteLoading) {
    return <p>로딩중</p>
  }

  if (favoriteError) {
    return <p>에러 발생</p>
  }

  return (
    <section>
      <AppBar title="찜목록" />
      <main className="pb-32">
        {favoriteListData &&
          favoriteListData?.length > 0 &&
          favoriteListData?.map((favorite) => (
            <FavoriteItem
              key={favorite?.storeId}
              storeId={favorite?.storeId}
              status={favorite?.status}
              imageSrc={favorite?.imageSrc || '/images/보쌈사진.jpg'}
              title={favorite?.storeName}
              score={favorite?.averageScore}
              notification={favorite?.alertOn}
            />
          ))}
      </main>
    </section>
  )
}
