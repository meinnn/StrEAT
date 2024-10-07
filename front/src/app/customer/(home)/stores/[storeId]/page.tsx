import StoreDetailPage from '@/containers/customer/home/stores'
import QueueStatusModal from '@/containers/customer/home/stores/QueueStatusModal'

export default function StoreDetail({
  params,
  searchParams,
}: {
  params: { storeId: string }
  searchParams: { fromNFC: boolean | undefined }
}) {
  return (
    <div>
      {searchParams.fromNFC && <QueueStatusModal storeId={params.storeId} />}
      <StoreDetailPage storeId={params.storeId} />
    </div>
  )
}
