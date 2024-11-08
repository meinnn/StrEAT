import BackButtonWithImage from '@/components/BackButtonWithImage'
import MenuOptions from '@/components/MenuOptions'
import { Menu, OptionCategory } from '@/types/menu'

async function fetchOptionCategory(id: number): Promise<OptionCategory> {
  const response = await fetch(
    `https://j11a307.p.ssafy.io/api/products/option-categories/${id}`
  )

  if (!response.ok) {
    throw new Error('Failed to fetch option category')
  }

  const result = await response.json()
  return result.data
}

async function fetchMenuDetails(menuId: string): Promise<Menu> {
  const response = await fetch(
    `https://j11a307.p.ssafy.io/api/products/${menuId}`
  )

  if (!response.ok) {
    throw new Error('Failed to fetch menu details')
  }

  const result = await response.json()
  const menuData = result.data

  // 옵션 카테고리 데이터 fetch 및 Menu의 optionCategories에 추가
  const optionCategoryPromises = menuData.optionCategories.map((id: number) =>
    fetchOptionCategory(id)
  )
  const optionCategories: OptionCategory[] = await Promise.all(
    optionCategoryPromises
  )

  return {
    ...menuData,
    optionCategories, // 메뉴에 옵션 카테고리 배열을 추가
    image: menuData.photos[0] || '/images/default_img.jpg',
  }
}

export default async function MenuDetails({ menuId }: { menuId: string }) {
  const menu = await fetchMenuDetails(menuId)

  return (
    <div className="pb-20">
      <BackButtonWithImage src={menu.image} alt={menu.name} title={menu.name} />

      <div className="m-6">
        <h1 className="text-2xl font-bold">{menu.name}</h1>
        <p className="mt-1 leading-5 text-gray-dark whitespace-pre-line">
          {menu.description}
        </p>
      </div>

      <MenuOptions menuInfo={menu} />
    </div>
  )
}
