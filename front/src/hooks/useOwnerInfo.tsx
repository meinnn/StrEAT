/* eslint-disable import/prefer-default-export */
import { useQuery } from '@tanstack/react-query'

interface OwnerInfo {
  name: string
  profileImgSrc: string
  storeId: number
  dibsStoreAlert: boolean
  orderStatusAlert: boolean
}

export const useOwnerInfo = () => {
  const getOwnerInfo = async () => {
    const response = await fetch(`/services/users/owner`, {
      method: 'GET',
    })

    if (!response.ok) {
      throw new Error('Failed to fetch store information.')
    }

    return response.json()
  }

  return useQuery<OwnerInfo, Error>({
    queryKey: ['/users/owner'],
    queryFn: getOwnerInfo,
  })
}
