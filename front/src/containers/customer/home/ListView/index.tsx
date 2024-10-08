import { FiMap } from 'react-icons/fi'
import StoreSearchHeader from '@/components/StoreSearchHeader'
import StoreListItem from '@/containers/customer/home/StoreListItem'
import Checkbox from '@/components/Checkbox'
import { useState } from 'react'
import { Store } from '@/types/store'

export default function ListView({
  setView,
  currentAddress,
  storeList,
}: {
  setView: (view: 'map' | 'list') => void
  currentAddress: string
  storeList: Store[]
}) {
  const [isFavoriteOnly, setIsFavoriteOnly] = useState(false)

  return (
    <>
      <StoreSearchHeader view="list" currentAddress={currentAddress} />

      {/* 지도 뷰로 전환하는 버튼 */}
      <button
        type="button"
        onClick={() => setView('map')} // 버튼 클릭 시 'map'으로 상태 변경
        className="absolute top-[5.5rem] right-6 flex items-center border border-gray-medium rounded-full px-3 h-9 text-xs"
      >
        <FiMap className="mr-1.5 " size={14} />
        <span>지도 뷰</span>
      </button>

      {/* 리스트 내용 */}
      <div className="m-4">
        <div className="flex justify-between items-center mx-4 my-2">
          <h2 className="text-xl font-semibold text-primary-950">
            내 근처 붕어빵
          </h2>
          <div className="text-gray-dark">
            <Checkbox
              id="custom-checkbox"
              checked={isFavoriteOnly}
              onChange={setIsFavoriteOnly}
              label="찜한 가게만"
            />
          </div>
        </div>

        {storeList.map((store) => (
          <div key={store.id} className="divide-y divide-gray-medium">
            <StoreListItem store={store} />
          </div>
        ))}
      </div>
    </>
  )
}
