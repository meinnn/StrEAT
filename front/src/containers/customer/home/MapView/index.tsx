import StoreSearchHeader from '@/components/StoreSearchHeader'
import StoreCard from '@/containers/customer/home/StoreCard'
import { FiList } from 'react-icons/fi'
import useNaverMap from '@/hooks/useNaverMap'

export default function MapView({
  setView,
}: {
  setView: (view: 'map' | 'list') => void
}) {
  // 지도 생성 및 초기화
  useNaverMap('map', { zoom: 16 })

  return (
    <div className="relative h-screen w-full">
      <div
        id="map"
        className="fixed inset-0  w-full flex items-center justify-center text-center bg-gray-light"
        style={{ height: 'calc(100vh - 4rem)' }}
      />

      <div className="relative">
        <StoreSearchHeader view="map" />
      </div>

      <div className="absolute bottom-20 w-full">
        <button
          type="button"
          onClick={() => setView('list')} // 버튼 클릭 시 'list'로 상태 변경
          className="ml-auto flex items-center bg-[#371B1B] rounded-full px-3 py-2 text-white text-xs"
        >
          <FiList className="mr-1.5" size={14} />
          <span>리스트 뷰</span>
        </button>

        <div className="px-4 py-2 flex overflow-x-auto whitespace-nowrap gap-4">
          <StoreCard />
          <StoreCard />
          <StoreCard />
          <StoreCard />
        </div>
      </div>
    </div>
  )
}
