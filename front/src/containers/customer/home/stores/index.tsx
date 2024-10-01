'use client'

import StoreDetails from '@/containers/customer/home/stores/StoreDetails'
import MenuList from '@/containers/customer/home/stores/MenuList'
import CartButton from '@/containers/customer/home/stores/CartButton'

export default function StoreDetailPage({ storeId }: { storeId: string }) {
  return (
    <div>
      <StoreDetails />
      <MenuList storeId={storeId} />
      <CartButton />
    </div>
  )
}
