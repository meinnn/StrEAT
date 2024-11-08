'use client'

import Link from 'next/link'
import { usePathname } from 'next/navigation'
import { TbSmartHome } from 'react-icons/tb'
import { BiReceipt, BiStoreAlt } from 'react-icons/bi'
import { FiGrid } from 'react-icons/fi'
import { LuFilePieChart } from 'react-icons/lu'
import { IconType } from 'react-icons'

const LINKS = [
  { name: '홈', href: '/', icon: TbSmartHome },
  { name: '내 점포 정보', href: '/store', icon: BiStoreAlt },
  { name: '주문 접수', href: '/order-management', icon: BiReceipt },
  { name: '매장 관리', href: '/store-management', icon: LuFilePieChart },
  { name: '전체', href: '/all', icon: FiGrid },
]

interface NavLink {
  name: string
  href: string
  icon: IconType
}

interface TabLinkProps {
  link: NavLink
  isActive: boolean
}

function TabLink({ link, isActive }: TabLinkProps) {
  const isOrderReception = link.name === '주문 접수'

  return (
    <li key={link.name}>
      <Link
        href={`/owner${link.href}`}
        className={`${isOrderReception ? 'w-7' : ''} flex flex-col gap-y-0.5 items-center ${isActive ? 'text-primary-500' : ''}`}
      >
        {isOrderReception ? (
          <div className="shadow-inner-strong text-secondary-light bg-primary-500 h-[4.5rem] w-[4.5rem] absolute bottom-2 rounded-full flex flex-col justify-center items-center">
            <link.icon size="28" />
            <p className="text-xs">{link.name}</p>
          </div>
        ) : (
          <>
            <link.icon size="28" />
            <p className="text-xs">{link.name}</p>
          </>
        )}
      </Link>
    </li>
  )
}

export default function OwnerNav() {
  const path = usePathname()

  const HIDDEN_PATHS = [
    '/owner/store/setting',
    '/owner/store/review',
    '/owner/store/regist',
    '/owner/store/setting/\\d+/',
    '/owner/store/menu',
    '/owner/account',
  ]

  const isHiddenPath = HIDDEN_PATHS.some((hiddenPath) =>
    path.startsWith(hiddenPath)
  )
  if (isHiddenPath) return null

  return (
    <nav className="z-50 bg-white h-tabbar fixed bottom-0 inset-x-0">
      <ul className="relative h-full flex justify-around items-center">
        {LINKS.map((link) => (
          <TabLink
            key={link.name}
            link={link}
            isActive={path === `/owner${link.href}`}
          />
        ))}
      </ul>
    </nav>
  )
}
