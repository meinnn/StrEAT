'use client'

import { useEffect, useState } from 'react'
import Checkbox from '@/components/Checkbox'
import RadioButton from '@/components/RadioButton'
import BackButtonWithImage from '@/components/BackButtonWithImage'

export default function MenuDetails() {
  // 임시 데이터
  const MENU_INFO = {
    name: '후라이드 치킨',
    description:
      '주방장이 혼신의 힘을 다해 튀긴 개쩌는 후라이드 \n겉바속촉 부위 선택 가능',
    price: 1000000000,
    option_categories: [
      {
        id: 1,
        name: '부분육 선택',
        min_select: 1,
        max_select: 1, // 1개만 선택 가능 -> RadioButton
        options: [
          { id: 1, desc: '한마리' },
          { id: 2, desc: '순살 변경' },
          { id: 3, desc: '윙&봉 변경' },
        ],
      },
      {
        id: 2,
        name: '소스 추가 선택',
        is_essential: false,
        min_select: 2,
        max_select: 2, // 여러 개 선택 가능 -> Checkbox
        options: [
          { id: 4, desc: '양념치킨 소스' },
          { id: 5, desc: '스모크 소스' },
        ],
      },
      {
        id: 3,
        name: '소스 추가 선택',
        is_essential: false,
        min_select: 0,
        max_select: 2, // 여러 개 선택 가능 -> Checkbox
        options: [
          { id: 6, desc: '양념치킨 소스' },
          { id: 7, desc: '스모크 소스' },
        ],
      },
    ],
  }

  const [selectedOptions, setSelectedOptions] = useState<
    Record<number, string[]>
  >({})

  const [quantity, setQuantity] = useState(1)
  const [showAlert, setShowAlert] = useState(false)

  // 첫 번째 RadioButton 옵션 자동 선택
  useEffect(() => {
    const initialSelections: Record<number, string[]> = {}
    MENU_INFO.option_categories.forEach((category) => {
      if (category.max_select === 1 && category.options.length > 0) {
        initialSelections[category.id] = [category.options[0].desc] // 첫 번째 옵션 선택
      }
    })
    setSelectedOptions(initialSelections)
  }, [])

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
        // 이미 선택된 경우에는 선택 해제
        return {
          ...prev,
          [categoryId]: currentSelections.filter(
            (selectedOption) => selectedOption !== optionDesc
          ),
        }
      }

      // 선택 가능한 옵션 개수 제한
      if (currentSelections.length < maxSelect) {
        return {
          ...prev,
          [categoryId]: [...currentSelections, optionDesc],
        }
      }

      return prev
    })
  }

  const handleQuantityChange = (amount: number) => {
    setQuantity((prev) => Math.max(1, prev + amount))
  }

  // 필수 옵션들이 충분히 선택되었는지 확인하는 함수
  const areAllRequiredOptionsSelected = () => {
    return MENU_INFO.option_categories.every((category) => {
      // min_select > 0 인 카테고리는 필수 항목이므로 확인
      const selectedCount = selectedOptions[category.id]?.length || 0
      return selectedCount >= category.min_select
    })
  }

  // 경고 메시지를 일정 시간 후 자동으로 숨김
  const handleButtonClick = () => {
    if (!areAllRequiredOptionsSelected()) {
      setShowAlert(true)

      // 3초 후에 경고 메시지를 자동으로 숨김
      setTimeout(() => {
        setShowAlert(false)
      }, 3000)

      return
    }
    // 필수 옵션이 모두 선택되었을 때 실행할 코드
    console.log('장바구니 담기 실행')
  }

  return (
    <div className="mb-20">
      <BackButtonWithImage src="/" alt="메뉴 사진" title={MENU_INFO.name} />

      <div className="divide-y-4">
        {/* 메뉴 정보 */}
        <div className="m-6">
          <h1 className="text-2xl font-bold">{MENU_INFO.name}</h1>
          <p className="mt-1 leading-5 text-gray-dark whitespace-pre-line">
            {MENU_INFO.description}
          </p>
          <div className="mt-6 text-lg flex justify-between font-bold">
            <span>가격</span>
            <span>{MENU_INFO.price.toLocaleString()}원</span>
          </div>
        </div>

        {/* 옵션 카테고리 */}
        {MENU_INFO.option_categories.map((category) => (
          <div key={category.id} className="p-6">
            <div className="flex justify-between items-center">
              <h2 className="text-lg font-bold">{category.name}</h2>
              {category.min_select > 0 ? (
                <div className="bg-primary-50 px-3 py-1 rounded-full text-primary-500 font-semibold text-xs">
                  필수
                </div>
              ) : (
                <div className="bg-gray-medium px-3 py-1 rounded-full text-gray-dark font-semibold text-xs">
                  선택
                </div>
              )}
            </div>
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
          </div>
        ))}

        {/* 수량 선택 */}
        <div className="p-6 flex justify-between items-center text-lg font-bold">
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
          // disabled={!areAllRequiredOptionsSelected()} // 필수 옵션을 모두 선택해야 활성화
        >
          {`${(MENU_INFO.price * quantity).toLocaleString()}원 담기`}
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
