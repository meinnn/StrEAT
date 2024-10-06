import { useState } from 'react'
import Checkbox from '@/components/Checkbox'
import RadioButton from '@/components/RadioButton'
import { FaChevronDown, FaChevronUp } from 'react-icons/fa6'
import {
  CartOptionCategory,
  OptionCategory,
  OptionCategoryItem,
} from '@/types/menu'

interface OptionSelectorProps {
  category: OptionCategory | CartOptionCategory
  selectedOptions: Record<number, OptionCategoryItem[]> // 변경된 타입
  handleOptionChange: (
    categoryId: number,
    option: OptionCategoryItem,
    maxSelect: number
  ) => void
  type: 'default' | 'change'
}

export default function OptionSelector({
  category,
  selectedOptions,
  handleOptionChange,
  type,
}: OptionSelectorProps) {
  const [accordionOpen, setAccordionOpen] = useState(type !== 'change')

  const toggleAccordion = () => {
    if (type === 'change') {
      setAccordionOpen((prev) => !prev)
    }
  }

  const selectedOptionNames = selectedOptions[category.id]
    ?.map((option) => option.productOptionName)
    .join(', ')

  return (
    <div
      key={category.id}
      className={`${type === 'change' ? 'border border-gray-medium rounded-lg py-4 px-5 m-2' : 'p-6'}`}
    >
      <div
        role="presentation"
        className="flex justify-between items-center cursor-pointer"
        onClick={toggleAccordion}
      >
        <div
          className={`flex justify-between items-center ${type === 'default' ? 'w-full' : ''}`}
        >
          <h2 className="text-lg font-bold flex items-center">
            {category.name}
          </h2>
          {category.isEssential ? (
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
            <span>{accordionOpen ? <FaChevronDown /> : <FaChevronUp />}</span>
          </span>
        )}
        {/* 아코디언 토글 화살표 */}
      </div>

      {/* 선택된 옵션 표시 */}
      {type === 'change' && (
        <p className="text-sm text-gray-dark">{selectedOptionNames}</p>
      )}

      {/* 아코디언 내용: type이 change일 때만 접힘 */}
      {(type !== 'change' || accordionOpen) && (
        <>
          {category.maxSelect > 1 &&
            (category.minSelect === category.maxSelect ? (
              <p className="text-gray-dark text-sm">
                {category.maxSelect}개 선택 필수
              </p>
            ) : (
              <p className="text-gray-dark text-sm">
                최대 {category.maxSelect}개 선택
              </p>
            ))}

          <div className="mt-5 mx-2 space-y-5">
            {category.options.map((option) =>
              category.maxSelect > 1 ? (
                <Checkbox
                  key={option.id}
                  onChange={() =>
                    handleOptionChange(category.id, option, category.maxSelect)
                  }
                  checked={
                    selectedOptions[category.id]?.includes(option) || false
                  }
                  id={`option-${option.id}`}
                  label={`${option.productOptionName}${option.productOptionPrice > 0 ? ` (+${option.productOptionPrice.toLocaleString()}원)` : ''}`}
                  size={24}
                />
              ) : (
                <RadioButton
                  key={option.id}
                  onChange={() =>
                    handleOptionChange(category.id, option, category.maxSelect)
                  }
                  checked={
                    selectedOptions[category.id]?.includes(option) || false
                  }
                  id={`option-${option.id}`}
                  name={`option-${category.id}`}
                  label={`${option.productOptionName}${option.productOptionPrice > 0 ? ` (+${option.productOptionPrice.toLocaleString()}원)` : ''}`}
                  size={24}
                />
              )
            )}
          </div>
        </>
      )}
    </div>
  )
}
