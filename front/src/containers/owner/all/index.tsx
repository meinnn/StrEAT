import Image from 'next/image'
import { GoChevronRight } from 'react-icons/go'

export default function OwnerAll() {
  return (
    <div className="pt-12 bg-navbar-all-gradient pb-32">
      <h1 className="font-semibold mb-4 text-xl pl-11">야옹이님, 안녕하세요</h1>

      <section className="px-7 flex flex-col gap-5 pb-12">
        <div className="text-text pt-5 pb-7 px-5 flex bg-secondary-light flex-col gap-4 border rounded-lg border-gray-medium">
          <div className="flex gap-2 items-center">
            <p className="relative h-4 w-6">
              <Image
                src="/images/truck.png"
                alt="푸드트럭 아이콘"
                fill
                priority
              />
            </p>
            <h4 className="font-medium">현재 운영중인 점포</h4>
          </div>
          <p className="font-bold text-xl">옐로우 키친 치킨</p>
        </div>
        <div className="text-text pt-5 pb-7 px-5 flex bg-secondary-medium flex-col gap-6 border rounded-lg border-gray-medium">
          <div className="flex justify-between">
            <h4 className="font-medium">등록된 계좌 정보</h4>
            <button className="flex items-center text-xs font-normal">
              수정하기
              <GoChevronRight />
            </button>
          </div>
          <div className="flex gap-2 items-center text-text">
            <span className="w-8 h-8 bg-gray-medium rounded-full" />
            <div className="flex flex-col gap-1">
              <p className="text-xs font-medium">우리은행</p>
              <p className="font-bold text-xl">1002-000-00000000</p>
            </div>
          </div>
        </div>
      </section>

      <hr className="h-1 border-none bg-gray-medium" />

      <section className="px-8 pt-6 pb-10">
        <div className="flex justify-between items-center mb-5 pr-4">
          <h4 className="font-medium text-gray-dark text-sm">푸드트럭 공고</h4>
          <button className="flex items-center text-xs font-normal">
            전체보기
            <GoChevronRight />
          </button>
        </div>
        <div className="flex flex-col border border-gray-medium rounded-xl px-2 pt-3 pb-2">
          <h5 className="text-primary-500 text-xs pl-2">최신 공고</h5>
          {new Array(2).fill(0).map((_) => {
            return (
              <div
                className="text-text flex flex-col gap-2 border-b py-4 px-2 last:border-b-0"
                key={_}
              >
                <div className="flex justify-between items-center">
                  <div className="flex gap-4">
                    <p className="font-mediumm">전북장수목장 숲속음악회</p>
                    <span className="flex items-center text-xs border border-primary-500 rounded-full text-primary-500 px-5 text-[10px] font-semibold">
                      모집중
                    </span>
                  </div>
                  <GoChevronRight />
                </div>
                <ul className="text-xs flex flex-col gap-2 font-normal">
                  <li className="flex">
                    <span className="w-14">운영장소</span>
                    <p>한국마사회 장수목장</p>
                  </li>
                  <li className="flex">
                    <span className="w-14">행사일</span>
                    <p>2024년 10월 4일</p>
                  </li>
                </ul>
              </div>
            )
          })}
        </div>
      </section>

      <hr className="h-1 border-none bg-gray-medium" />

      <section className="flex flex-col items-start pt-6 px-8 text-text">
        <h4 className="font-medium text-gray-dark mb-6 ">회원 관리</h4>
        <div className="flex flex-col gap-8">
          <button className="font-normal">로그아웃</button>
          <button className="font-normal">회원 탈퇴</button>
        </div>
      </section>
    </div>
  )
}
