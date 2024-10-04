import { useState } from 'react'
import { CiFilter } from 'react-icons/ci'

interface OrderTagProps {
  onFilterChange: (filters: string[]) => void
}

export default function OrderTag({ onFilterChange }: OrderTagProps) {
  // 태그 목록
  const tags = ['카드', '계좌이체', '간편결제', '현금', '수령완료', '주문취소']

  // 선택된 태그 상태 (기본값으로 모든 태그 선택)
  const [selectedTags, setSelectedTags] = useState<string[]>(tags)

  // 태그 클릭 시 선택 상태 변경
  const handleTagClick = (tag: string) => {
    let updatedTags = [...selectedTags]
    if (updatedTags.includes(tag)) {
      updatedTags = updatedTags.filter((t) => t !== tag) // 이미 선택된 태그는 제거
    } else {
      updatedTags.push(tag) // 선택되지 않은 태그는 추가
    }
    setSelectedTags(updatedTags)
    onFilterChange(updatedTags) // 부모 컴포넌트에 필터 변경사항 전달
  }

  return (
    <div className="flex flex-wrap justify-center gap-2 mt-4 mx-2 mb-4">
      {/* 태그 버튼들 */}
      <div className="grid grid-cols-3 gap-2 w-full">
        {tags.map((tag) => (
          <button
            type="button"
            key={tag}
            onClick={() => handleTagClick(tag)}
            className={`w-full py-2 border-2 rounded-lg ${
              selectedTags.includes(tag)
                ? 'border-primary-400 text-primary-400'
                : 'border-gray-300 text-gray-500'
            } transition-colors duration-300 ease-in-out`}
          >
            {tag}
          </button>
        ))}
      </div>
    </div>
  )
}
