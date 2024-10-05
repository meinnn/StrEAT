import Image from 'next/image'
import Link from 'next/link'

interface MenuListItem {
  id: number // 메뉴 ID
  storeId: number // 가게 ID
  name: string // 메뉴 이름
  price: number // 가격
  description: string
  categories: number[] // 카테고리 배열
  optionCategories: number[] // 옵션 카테고리 배열
  photos: string[]
}

async function fetchMenuList(storeId: string): Promise<MenuListItem[]> {
  const response = await fetch(
    `https://j11a307.p.ssafy.io/api/products/store/${storeId}`
  )

  if (!response.ok) {
    throw new Error('Failed to fetch store details')
  }

  const result = await response.json()
  return result.data
}

export default async function MenuList({ storeId }: { storeId: string }) {
  const menuList = await fetchMenuList(storeId)

  return (
    <div className="m-6">
      <h2 className="text-lg font-bold mb-4">메뉴</h2>
      {menuList.map((item) => (
        <Link
          href={`/customer/stores/${storeId}/menu/${item.id}`}
          key={item.id}
          className="flex items-center mb-4 px-1"
        >
          <Image
            src={item.photos[0] || '/images/default_img.jpg'}
            alt={item.name}
            width={80}
            height={80}
            className="w-20 h-20 object-cover rounded-lg bg-gray-medium"
          />
          <div className="ml-4">
            <h3 className="font-semibold">{item.name}</h3>
            <p className="text-xs">{item.description}</p>
            <p className="text-sm font-semibold mt-2">
              {item.price.toLocaleString()}원
            </p>
          </div>
        </Link>
      ))}
    </div>
  )
}
