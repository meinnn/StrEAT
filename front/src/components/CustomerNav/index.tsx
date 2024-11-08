'use client'

import Link from 'next/link'
import { usePathname } from 'next/navigation'
import { RiHeart3Line, RiSearchLine } from 'react-icons/ri'
import { TbReceipt, TbSmartHome } from 'react-icons/tb'
import { HiOutlineUser } from 'react-icons/hi'

const LINKS = [
  { name: '홈', href: '', icon: TbSmartHome },
  { name: '위치 검색', href: '/search', icon: RiSearchLine },
  { name: '주문 내역', href: '/orders', icon: TbReceipt },
  { name: '찜 목록', href: '/favorites', icon: RiHeart3Line },
  { name: '마이 페이지', href: '/mypage', icon: HiOutlineUser },
]

const HIDDEN_PATHS = [
  '/customer/stores',
  '/customer/cart',
  '/customer/payment',
  '/customer/mypage/reviews',
  '/customer/orders/\\d+/review/new',
]

export default function CustomerNav() {
  const path = usePathname()

  const isHiddenPath = HIDDEN_PATHS.some((hiddenPath) =>
    new RegExp(`^${hiddenPath}`).test(path)
  )
  if (isHiddenPath) return null

  return (
    <nav className="bg-white h-tabbar fixed bottom-0 inset-x-0">
      <ul className="h-full flex justify-around items-center">
        {LINKS.map((link) => {
          return (
            <li key={link.name}>
              <Link
                href={`/customer${link.href}`}
                className={
                  path === `/customer${link.href}` ||
                  (link.href !== '' && path.startsWith(`/customer${link.href}`))
                    ? 'text-primary-500'
                    : ''
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
