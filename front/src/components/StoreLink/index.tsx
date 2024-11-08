import Link from 'next/link'
import Image from 'next/image'
import { GoChevronRight } from 'react-icons/go'

export default function StoreLink({
  storeId,
  storeName,
  storeSrc,
}: {
  storeId: number
  storeName: string
  storeSrc: string
}) {
  return (
    <Link href={`/customer/stores/${storeId}`} className="ms-2 mb-5 flex">
      <Image
        src={storeSrc}
        alt="store"
        width={48}
        height={48}
        className="rounded-lg object-cover aspect-square"
      />
      <div className="flex items-center">
        <span className="ml-4 text-lg font-bold">{storeName}</span>
        <GoChevronRight size={20} />
      </div>
    </Link>
  )
}
