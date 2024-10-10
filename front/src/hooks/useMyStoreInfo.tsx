/* eslint-disable react-hooks/rules-of-hooks */
import { useQuery } from '@tanstack/react-query'

interface storeImage {
  createdAt: string
  id: number
  src: string
  storeId: number
}

interface StoreInfo {
  address: string
  bankAccount: string
  bankName: string
  businessRegistrationNumber: string
  closedDays: string
  id: number
  industryCategroyId: number
  latitude: number
  longitude: number
  name: string
  ownerWord: string
  status: string
  storePhoneNumber: string
  type: string
  useId: number
}

export interface StoreBusinessDays {
  id: number
  storeId: number
  fridayEnd: string
  fridayStart: string
  mondayEnd: string
  mondayStart: string
  saturdayEnd: string
  saturdayStart: string
  sundayEnd: string
  sundayStart: string
  thursdayEnd: string
  thursdayStart: string
  tuesdayEnd: string
  tuesdayStart: string
  wednesdayEnd: string
  wednesdayStart: string
}

export interface StoreLocation {
  locationPhotos: any
  createdAt: string
  id: number
  latitude: number
  longitude: number
  src: string
  storeId: number
}

export interface StoreReview {
  averageScore: number
  reviewTotalCount: number
}

export const useMyStoreInfo = (storeId: number | null | undefined) => {
  console.log('내 점포 아이디:', storeId)

  const getStoreInfo = async () => {
    const response = await fetch(`/services/store/${storeId}`, {
      method: 'GET',
    })

    if (!response.ok) {
      throw new Error('Failed to fetch store information.')
    }

    return response.json()
  }

  return useQuery<
    {
      storeImage: storeImage[]
      storeInfo: StoreInfo
      storeBusinessDays: StoreBusinessDays
      storeLocations: StoreLocation[]
      storeReview: StoreReview
    },
    Error
  >({
    queryKey: ['/store', storeId],
    queryFn: getStoreInfo,
    enabled: !!storeId,
  })
}
