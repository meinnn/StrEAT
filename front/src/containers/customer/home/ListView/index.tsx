import { FiMap } from 'react-icons/fi'
import StoreSearchHeader from '@/components/StoreSearchHeader'
import StoreListItem from '@/containers/customer/home/StoreListItem'
import { MdCheckBoxOutlineBlank, MdCheckBox } from 'react-icons/md'

export default function ListView({
  setView,
}: {
  setView: (view: 'map' | 'list') => void
}) {
  return (
    <>
      <StoreSearchHeader view="list" />
      <div className="absolute top-[5.5rem] w-full">
        {/* 지도 뷰로 전환하는 버튼 */}
        <button
          type="button"
          onClick={() => setView('map')} // 버튼 클릭 시 'map'으로 상태 변경
          className="ml-auto me-6 flex items-center border border-gray-medium rounded-full px-3 h-9 text-xs"
        >
          <FiMap className="mr-1.5 " size={14} />
          <span>지도 뷰</span>
        </button>

        {/* 리스트 내용 */}
        <div className="p-4">
          <div className="flex justify-between items-center mx-4 my-2">
            <h2 className="text-xl font-semibold text-primary-950">
              내 근처 붕어빵
            </h2>
            <div className="flex items-center space-x-1 text-gray-dark">
              {/* 커스텀 체크박스 */}
              <label
                htmlFor="favorite"
                className="flex items-center cursor-pointer"
              >
                <input
                  type="checkbox"
                  id="favorite"
                  name="favorite"
                  className="appearance-none h-4 w-4 border border-gray-300 rounded-sm checked:bg-primary-500 checked:border-transparent focus:outline-none" // 체크박스 스타일링
                />
                <span className="ml-2">찜한 가게만</span>
              </label>
            </div>
          </div>

          <StoreListItem />
          <StoreListItem />
          <StoreListItem />
          <StoreListItem />
          <StoreListItem />
        </div>
      </div>
    </>
  )
}
