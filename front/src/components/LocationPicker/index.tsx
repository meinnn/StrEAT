import { RiSearchLine } from 'react-icons/ri'

export default function LocationPicker() {
  return (
    <div className="fixed px-3 pt-4 max-w-96 top-0 left-0 overflow-hidden bg-gray-dark max-h-[36rem] h-full w-full rounded-xl">
      <div className="flex items-center w-full shadow-md rounded-xl overflow-hidden pl-4 bg-white">
        <RiSearchLine className="text-primary-400 w-6 h-6" />
        <input
          type="text"
          placeholder="위치를 검색해주세요"
          className="outline-none placeholder-gray-dark w-full py-3 pr-4 pl-2 text-text"
        />
      </div>
      <div className="absolute w-full flex flex-col bottom-0 left-0 overflow-hidden bg-red-100 rounded-t-lg">
        <div className="flex flex-col gap-3 bg-white px-6 pt-3 pb-4 text-text">
          <h4 className="font-medium text-lg">현재 선택된 위치</h4>
          <p>선택된 위치가 없습니다.</p>
        </div>
        <button
          type="button"
          className="bg-primary-500 py-5 text-secondary-light"
        >
          위치 등록하기
        </button>
      </div>
    </div>
  )
}
