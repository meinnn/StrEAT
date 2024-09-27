'use client'

import Link from 'next/link'
import { usePathname } from 'next/navigation'
import { TbSmartHome } from 'react-icons/tb'
import { BiReceipt, BiStoreAlt } from 'react-icons/bi'
import { HiOutlineSpeakerphone } from 'react-icons/hi'

const LINKS = [
  { name: '내 점포 정보', href: '/store', icon: TbSmartHome },
  { name: '주문 접수', href: '/order-management', icon: BiReceipt },
  { name: '매장 관리', href: '/manage', icon: BiStoreAlt },
  { name: '공고', href: '/announcement', icon: HiOutlineSpeakerphone },
]

export default function OwnerNav() {
  const path = usePathname()

  const HIDDEN_PATHS = [
    '/owner/store/setting/announcement',
    '/owner/store/setting/business-hours',
    '/owner/store/setting/business-location',
    '/owner/store/review',
    '/owner/store/regist',
  ]

  const isHiddenPath = HIDDEN_PATHS.some((hiddenPath) =>
    path.startsWith(hiddenPath)
  )
  if (isHiddenPath) return null

  return (
    <nav className="z-50 bg-white h-tabbar fixed bottom-0 inset-x-0">
      <ul className="h-full flex justify-around items-center">
        {LINKS.map((link) => {
          return (
            <li key={link.name}>
              <Link
                href={`/owner${link.href}`}
                className={
                  path === `/owner${link.href}` ? 'text-primary-500' : ''
                }
              >
                <div className="flex flex-col gap-y-0.5 items-center">
                  <link.icon size="28" />
                  <p className="text-xs">{link.name}</p>
                </div>
              </Link>
            </li>
          )
        })}
      </ul>
    </nav>
  )
}
