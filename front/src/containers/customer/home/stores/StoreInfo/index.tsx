import StoreMap from '@/containers/customer/home/stores/StoreMap'

interface StoreDetails {
  id: number
  userId: number
  businessRegistrationNumber: string
  name: string
  address: string
  latitude: number
  longitude: number
  type: string
  bankAccount: string
  bankName: string
  ownerWord: string
  storePhoneNumber: string
  closedDays: string | null
  status: string
  industryCategoryId: number
}

async function fetchStoreDetails(storeId: string): Promise<StoreDetails> {
  const response = await fetch(
    `https://j11a307.p.ssafy.io/api/stores/${storeId}`,
    { cache: 'no-store' }
  )

  if (!response.ok) {
    throw new Error('Failed to fetch store details')
  }

  const result = await response.json()
  return result.data // API 응답에서 가게 정보 반환
}

async function fetchBusinessDays(storeId: string) {
  const response = await fetch(
    `https://j11a307.p.ssafy.io/api/stores/business-days/${storeId}`
  )
  const result = await response.json()

  if (!response.ok) {
    throw new Error(result.message) // 에러 처리
  }

  return result.data // 영업일 정보 반환
}

export default async function StoreInfo({ storeId }: { storeId: string }) {
  const store = await fetchStoreDetails(storeId)
  const businessDays = await fetchBusinessDays(storeId)

  return (
    <>
      <StoreMap lat={store.latitude} lng={store.longitude} />
      <div className="p-6 bg-white">
        {/* 가게 정보 */}
        <div className="mb-8">
          <h2 className="text-lg font-bold mb-2">{store.name}</h2>
          <div className="grid grid-cols-[100px_1fr] gap-2 text-sm">
            <p className="font-semibold">주소</p>
            <p>{store.address}</p>
            <p className="font-semibold">운영 시간</p>
            <div className="grid grid-cols-[max-content_1fr] gap-x-2 gap-y-1">
              <p>월</p>
              <p>
                {businessDays.mondayStart} - {businessDays.mondayEnd}
              </p>
              <p>화</p>
              <p>
                {businessDays.tuesdayStart} - {businessDays.tuesdayEnd}
              </p>
              <p>수</p>
              <p>
                {businessDays.wednesdayStart} - {businessDays.wednesdayEnd}
              </p>
              <p>목</p>
              <p>
                {businessDays.thursdayStart} - {businessDays.thursdayEnd}
              </p>
              <p>금</p>
              <p>
                {businessDays.fridayStart} - {businessDays.fridayEnd}
              </p>
              <p>토</p>
              <p>
                {businessDays.saturdayStart} - {businessDays.saturdayEnd}
              </p>
              <p>일</p>
              <p>
                {businessDays.sundayStart} - {businessDays.sundayEnd}
              </p>
            </div>
            {store.closedDays && (
              <>
                <p className="font-semibold">휴무일</p>
                <p>{store.closedDays}</p>
              </>
            )}
            <p className="font-semibold">전화번호</p>
            <p>{store.storePhoneNumber}</p>
          </div>
        </div>

        {/* 사업자 정보 */}
        <div className="mb-8">
          <h3 className="text-lg font-bold mb-2">사업자 정보</h3>
          <div className="grid grid-cols-[100px_1fr] gap-2 text-sm">
            {/* <p className="font-semibold">대표자명</p> */}
            {/* <p>김사장</p> */}
            {/* <p className="font-semibold">상호명</p> */}
            {/* <p>이게진짜상호명 강남점</p> */}
            {/* <p className="font-semibold">사업자주소</p> */}
            {/* <div> */}
            {/*  <p>서울특별시 강남구 역삼동 123-123 1층 01호</p> */}
            {/*  <p className="text-xs text-gray-dark"> */}
            {/*    위 주소는 사업자등록증에 표기된 정보입니다 */}
            {/*  </p> */}
            {/* </div> */}
            <p className="font-semibold">사업자등록번호</p>
            <p>{store.businessRegistrationNumber}</p>
          </div>
        </div>
      </div>
    </>
  )
}
