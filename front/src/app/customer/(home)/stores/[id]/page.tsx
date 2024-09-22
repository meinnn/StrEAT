import CartButton from '@/containers/customer/home/stores/CartButton'
import StoreDetails from '@/containers/customer/home/stores/StoreDetails'

export default function StoreDetail({ params }: { params: { id: string } }) {
  return (
    <div>
      <StoreDetails />
      <CartButton />
    </div>
  )
}
