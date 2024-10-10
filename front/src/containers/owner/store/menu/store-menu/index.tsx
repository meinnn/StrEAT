import { GoPlus } from 'react-icons/go'
import { useQuery } from '@tanstack/react-query'
import FloatingButton from '@/components/FloatingButton'
import StoreMenuItem from '@/containers/owner/store/menu/StoreMenuItem'
import { useOwnerInfo } from '@/hooks/useOwnerInfo'
import { useMyStoreInfo } from '@/hooks/useMyStoreInfo'

interface Option {
  id: number
  productId: number
  productOptionCategoryId: number
  productOptionName: string
  productOptionPrice: number
}

export interface OptionCategory {
  id: number
  isEssential: boolean
  maxSelect: number
  minSelect: number
  name: string
  options: Option[]
  productId: number
}

interface Menu {
  categoryId: number
  description: string
  name: string
  optionCategories: OptionCategory[]
  photos: any[]
  price: number
  productId: number
  stockStatus: boolean
}

export default function StoreMenu() {
  const {
    data: ownerInfo,
    error: ownerInfoError,
    isLoading: ownerInfoLoading,
  } = useOwnerInfo()
  const {
    data: storeData,
    error: storeError,
    isLoading: stoerLoading,
  } = useMyStoreInfo(ownerInfo?.storeId)
  const getStoreMenuList = async () => {
    const response = await fetch(`/services/store/${ownerInfo?.storeId}/menu`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization':
          'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhY2Nlc3MtdG9rZW4iLCJpYXQiOjE3Mjc4MzE0MTQsImV4cCI6MjA4NzgzMTQxNCwidXNlcklkIjoxMn0.UrVrI-WUCXdx017R4uRIl6lzxbktVSfEDjEgYe5J8UQ',
      },
    })
    if (!response.ok) {
      console.error('가게 메뉴 리스트 조회에 실패했습니다.')
    }
    return response.json()
  }

  const {
    data: storeMenuListData,
    error,
    isLoading,
  } = useQuery<Menu[], Error>({
    queryKey: ['/store/menu', ownerInfo?.storeId],
    queryFn: getStoreMenuList,
    enabled: !!ownerInfo?.storeId,
  })

  if (isLoading || ownerInfoLoading) {
    return <p>로딩중</p>
  }

  if (error || ownerInfoError) {
    return <p>에러 발생</p>
  }
  return (
    <main className="py-5 pb-16">
      <h2 className="text-xl font-bold mb-2 pl-7">
        {storeData?.storeInfo?.name}
      </h2>
      <div className="flex flex-col">
        {storeMenuListData &&
          storeMenuListData.length > 0 &&
          storeMenuListData.map((menu) => {
            return (
              <StoreMenuItem
                key={menu.productId}
                storeId={ownerInfo?.storeId}
                productId={menu.productId}
                name={menu.name}
                optionList={menu.optionCategories}
                stockStatus={menu.stockStatus}
                description={menu.description}
                price={menu.price}
              />
            )
          })}
      </div>
      <FloatingButton
        href="/owner/store/menu/menu-add"
        icon={<GoPlus className="text-secondary-light w-9 h-9" />}
      />
    </main>
  )
}
