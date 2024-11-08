import Checkbox from '@/components/Checkbox'
import { IoCloseOutline } from 'react-icons/io5'
import Image from 'next/image'
import {
  CartMenu,
  CartOptionCategory,
  CartOptionCategoryItem,
} from '@/types/menu'
import { useEffect, useState } from 'react'
import Drawer from '@/components/Drawer'
import MenuOptions from '@/components/MenuOptions'

interface CartItemProps {
  item: CartMenu
  onCheck: (id: number) => void
  onRemove: (id: number) => void
}

interface OptionResponse {
  optionId: number
  optionName: string
  optionPrice: number
  isSelected: boolean
  optionCategoryId: number
  optionCategoryName: string
  isEssentialCategory: boolean
  minSelectCategory: number
  maxSelectCategory: number
}

interface OptionCategoryResponse {
  [key: string]: OptionResponse[]
}

interface CartDetailResponse {
  basketId: number
  productName: string
  productPrice: number
  stockStatus: boolean
  getBasketOptionDetailMap: OptionCategoryResponse
}

async function fetchCartDetails(cartId: number) {
  const response = await fetch(`/services/cart/${cartId}`)
  if (!response.ok) {
    throw new Error('Failed to fetch menu details')
  }

  const result: CartDetailResponse = await response.json()

  // optionCategory 추출
  const updatedOptionCategories: CartOptionCategory[] = Object.keys(
    result.getBasketOptionDetailMap
  ).map((categoryId) => {
    const options: CartOptionCategoryItem[] = result.getBasketOptionDetailMap[
      categoryId
    ].map((option) => ({
      id: option.optionId,
      productOptionName: option.optionName,
      productOptionPrice: option.optionPrice,
      isSelected: option.isSelected,
    }))

    // 같은 카테고리의 첫 번째 옵션을 기준으로 카테고리 정보 추출
    const firstOption = result.getBasketOptionDetailMap[categoryId][0]

    return {
      id: firstOption.optionCategoryId,
      name: firstOption.optionCategoryName,
      isEssential: firstOption.isEssentialCategory,
      minSelect: firstOption.minSelectCategory,
      maxSelect: firstOption.maxSelectCategory,
      options,
    }
  })

  return {
    price: result.productPrice, // 원래 상품 가격으로 표시
    optionCategories: updatedOptionCategories,
  }
}

export default function CartItem({ item, onCheck, onRemove }: CartItemProps) {
  const [showDrawer, setShowDrawer] = useState(false)
  const [menuDetails, setMenuDetails] = useState<CartMenu>(item)

  // 컴포넌트 렌더링 시점에 옵션 카테고리 미리 불러오기
  useEffect(() => {
    const loadOptionCategories = async () => {
      try {
        const updatedMenuDetails = await fetchCartDetails(item.cartId)
        setMenuDetails((prevDetails) => ({
          ...prevDetails,
          ...updatedMenuDetails,
        }))
      } catch (error) {
        console.error('Failed to fetch option categories', error)
      }
    }

    loadOptionCategories().then()
  }, [item.cartId])

  const handleOptionChangeButtonClick = () => {
    setShowDrawer(true)
  }

  return (
    <div className="border border-gray-medium rounded-lg p-4 mb-2 flex flex-col">
      <div className="flex justify-between">
        <Checkbox
          onChange={() => onCheck(item.cartId)}
          checked={item.checked}
          id={`cart-${item.cartId}`}
          size={24}
        />
        <button
          type="button"
          onClick={() => onRemove(item.cartId)}
          className="hover:text-primary-500"
        >
          <IoCloseOutline size={20} />
        </button>
      </div>

      <div className="ms-10 mt-2">
        <div className="flex items-start space-x-4">
          <Image
            src={item.image}
            alt={item.name}
            width={80}
            height={80}
            className="rounded-lg bg-gray-medium"
            priority
          />
          <div>
            <h3 className="text-md font-semibold">{item.name}</h3>
            <p className="text-sm">{item.quantity}개</p>
            {item.optionNameList.map((option) => (
              <p className="text-sm text-gray-dark leading-4" key={option}>
                {option}
              </p>
            ))}
          </div>
        </div>
      </div>

      <div className="ms-10 mt-4 flex justify-between items-center">
        <button
          type="button"
          className="p-3 py-1 text-primary-500 border border-primary-300 rounded-full text-xs w-20"
          onClick={handleOptionChangeButtonClick}
        >
          옵션 변경
        </button>
        <p className="font-bold">{item.price.toLocaleString()}원</p>
      </div>

      {showDrawer && (
        <Drawer title={item.name} onClose={() => setShowDrawer(false)}>
          <div className="mb-20">
            <MenuOptions
              type="change"
              menuInfo={menuDetails}
              closeDrawer={() => setShowDrawer(false)}
            />
          </div>
        </Drawer>
      )}
    </div>
  )
}
