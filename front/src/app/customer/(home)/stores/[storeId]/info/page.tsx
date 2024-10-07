import AppBar from '@/components/AppBar'
import StoreInfo from '@/containers/customer/home/stores/StoreInfo'

export default async function StoreDetailInfo({
  params,
}: {
  params: { storeId: string }
}) {
  return (
    <>
      <AppBar title="가게 정보" />
      <StoreInfo storeId={params.storeId} />
    </>
  )
}
