export default function OwnerHome() {
  return (
    <div
      className="bg-gray-medium relative"
      style={{ height: 'calc(100vh - 4rem)' }}
    >
      <section className=" w-full p-2 top-2 absolute h-28 bg-none">
        <div className="flex justify-center items-center bg-secondary-light w-full h-full shadow-md rounded-md ">
          <p>오늘은 장사하기 좋은 날^^</p>
          <p>들어갈 내용은 추후 수정</p>
        </div>
      </section>
      <section className="flex flex-col items-center shadow-up-shadow bg-secondary-light rounded-t-xl w-full absolute bottom-0 px-6 pb-10 pt-7">
        <h2 className="text-xl font-semibold pb-6">
          영업 장소가 이 장소가 맞나요?
        </h2>
        <p className="pb-8">서울특별시 강남구 테헤란로 212</p>
        <button className="bg-primary-500 text-secondary-light rounded-md text-xl font-semibold w-full py-4">
          오늘의 영업 시작하기
        </button>
      </section>
    </div>
  )
}
