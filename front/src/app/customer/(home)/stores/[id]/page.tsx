import StoreInfo from '@/containers/customer/home/stores/StoreInfo'
import CartButton from '@/containers/customer/home/stores/CartButton'

export default function StoreDetail({ params }: { params: { id: string } }) {
  return (
    <div>
      <StoreInfo />
      <CartButton />
    </div>
  )
}
