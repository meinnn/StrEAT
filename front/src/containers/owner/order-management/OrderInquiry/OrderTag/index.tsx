import { useState } from 'react'
import { CiFilter } from 'react-icons/ci'

interface OrderTagProps {
  onFilterChange: (filters: string[]) => void
}

export default function OrderTag({ onFilterChange }: OrderTagProps) {
  // 선택된 태그 상태
  const [selectedTags, setSelectedTags] = useState<string[]>([])

  // 태그 목록
  const tags = [
    '주문정보',
    '1:1문의',
    '서비스평가',
    '스탬프/쿠폰',
    '포인트',
    '월간랭킹',
  ]

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
    <div className="flex flex-wrap justify-start gap-4 mt-4 ml-4 mb-4">
      {/* 필터 아이콘 */}
      <div className="flex items-center justify-center mb-2">
        <CiFilter className="text-2xl" />
      </div>

      {/* 태그 버튼들 */}
      <div className="grid grid-cols-3 gap-4">
        {tags.map((tag) => (
          <button
            type="button"
            key={tag}
            onClick={() => handleTagClick(tag)}
            className={`px-4 py-2 border-2 rounded-lg ${
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
