import Checkbox from '@/components/Checkbox'
import { IoCloseOutline } from 'react-icons/io5'
import Image from 'next/image'
import { CartMenu, OptionCategory } from '@/types/menu'
import { useState } from 'react'
import Drawer from '@/components/Drawer'
import MenuOptions from '@/containers/customer/home/stores/menu/MenuOptions'
import OrderMenuDetail from '@/components/OrderMenuDetail'

interface CartItemProps {
  item: CartMenu
  onCheck: (id: number) => void
  onRemove: (id: number) => void
}

interface OptionResponseData {
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

interface CartDetailResponseData {
  basketId: number
  productName: string
  productPrice: number
  stockStatus: boolean
  getBasketOptionDetailDTOs: OptionResponseData[]
}

async function fetchCartDetails(cartId: number) {
  const response = await fetch(`/services/cart/${cartId}`)
  if (!response.ok) {
    throw new Error('Failed to fetch menu details')
  }

  const result: CartDetailResponseData = await response.json()

  // optionCategory 추출
  const updatedOptionCategories: OptionCategory[] =
    result.getBasketOptionDetailDTOs.map((option) => ({
      id: option.optionCategoryId,
      productId: cartId,
      name: option.optionCategoryName,
      isEssential: option.isEssentialCategory,
      minSelect: option.minSelectCategory,
      maxSelect: option.maxSelectCategory,
      options: [
        {
          id: option.optionId,
          productId: cartId,
          productOptionCategoryId: option.optionCategoryId,
          productOptionName: option.optionName,
          productOptionPrice: option.optionPrice,
        },
      ],
      selectedOption: option.optionName,
    }))

  return {
    ...result,
    price: result.productPrice,
    optionCategories: updatedOptionCategories,
  }
}

export default function CartItem({ item, onCheck, onRemove }: CartItemProps) {
  const [showDrawer, setShowDrawer] = useState(false)
  const [menuDetails, setMenuDetails] = useState<CartMenu>(item)

  const handleOptionChange = async () => {
    try {
      // fetchCartOptionCategories 호출해서 optionCategories 업데이트
      const updatedMenuDetails = await fetchCartDetails(item.cartId)
      console.log(updatedMenuDetails)
      setMenuDetails((prevDetails) => ({
        ...prevDetails,
        ...updatedMenuDetails,
      }))
      setShowDrawer(true)
    } catch (error) {
      console.error('Failed to fetch option categories', error)
    }
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
          onClick={handleOptionChange}
        >
          옵션 변경
        </button>
        <p className="font-bold">{item.price.toLocaleString()}원</p>
      </div>

      {showDrawer && (
        <Drawer title={item.name} onClose={() => setShowDrawer(false)}>
          <div className="mb-20">
            <MenuOptions type="change" menuInfo={menuDetails} />
          </div>
        </Drawer>
      )}
    </div>
  )
}
