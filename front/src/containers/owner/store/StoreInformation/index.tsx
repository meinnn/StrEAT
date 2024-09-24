import { useState } from 'react'
import { FaStar } from 'react-icons/fa'
import { GoChevronRight } from 'react-icons/go'
import { HiOutlineLocationMarker } from 'react-icons/hi'

export default function StoreInformation() {
  const [isBusinessStart, setIsBusinessStart] = useState(false)

  return (
    <section className="flex flex-col px-6 pt-6">
      <div className="flex flex-col gap-1 mb-2">
        <span className="text-xs font-normal pl-1">#치킨</span>
        <h1 className="text-2xl font-bold">옐로우 키친 치킨</h1>
      </div>
      <div className="flex flex-col gap-3 mb-5">
        <div className="flex justify-between items-center">
          <div className="flex gap-4">
            <div className="flex gap-[2px] items-center">
              <FaStar className="text-yellow-400" />
              <span>4.9</span>
            </div>
            <div className="flex gap items-center gap-1">
              <p>리뷰 333개</p>
              <GoChevronRight />
            </div>
          </div>
          <div className="flex gap-1 items-center">
            <span
              className={`${isBusinessStart ? 'bg-green-500' : 'bg-red-500'} h-2 w-2 block rounded-full`}
            ></span>
            <p className="font-normal pr-2">
              {isBusinessStart ? '영업중' : '영업전'}
            </p>
          </div>
        </div>
        <div className="flex justify-between">
          <div className="flex gap-[2px] items-center">
            <HiOutlineLocationMarker className="text-primary-500" />
            <p className="text-xs font-normal-">
              서울특별시 강남구 테헤란로 212
            </p>
          </div>
          <button
            onClick={() => setIsBusinessStart(!isBusinessStart)}
            className={`rounded-full font-semibold py-1 px-5 border ${isBusinessStart ? 'border-green-500 text-green-500' : 'border-[#3e3e3e] text-[#3e3e3e] hover:text-primary-500 hover:border-primary-500'}`}
          >
            {isBusinessStart ? '영업 종료하기' : '영업 시작하기'}
          </button>
        </div>
      </div>
      <div className="flex flex-col gap-2">
        <div className="py-3 px-4 border-gray-dark border rounded-lg">
          <p className="flex gap-2 items-center text-xs">
            <span>📢</span>
            역삼역 1번 출구 앞 큰 건물 뒤에 있습니다! 오늘 주방장 폼 미침
          </p>
        </div>
        <button className="rounded-md text-xs font-normal bg-primary-500 py-2 w-full text-secondary-light">
          사장님 한 마디 수정
        </button>
      </div>
    </section>
  )
}
