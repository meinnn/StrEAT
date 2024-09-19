import { GoChevronRight } from 'react-icons/go'
import { RiSearchLine } from 'react-icons/ri'

export default function StoreSearchHeader({ view }: { view: 'map' | 'list' }) {
  return (
    <div className="p-6">
      <div className="flex items-center justify-between rounded-xl h-12 px-5 border border-gray-medium drop-shadow-sm">
        <span>현재 주소</span>
        <GoChevronRight size="18" />
      </div>
      <div className="mt-4 flex overflow-x-auto whitespace-nowrap gap-2">
        <button
          type="button"
          className="flex items-center border border-gray-medium rounded-full px-3 h-9 text-xs"
        >
          <RiSearchLine className="text-primary-500 mr-1" size={16} />
          <span>메뉴 검색</span>
        </button>

        {view === 'map' && (
          <button
            type="button"
            className="bg-primary-500 text-white rounded-full px-6 h-9 text-sm"
          >
            현재 지도에서 가게 재검색
          </button>
        )}
      </div>
    </div>
  )
}
