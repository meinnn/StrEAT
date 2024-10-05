'use client'

import { useEffect, useState } from 'react'
import { CartMenu, Menu } from '@/types/menu'
import { useRouter } from 'next/navigation'
import OptionSelector from '@/containers/customer/home/stores/menu/OptionSelector'
import QuantityControl from '@/containers/customer/home/stores/menu/QuantityControl'

interface MenuOptionsProps {
  type?: 'default' | 'change'
  menuInfo: Menu | CartMenu
}

export default function MenuOptions({
  type = 'default',
  menuInfo,
}: MenuOptionsProps) {
  const router = useRouter()

  const initialQuantity = 'quantity' in menuInfo ? menuInfo.quantity : 1
  const [selectedOptions, setSelectedOptions] = useState<
    Record<number, number[]>
  >({})
  const [quantity, setQuantity] = useState(initialQuantity)
  const [showAlert, setShowAlert] = useState(false)

  useEffect(() => {
    const initialSelections: Record<number, number[]> = {}
    menuInfo.optionCategories.forEach((category) => {
      if (category.maxSelect === 1 && category.options.length > 0) {
        initialSelections[category.id] = [category.options[0].id]
      }
    })
    setSelectedOptions(initialSelections)
  }, [menuInfo])

  const handleOptionChange = (
    categoryId: number,
    optionId: number,
    maxSelect: number
  ) => {
    setSelectedOptions((prev) => {
      const currentSelections = prev[categoryId] || []

      if (maxSelect === 1) {
        return { ...prev, [categoryId]: [optionId] }
      }

      if (currentSelections.includes(optionId)) {
        return {
          ...prev,
          [categoryId]: currentSelections.filter(
            (selectedOption) => selectedOption !== optionId
          ),
        }
      }

      if (currentSelections.length < maxSelect) {
        return { ...prev, [categoryId]: [...currentSelections, optionId] }
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

    const selectedOptionIds = Object.values(selectedOptions).flat()
    if (type === 'default') {
      const response = await fetch(`/services/cart/item/${menuInfo.id}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          quantity,
          productOptionIds: selectedOptionIds,
        }),
      })

      if (!response.ok) {
        throw new Error('Failed to add to cart')
      }
      router.push(`/customer/stores/${menuInfo.storeId}`)
    }
  }

  const totalOptionPrice = menuInfo.optionCategories.reduce(
    (total, category) => {
      const selectedOptionIds = selectedOptions[category.id] || []
      const selectedOptionPrices = category.options
        .filter((option) => selectedOptionIds.includes(option.id))
        .map((option) => option.productOptionPrice)
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
