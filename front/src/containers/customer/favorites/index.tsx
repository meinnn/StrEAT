import AppBar from '@/components/AppBar'
import FavoriteItem from '@/containers/customer/favorites/FavoriteItem'

export const FAVORITE_LIST = [
  {
    id: 1,
    imageUrl: '/images/보쌈사진.jpg',
    title: '맛있는 족발/보쌈',
    score: 4.5,
    favorite: true,
    notification: true,
  },
  {
    id: 2,
    imageUrl: '/images/보쌈사진.jpg',
    title: '맛있는 족발/보쌈',
    score: 4,
    favorite: true,
    notification: false,
  },
  {
    id: 3,
    imageUrl: '/images/보쌈사진.jpg',
    title: '맛있는 족발/보쌈',
    score: 4.5,
    favorite: false,
    notification: false,
  },
  {
    id: 4,
    imageUrl: '/images/보쌈사진.jpg',
    title: '맛있는 족발/보쌈',
    score: 3,
    favorite: false,
    notification: false,
  },
  {
    id: 5,
    imageUrl: '/images/보쌈사진.jpg',
    title: '맛있는 족발/보쌈',
    score: 2.5,
    favorite: false,
    notification: false,
  },
]

export default function Favorites() {
  return (
    <section>
      <AppBar title="찜목록" />
      <main className="pb-32">
        {FAVORITE_LIST.map((favorite) => (
          <FavoriteItem
            key={favorite.id}
            id={favorite.id}
            imageUrl={favorite.imageUrl}
            title={favorite.title}
            score={favorite.score}
            favorite={favorite.favorite}
            notification={favorite.notification}
          />
        ))}
      </main>
    </section>
  )
}
