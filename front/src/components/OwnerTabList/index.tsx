'use client'

import Link from 'next/link'
import { usePathname } from 'next/navigation'

interface OwnerTab {
  name: string
  href: string
}

export default function OwnerTabList({ tabList }: { tabList: OwnerTab[] }) {
  const path = usePathname()

  return (
    <ul className="flex sticky top-0 bg-white z-50 border-b border-gray-medium">
      {tabList.map((tab) => {
        return (
          <li
            key={tab.name}
            className={`${path === `/owner${tab.href}` ? 'bg-primary-400' : 'bg-gray-medium'} flex justify-center  px-6 pt-3 pb-2 min-w-28 text-secondary-light rounded-t-[10px] `}
          >
            <Link
              href={`/owner${tab.href}`}
              className={
                path === `/owner${tab.href}`
                  ? ' text-secondary-light'
                  : 'text-[#6D6D6D]'
              }
            >
              <p className="font-bold">{tab.name}</p>
            </Link>
          </li>
        )
      })}
    </ul>
  )
}
