export default function StoreInfo() {
  return (
    <>
      <div className="w-full h-48 object-cover bg-gray-medium">지도</div>
      <div className="p-6 bg-white">
        {/* 가게 정보 */}
        <div className="mb-8">
          <h2 className="text-lg font-bold mb-2">옐로우 키친 치킨</h2>
          <div className="grid grid-cols-[100px_1fr] gap-2 text-sm">
            <p className="font-semibold">주소</p>
            <p>서울특별시 강남구 역삼동 123-123 1층 01호</p>
            <p className="font-semibold">운영 시간</p>
            <div className="grid grid-cols-[max-content_1fr] gap-x-2 gap-y-1">
              <p>월</p>
              <p>정기 휴무</p>
              <p>화</p>
              <p>11:00 - 22:00</p>
              <p>수</p>
              <p>11:00 - 22:00</p>
              <p>목</p>
              <p>11:00 - 22:00</p>
              <p>금</p>
              <p>11:00 - 22:00</p>
              <p>토</p>
              <p>11:00 - 22:00</p>
              <p>일</p>
              <p>11:00 - 22:00</p>
            </div>
            <p className="font-semibold">휴무일</p>
            <div>
              <p>매주 월요일</p>
              <p>09/16~09/18 추석 연휴 휴무</p>
            </div>
            <p className="font-semibold">전화번호</p>
            <p>010-8282-9999</p>
          </div>
        </div>

        {/* 사업자 정보 */}
        <div className="mb-8">
          <h3 className="text-lg font-bold mb-2">사업자 정보</h3>
          <div className="grid grid-cols-[100px_1fr] gap-2 text-sm">
            <p className="font-semibold">대표자명</p>
            <p>김사장</p>
            <p className="font-semibold">상호명</p>
            <p>이게진짜상호명 강남점</p>
            <p className="font-semibold">사업자주소</p>
            <div>
              <p>서울특별시 강남구 역삼동 123-123 1층 01호</p>
              <p className="text-xs text-gray-dark">
                위 주소는 사업자등록증에 표기된 정보입니다
              </p>
            </div>
            <p className="font-semibold">사업자등록번호</p>
            <p>123-12-12345</p>
          </div>
        </div>

        {/* 원산지 표기 */}
        <div className="mb-8">
          <h3 className="text-lg font-bold mb-2">원산지 표기</h3>
          <p className="text-sm">
            어쩌구저쩌구: 국내산 <br />
            중국인: 중국산 <br />
            이주호: 일본산
          </p>
        </div>
      </div>
    </>
  )
}
