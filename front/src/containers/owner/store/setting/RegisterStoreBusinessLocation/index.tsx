import { MdOutlineCameraAlt } from 'react-icons/md'

export default function RegisterStoreBusinessLocation() {
  return (
    <section className="overflow-hidden flex flex-col items-center pt-6 absolute z-50 bg-white w-full max-w-80 rounded-lg">
      <h1 className="mb-3 font-medium text-text">영업 장소를 등록해주세요</h1>
      <div className="flex flex-col items-center gap-3 px-3 w-full mb-3">
        <p className="text-xs px-2 font-normal text-text">
          현재 선택된 장소가 없습니다.
        </p>
        <div className=" bg-gray-medium w-full h-28 rounded overflow-hidden text-text">
          <label
            htmlFor="file-upload"
            className="cursor-pointer flex flex-col justify-center items-center bg-gray-medium w-full h-full gap-1"
          >
            <MdOutlineCameraAlt className="w-6 h-6" />
            <p className="text-[10px] font-normal">
              영업 장소 이미지를 추가해주세요
            </p>
          </label>
          <input id="file-upload" type="file" className="hidden" />
        </div>
      </div>
      <div className="bg-gray-dark w-full h-48">지도</div>
      <div className="flex gap-2 self-center px-3 py-3 w-full bg-white">
        <button className="text-xs w-full py-2 bg-gray-medium text-text rounded-lg font-normal">
          취소
        </button>
        <button className="text-xs w-full py-2 bg-primary-500 text-secondary-light rounded-lg font-normal">
          등록하기
        </button>
      </div>
    </section>
  )
}
