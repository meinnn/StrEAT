import Link from 'next/link'
import Image from 'next/image'
import { GoChevronRight } from 'react-icons/go'

export default function StoreLink({ storeId }: { storeId: number }) {
  return (
    <Link href={`/customer/stores/${storeId}`} className="ms-2 mb-5 flex">
      <Image
        src="/images/보쌈사진.jpg"
        alt="store"
        width={48}
        height={48}
        className="rounded-lg bg-gray-medium"
      />
      <div className="flex items-center">
        <span className="ml-4 text-lg font-bold">옐로우 키친 치킨</span>
        <GoChevronRight size={20} />
      </div>
    </Link>
  )
}
