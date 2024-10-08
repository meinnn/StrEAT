import StoreDetails from '@/containers/customer/home/stores/StoreDetails'
import MenuList from '@/containers/customer/home/stores/MenuList'
import CartButton from '@/containers/customer/home/stores/CartButton'

interface Store {
  id: string
  name: string
  address: string
  ownerWord: string
  status: string
  storePhotos: string[]
  categories: string[]
}

async function fetchStoreDetails(storeId: string): Promise<Store> {
  const response = await fetch(
    `https://j11a307.p.ssafy.io/api/stores/${storeId}/details/customer`
  )

  if (!response.ok) {
    throw new Error('Failed to fetch store details')
  }

  const result = await response.json()
  return {
    ...result.data,
    id: storeId,
  }
}

export default async function StoreDetailPage({
  storeId,
}: {
  storeId: string
}) {
  const store = await fetchStoreDetails(storeId) // 가게 정보 가져오기

  return (
    <div>
      <StoreDetails store={store} />
      <MenuList storeId={storeId} />
      <CartButton />
    </div>
  )
}
