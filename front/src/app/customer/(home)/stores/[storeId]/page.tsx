import CartButton from '@/containers/customer/home/stores/CartButton'
import StoreDetails from '@/containers/customer/home/stores/StoreDetails'
import MenuList from '@/containers/customer/home/stores/MenuList'

export default function StoreDetail({
  params,
}: {
  params: { storeId: string }
}) {
  return (
    <div>
      <StoreDetails />
      <MenuList storeId={params.storeId} />
      <CartButton />
    </div>
  )
}
