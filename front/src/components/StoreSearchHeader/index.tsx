import { GoChevronRight } from 'react-icons/go'
import { RiSearchLine } from 'react-icons/ri'
import { useState } from 'react'
import MenuCategoryModal from '@/containers/customer/home/MenuCategoryModal'
import { useRouter } from 'next/navigation'

type StoreSearchHeaderProps = {
  view: 'map' | 'list'
  currentAddress: string
  onReSearch?: () => void
}

export default function StoreSearchHeader({
  view,
  currentAddress,
  onReSearch,
}: StoreSearchHeaderProps) {
  const [isModalOpen, setIsModalOpen] = useState(false)
  const [selectedMenu, setSelectedMenu] = useState<string | null>(null) // 선택된 메뉴 저장
  const router = useRouter()

  const handleMenuSelect = (menuName: string) => {
    setSelectedMenu(menuName)
    setIsModalOpen(false) // 메뉴 선택 후 모달 닫기
  }

  return (
    <div className="p-6">
      <button
        type="button"
        onClick={() => {
          router.push('/customer/search')
        }}
        className="w-full flex items-center justify-between rounded-xl h-12 px-5 border border-gray-medium drop-shadow-md bg-white"
      >
        <span>{currentAddress || '위치 검색'}</span>
        <GoChevronRight size="18" />
      </button>
      <div className="mt-4 flex overflow-x-auto whitespace-nowrap gap-2">
        <button
          type="button"
          onClick={() => setIsModalOpen(true)} // 모달 열기
          className={`flex items-center rounded-full px-3 h-9 text-xs ${
            selectedMenu
              ? 'bg-[#371B1B] text-white'
              : 'bg-white border border-gray-medium'
          }`} // 선택된 메뉴에 따라 스타일 변경
        >
          <RiSearchLine className="text-primary-500 mr-1" size={16} />
          <span>{selectedMenu || '메뉴 검색'}</span>
        </button>

        {view === 'map' && (
          <button
            type="button"
            onClick={onReSearch}
            className="bg-primary-500 text-white rounded-full px-6 h-9 text-sm"
          >
            현재 지도에서 가게 재검색
          </button>
        )}
      </div>

      {isModalOpen && (
        <MenuCategoryModal
          onClose={() => setIsModalOpen(false)}
          onSelect={handleMenuSelect}
        /> // 모달 컴포넌트 호출
      )}
    </div>
  )
}
