'use client'

import { useState } from 'react'
import { CartMenu, Menu, OptionCategoryItem } from '@/types/menu'
import { useRouter } from 'next/navigation'
import OptionSelector from '@/containers/customer/home/stores/menu/OptionSelector'
import QuantityControl from '@/containers/customer/home/stores/menu/QuantityControl'
import { useCart } from '@/contexts/CartContext'

interface MenuOptionsProps {
  type?: 'default' | 'change'
  menuInfo: Menu | CartMenu
  closeDrawer?: () => void
}

interface CartOperationBody {
  quantity: number
  productOptionIds?: number[]
  optionList?: number[]
}

async function handleCartOperation(
  method: 'POST' | 'PATCH',
  url: string,
  body: CartOperationBody
) {
  const response = await fetch(url, {
    method,
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(body),
  })

  if (!response.ok) {
    throw new Error(`Failed to ${method === 'POST' ? 'add to' : 'update'} cart`)
  }
}

export default function MenuOptions({
  type = 'default',
  menuInfo,
  closeDrawer,
}: MenuOptionsProps) {
  const { cartStore, reloadCartItems } = useCart()

  const initialQuantity = 'quantity' in menuInfo ? menuInfo.quantity : 1
  // 초기 선택된 옵션 설정
  const initialSelectedOptions = menuInfo.optionCategories.reduce(
    (acc, category) => {
      if (type === 'change') {
        // 'change' 타입일 때 isSelected가 true인 옵션만 초기값으로 설정
        const selectedOptionsInCategory = category.options.filter(
          (option) => 'isSelected' in option && option.isSelected
        )
        if (selectedOptionsInCategory.length > 0) {
          acc[category.id] = selectedOptionsInCategory
        }
      } else if (
        category.isEssential &&
        category.maxSelect === 1 &&
        category.options.length > 0
      ) {
        // 'default' 타입일 때, maxSelect가 1인 필수 옵션의 경우 첫 번째 옵션을 선택
        acc[category.id] = [category.options[0]]
      }
      return acc
    },
    {} as Record<number, OptionCategoryItem[]>
  )

  const [selectedOptions, setSelectedOptions] = useState<
    Record<number, OptionCategoryItem[]>
  >(initialSelectedOptions)
  const [quantity, setQuantity] = useState(initialQuantity)
  const [showAlert, setShowAlert] = useState(false)

  const router = useRouter()

  const handleOptionChange = (
    categoryId: number,
    option: OptionCategoryItem,
    maxSelect: number
  ) => {
    setSelectedOptions((prev) => {
      const currentSelections = prev[categoryId] || []

      if (maxSelect === 1) {
        return { ...prev, [categoryId]: [option] }
      }

      if (
        currentSelections.some(
          (selectedOption) => selectedOption.id === option.id
        )
      ) {
        return {
          ...prev,
          [categoryId]: currentSelections.filter(
            (selectedOption) => selectedOption.id !== option.id
          ),
        }
      }

      if (currentSelections.length < maxSelect) {
        return { ...prev, [categoryId]: [...currentSelections, option] }
      }

      return prev
    })
  }

  const areAllRequiredOptionsSelected = () => {
    return menuInfo.optionCategories
      .filter((category) => category.isEssential)
      .every((category) => {
        const selectedCount = selectedOptions[category.id]?.length || 0
        return selectedCount >= category.minSelect
      })
  }

  const handleButtonClick = async () => {
    if (!areAllRequiredOptionsSelected()) {
      setShowAlert(true)
      setTimeout(() => setShowAlert(false), 3000)
      return
    }

    const selectedOptionIds = Object.values(selectedOptions)
      .flat()
      .map((option) => option.id)

    try {
      if (type === 'default') {
        if (cartStore?.storeId && menuInfo.storeId !== cartStore?.storeId) {
          router.push(
            `/customer/stores/${menuInfo.storeId}/menu/${menuInfo.id}/alert`
          )
          return
        }
        // 장바구니 담기
        await handleCartOperation(
          'POST',
          `/services/cart/item/${menuInfo.id}`,
          {
            productOptionIds: selectedOptionIds,
            quantity,
          }
        )
        reloadCartItems()
        router.push(`/customer/stores/${menuInfo.storeId}`)
      } else if ('cartId' in menuInfo) {
        // 장바구니 변경하기
        await handleCartOperation(
          'PATCH',
          `/services/cart/item/${menuInfo.cartId}`,
          {
            optionList: selectedOptionIds,
            quantity,
          }
        )
        if (closeDrawer) {
          reloadCartItems()
          closeDrawer()
        }
      }
    } catch (error: any) {
      console.error(error.message)
    }
  }

  const totalOptionPrice = menuInfo.optionCategories.reduce(
    (total, category) => {
      const selectedOptionItems = selectedOptions[category.id] || []
      const selectedOptionPrices = selectedOptionItems.map(
        (option) => option.productOptionPrice
      )
      return total + selectedOptionPrices.reduce((sum, price) => sum + price, 0)
    },
    0
  )

  const totalPrice = (menuInfo.price + totalOptionPrice) * quantity

  return (
    <div>
      <div
        className={type === 'default' ? 'divide-y-4 divide-gray-medium' : ''}
      >
        {/* 가격 섹션 */}
        <div
          className={`${type === 'change' ? 'border border-gray-medium rounded-lg py-4 px-5 m-2' : 'p-6'}`}
        >
          <div className="text-lg flex justify-between font-bold">
            <span>가격</span>
            <span>{menuInfo.price.toLocaleString()}원</span>
          </div>
        </div>

        {/* 옵션 카테고리들 */}
        {menuInfo.optionCategories.map((category) => (
          <OptionSelector
            key={category.id}
            category={category}
            selectedOptions={selectedOptions}
            handleOptionChange={handleOptionChange}
            type={type}
          />
        ))}

        <QuantityControl
          quantity={quantity}
          setQuantity={setQuantity}
          type={type}
        />
      </div>

      <div className="fixed bottom-0 inset-x-0 p-3 bg-white">
        <button
          type="button"
          className={`w-full py-4 font-bold rounded-lg ${
            areAllRequiredOptionsSelected()
              ? 'bg-primary-500 text-white'
              : 'bg-gray-medium text-gray-dark'
          }`}
          onClick={handleButtonClick}
        >
          {`${totalPrice.toLocaleString()}원 ${type === 'default' ? '담기' : '변경하기'}`}
        </button>
      </div>

      {showAlert && (
        <div className="fixed bottom-20 left-1/2 transform -translate-x-1/2 w-72 p-4 bg-primary-500 text-white text-center rounded-lg shadow-lg z-50">
          필수 옵션을 선택해 주세요
        </div>
      )}
    </div>
  )
}
