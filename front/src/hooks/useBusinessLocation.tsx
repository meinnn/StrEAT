/* eslint-disable import/prefer-default-export */
import { useQuery } from '@tanstack/react-query'

interface StoreLocationPhoto {
  createdAt: string
  id: number
  latitude: number
  longitude: number
  src: string
}

interface StoreLocation {
  address: string
  createdAt: string
  id: number
  latitude: number
  locationPhotos: StoreLocationPhoto[]
  longitude: number
  nickname: string
}

export const useBusinessLocation = (storeId: number | null | undefined) => {
  const getStoreLocation = async () => {
    const response = await fetch(
      `/services/store/${storeId}/business-location`,
      {
        method: 'GET',
      }
    )
    if (!response.ok) {
      console.error('영업 위치 리스트 조회에 실패했습니다')
    }
    return response.json()
  }

  return useQuery<StoreLocation[], Error>({
    queryKey: ['/store/business-location', storeId],
    queryFn: getStoreLocation,
  })
}
