/* eslint-disable import/prefer-default-export */
const defaultHeaders = {
  'Content-Type': 'application/json',
  'Authorization':
    'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhY2Nlc3MtdG9rZW4iLCJpYXQiOjE3Mjc4MzE0MTQsImV4cCI6MjA4NzgzMTQxNCwidXNlcklkIjoxMn0.UrVrI-WUCXdx017R4uRIl6lzxbktVSfEDjEgYe5J8UQ',
}
const imageHeaders = {
  Authorization:
    'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhY2Nlc3MtdG9rZW4iLCJpYXQiOjE3Mjc4MzE0MTQsImV4cCI6MjA4NzgzMTQxNCwidXNlcklkIjoxMn0.UrVrI-WUCXdx017R4uRIl6lzxbktVSfEDjEgYe5J8UQ',
}

// 점포 이미지 등록 API
export const postStoreImage = async (payload: FormData) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/stores/store-photos/photo`,
    {
      method: 'POST',
      headers: imageHeaders,
      body: payload,
      cache: 'no-store',
    }
  )

  if (!response.ok) {
    throw new Error('점포 이미지 등록에 실패했습니다.')
  }

  return response
}

// 점포 정보 등록 API
export const postStoreInfo = async (payload: any) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/stores/`,
    {
      method: 'POST',
      headers: defaultHeaders,
      body: JSON.stringify(payload),
      cache: 'no-store',
    }
  )

  if (!response.ok) {
    const errorMessage = await response.json()
    throw new Error(
      JSON.stringify({ message: errorMessage.message, status: response.status })
    )
  }

  return response
}

// 점포 영업일 및 영업시간 등록 API
export const postStoreBusinessDays = async (payload: any) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/stores/business-days/business-day`,
    {
      method: 'POST',
      headers: defaultHeaders,
      body: JSON.stringify(payload),
      cache: 'no-store',
    }
  )

  if (!response.ok) {
    const errorMessage = await response.json()
    throw new Error(
      JSON.stringify({ message: errorMessage.message, status: response.status })
    )
  }

  return response
}

// 점포 영업 위치 리스트 조회 API
export const fetchStoreLocation = async (payload: any) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/stores/locations/store/${payload}`,
    {
      method: 'GET',
      headers: defaultHeaders,
      cache: 'no-store',
    }
  )

  if (!response.ok) {
    const errorMessage = await response.json()
    throw new Error(
      JSON.stringify({ message: errorMessage.message, status: response.status })
    )
  }

  return response
}

// 점포 영업 위치 등록 API
export const postStoreBusinessLocation = async (payload: FormData) => {
  console.log('payload:', payload)
  const response = await fetch(
    // 원래는 create 대신 simple-only
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/stores/locations/create`,
    {
      method: 'POST',
      headers: imageHeaders,
      body: payload,
      cache: 'no-store',
    }
  )

  console.log('response:', response)

  if (!response.ok) {
    const errorMessage = await response.json()
    throw new Error(
      JSON.stringify({ message: errorMessage.message, status: response.status })
    )
  }

  return response
}

// 점포 위치 정보 업데이트 API
export const updateStoreBusinessLocation = async (payload: number) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/stores/locations/update-location?storeSimpleLocationId=${payload}`,
    {
      method: 'POST',
      headers: defaultHeaders,
      cache: 'no-store',
    }
  )

  if (!response.ok) {
    const errorMessage = await response.json()
    throw new Error(
      JSON.stringify({ message: errorMessage.message, status: response.status })
    )
  }

  return response
}

// 점포 상태 변경 API (준비중 <-> 영업중)
export const updateStoreStatus = async (payload: {
  storeId: string
  status: number
}) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/stores/${payload.storeId}/status?status=${payload.status}`,
    {
      method: 'PATCH',
      headers: defaultHeaders,
      cache: 'no-store',
    }
  )

  if (!response.ok) {
    const errorMessage = await response.json()
    throw new Error(
      JSON.stringify({ message: errorMessage.message, status: response.status })
    )
  }

  return response
}

// 점포 간편 위치 삭제 API (여러 개 삭제 가능) - DELETE - 완료
// /services/store/[id]/business-location
export const deleteStoreBusinessLocation = async (locationIdList: any) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/stores/locations/delete/multiple`,
    {
      method: 'DELETE',
      headers: defaultHeaders,
      body: JSON.stringify(locationIdList),
      cache: 'no-store',
    }
  )

  if (!response.ok) {
    const errorMessage = await response.json()
    throw new Error(
      JSON.stringify({ message: errorMessage.message, status: response.status })
    )
  }

  return response
}

// 사장님 한마디 수정 API - PATCH - 완료
// /services/store/[id]/ownerWord
export const updateStoreOwnerWord = async (ownerWord: string) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/stores/store/ownerWord`,
    {
      method: 'PATCH',
      headers: defaultHeaders,
      body: JSON.stringify(ownerWord),
      cache: 'no-store',
    }
  )

  if (!response.ok) {
    const errorMessage = await response.json()
    throw new Error(
      JSON.stringify({ message: errorMessage.message, status: response.status })
    )
  }

  return response
}

// 영업일 및 영업시간 수정 API - PUT - 완료
// /services/store/[id]/business-hours
export const updateStoreBusinessHours = async (businessDays: any) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/stores/business-days/update`,
    {
      method: 'PUT',
      headers: defaultHeaders,
      body: JSON.stringify(businessDays),
      cache: 'no-store',
    }
  )

  console.log(response)

  if (!response.ok) {
    const errorMessage = await response.json()
    throw new Error(
      JSON.stringify({ message: errorMessage.message, status: response.status })
    )
  }

  return response
}

// 영업 위치 수정 API - PATCH
// /services/store/[id]/business-location
export const updateBusinessLocation = async (locationId: number) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/stores/locations/update/${locationId}`,
    {
      method: 'PATCH',
      headers: defaultHeaders,
      cache: 'no-store',
    }
  )

  if (!response.ok) {
    const errorMessage = await response.json()
    throw new Error(
      JSON.stringify({ message: errorMessage.message, status: response.status })
    )
  }

  return response
}

// 점포 정보 수정 API (점포사진, 점포명, 점포유형, 점포번호) - PUT
export const updateStoreInfo = async (payload: any) => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACK_URL}/api/stores/update`,
    {
      method: 'PUT',
      headers: defaultHeaders,
      body: JSON.stringify(payload),
      cache: 'no-store',
    }
  )

  if (!response.ok) {
    const errorMessage = await response.json()
    throw new Error(
      JSON.stringify({ message: errorMessage.message, status: response.status })
    )
  }

  return response
}
