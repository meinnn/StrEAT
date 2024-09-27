'use client'

import { useEffect, useState } from 'react'
import Checkbox from '@/components/Checkbox'
import RadioButton from '@/components/RadioButton'
import { FaChevronDown, FaChevronUp } from 'react-icons/fa6'
import { MenuItem } from '@/types/menu'
import { CartItem } from '@/types/cart'

interface MenuOptionsProps {
  type?: 'default' | 'change'
  menuInfo: MenuItem | CartItem
}

export default function MenuOptions({
  type = 'default',
  menuInfo,
}: MenuOptionsProps) {
  // CartItem일 경우 이미 quantity가 있으므로, CartItem을 사용한다면 해당 값을 설정
  const initialQuantity = 'quantity' in menuInfo ? menuInfo.quantity : 1
  const [selectedOptions, setSelectedOptions] = useState<
    Record<number, string[]>
  >({})
  const [quantity, setQuantity] = useState(initialQuantity)
  const [showAlert, setShowAlert] = useState(false)
  const [accordionOpen, setAccordionOpen] = useState<Record<number, boolean>>(
    {}
  ) // 아코디언 상태 관리

  // 첫 번째 RadioButton 옵션 자동 선택
  useEffect(() => {
    const initialSelections: Record<number, string[]> = {}
    menuInfo.option_categories.forEach((category) => {
      if (category.max_select === 1 && category.options.length > 0) {
        initialSelections[category.id] = [category.options[0].desc]
      }
    })
    setSelectedOptions(initialSelections)
  }, [menuInfo])

  // 아코디언 상태 초기화
  useEffect(() => {
    const initialAccordionState: Record<number, boolean> = {}
    menuInfo.option_categories.forEach((category) => {
      initialAccordionState[category.id] = type !== 'change' // change일 때는 접혀있도록
    })
    setAccordionOpen(initialAccordionState)
  }, [menuInfo, type])

  const handleOptionChange = (
    categoryId: number,
    optionDesc: string,
    maxSelect: number
  ) => {
    setSelectedOptions((prev) => {
      const currentSelections = prev[categoryId] || []

      if (maxSelect === 1) {
        return { ...prev, [categoryId]: [optionDesc] }
      }

      if (currentSelections.includes(optionDesc)) {
        return {
          ...prev,
          [categoryId]: currentSelections.filter(
            (selectedOption) => selectedOption !== optionDesc
          ),
        }
      }

      if (currentSelections.length < maxSelect) {
        return { ...prev, [categoryId]: [...currentSelections, optionDesc] }
      }

      return prev
    })
  }

  const handleQuantityChange = (amount: number) => {
    setQuantity((prev) => Math.max(1, prev + amount))
  }

  const areAllRequiredOptionsSelected = () => {
    return menuInfo.option_categories.every((category) => {
      const selectedCount = selectedOptions[category.id]?.length || 0
      return selectedCount >= category.min_select
    })
  }

  const handleButtonClick = () => {
    if (!areAllRequiredOptionsSelected()) {
      setShowAlert(true)
      setTimeout(() => setShowAlert(false), 3000)
    }

    // 장바구니 작업
  }

  const toggleAccordion = (categoryId: number) => {
    if (type === 'change') {
      setAccordionOpen((prev) => ({ ...prev, [categoryId]: !prev[categoryId] }))
    }
  }

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
        {menuInfo.option_categories.map((category) => (
          <div
            key={category.id}
            className={`${type === 'change' ? 'border border-gray-medium rounded-lg py-4 px-5 m-2' : 'p-6'}`}
          >
            <div
              role="presentation"
              className="flex justify-between items-center cursor-pointer"
              onClick={() => toggleAccordion(category.id)}
            >
              <div
                className={`flex justify-between items-center ${type === 'default' ? 'w-full' : ''}`}
              >
                <h2 className="text-lg font-bold flex items-center">
                  {category.name}
                </h2>
                {category.min_select > 0 ? (
                  <span className="ml-2 bg-primary-50 px-3 py-1 rounded-full text-primary-500 font-semibold text-xs">
                    필수
                  </span>
                ) : (
                  <span className="ml-2 bg-gray-medium px-3 py-1 rounded-full text-gray-dark font-semibold text-xs">
                    선택
                  </span>
                )}
              </div>
              {type === 'change' && (
                <span>
                  {accordionOpen[category.id] ? (
                    <FaChevronDown />
                  ) : (
                    <FaChevronUp />
                  )}
                </span>
              )}
              {/* 아코디언 토글 화살표 */}
            </div>

            {/* 선택된 옵션 표시 */}
            {type === 'change' && selectedOptions[category.id]?.length > 0 && (
              <p className="text-sm text-gray-dark">
                {selectedOptions[category.id].join(', ')}
              </p>
            )}

            {/* 아코디언 내용: type이 change일 때만 접힘 */}
            {(type !== 'change' || accordionOpen[category.id]) && (
              <>
                {category.max_select > 1 &&
                  (category.min_select === category.max_select ? (
                    <p className="text-gray-dark text-sm">
                      {category.max_select}개 선택 필수
                    </p>
                  ) : (
                    <p className="text-gray-dark text-sm">
                      최대 {category.max_select}개 선택
                    </p>
                  ))}

                <div className="mt-5 mx-2 space-y-5">
                  {category.options.map((option) =>
                    category.max_select > 1 ? (
                      <Checkbox
                        key={option.id}
                        onChange={() =>
                          handleOptionChange(
                            category.id,
                            option.desc,
                            category.max_select
                          )
                        }
                        checked={
                          selectedOptions[category.id]?.includes(option.desc) ||
                          false
                        }
                        id={`option-${option.id}`}
                        label={option.desc}
                        size={24}
                      />
                    ) : (
                      <RadioButton
                        key={option.id}
                        onChange={() =>
                          handleOptionChange(
                            category.id,
                            option.desc,
                            category.max_select
                          )
                        }
                        checked={
                          selectedOptions[category.id]?.includes(option.desc) ||
                          false
                        }
                        id={`option-${option.id}`}
                        name={`option-${category.id}`}
                        label={option.desc}
                        size={24}
                      />
                    )
                  )}
                </div>
              </>
            )}
          </div>
        ))}

        {/* 수량 조절 */}
        <div
          className={`${type === 'change' ? 'border border-gray-medium rounded-lg py-4 px-5 m-2' : 'p-6'}`}
        >
          <div className="flex justify-between items-center text-lg font-bold">
            <h2>수량</h2>
            <div className="flex items-center">
              <button
                type="button"
                className={`flex justify-center items-center rounded-full size-7 ${
                  quantity === 1
                    ? 'bg-gray-medium text-gray-dark'
                    : 'border border-primary-500 text-primary-500 hover:bg-primary-500 hover:text-white active:bg-primary-600'
                }`}
                onClick={() => handleQuantityChange(-1)}
                disabled={quantity === 1}
              >
                -
              </button>
              <span className="mx-4 text-xl">{quantity}</span>
              <button
                type="button"
                className="flex justify-center items-center border border-primary-500 text-primary-500 hover:bg-primary-500 hover:text-white active:bg-primary-600 rounded-full size-7"
                onClick={() => handleQuantityChange(1)}
              >
                +
              </button>
            </div>
          </div>
        </div>
      </div>

      {/* 주문 버튼 */}
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
          {`${(menuInfo.price * quantity).toLocaleString()}원 ${type === 'default' ? '담기' : '변경하기'}`}
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
