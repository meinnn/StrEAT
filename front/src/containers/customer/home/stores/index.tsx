import StoreDetails from '@/containers/customer/home/stores/StoreDetails'
import MenuList from '@/containers/customer/home/stores/MenuList'
import CartButton from '@/containers/customer/home/stores/CartButton'
import { ReviewSummary } from '@/containers/customer/home/StoreListItem'

interface Store {
  id: number
  name: string
  address: string
  ownerWord: string
  status: string
  storePhotos: string[]
  categories: string[]
  reviewTotalCount: number
  averageScore: number
}

async function fetchStoreReviewSummary(
  storeId: string
): Promise<ReviewSummary> {
  const response = await fetch(
    `https://j11a307.p.ssafy.io/api/orders/stores/${storeId}/reviews/summary`
  )

  if (!response.ok) {
    throw new Error('Failed to fetch store review summary')
  }

  const result = await response.json()
  return result.data
}

async function fetchStoreDetails(storeId: string): Promise<Store> {
  const response = await fetch(
    `https://j11a307.p.ssafy.io/api/stores/${storeId}/details/customer`,
    { cache: 'no-store' }
  )

  if (!response.ok) {
    throw new Error('Failed to fetch store details')
  }

  const result = await response.json()
  const reviewSummary = await fetchStoreReviewSummary(storeId)
  return {
    ...result.data,
    id: storeId,
    reviewTotalCount: reviewSummary.reviewTotalCount,
    averageScore: reviewSummary.averageScore,
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
