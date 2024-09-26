import { RiSearchLine } from 'react-icons/ri'

export default function LocationPicker() {
  return (
    <div className="w-5/6 max-w-lg ">
      <div className="relative flex items-center w-full shadow-md bg-white rounded-xl overflow-hidden pl-4">
        <RiSearchLine className="text-primary-400 w-6 h-6" />
        <input
          type="text"
          placeholder="위치를 검색해주세요"
          className="absolute outline-none bg-white placeholder-gray-dark w-full py-3 pr-4 pl-2 text-text"
        />
        <div className="absoflex flex-col">
          <div className="flex flex-col">
            <h4>현재 선택된 위치</h4>
            <p>선택된 위치가 없습니다.</p>
          </div>
          <button>위치 등록하기</button>
        </div>
      </div>
    </div>
  )
}
