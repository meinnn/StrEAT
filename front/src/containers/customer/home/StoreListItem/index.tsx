import { FaLocationArrow, FaStar } from 'react-icons/fa'
import Image from 'next/image'
import Link from 'next/link'
import { Store } from '@/types/store'
import StoreLikeButton from '@/components/StoreLikeButton'

export default function StoreListItem({ store }: { store: Store }) {
  return (
    <div className="flex items-center justify-between p-4 h-full w-full">
      <Link
        href={`/customer/stores/${store.id}`}
        className="flex items-center overflow-hidden"
      >
        {/* 음식점 이미지 */}
        <Image
          src={store.src || '/images/default_img.jpg'}
          alt={store.storeName}
          width={64}
          height={64}
          className="object-cover w-16 h-16 rounded-full mr-4"
        />

        {/* 음식점 정보 */}
        <div className="truncate">
          {store.categories.map((category) => (
            <span key={category} className="text-xs text-gray-dark me-1">
              #{category}
            </span>
          ))}
          <h3 className="text-lg font-semibold">{store.storeName}</h3>
          <div className="flex items-center text-xs mt-1">
            <FaStar className="text-yellow-400 mr-1" />
            <span>4.8</span>
            <span className="mx-2">|</span>
            <FaLocationArrow className="mr-1" size={12} />
            <span>{store.distance}m</span>
          </div>
        </div>
      </Link>

      <div className="ml-4 mb-auto">
        <StoreLikeButton storeId={store.id} />
      </div>
    </div>
  )
}
