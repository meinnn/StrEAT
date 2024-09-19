import StoreSearchHeader from '@/components/StoreSearchHeader'
import StoreCard from '@/containers/customer/home/StoreCard'
import { FiList } from 'react-icons/fi'

export default function MapView({
  setView,
}: {
  setView: (view: 'map' | 'list') => void
}) {
  return (
    <div className="relative h-screen w-full">
      <div className="absolute inset-0 h-full w-full flex items-center justify-center text-center">
        <span className="text-9xl text-gray-dark">지도</span>
      </div>

      <div className="relative z-10">
        <StoreSearchHeader view="map" />
      </div>

      <div className="absolute bottom-16 w-full z-10">
        <button
          type="button"
          onClick={() => setView('list')} // 버튼 클릭 시 'list'로 상태 변경
          className="ml-auto me-4 flex items-center bg-[#371B1B] rounded-full px-3 py-2 text-white text-xs"
        >
          <FiList className="mr-1.5" size={14} />
          <span>리스트 뷰</span>
        </button>

        <div className="p-4 flex overflow-x-auto whitespace-nowrap gap-4">
          <StoreCard />
          <StoreCard />
          <StoreCard />
          <StoreCard />
        </div>
      </div>
    </div>
  )
}
