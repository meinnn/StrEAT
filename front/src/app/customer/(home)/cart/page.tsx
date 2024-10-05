import AppBar from '@/components/AppBar'
import CartPage from '@/containers/customer/home/cart'
import ClientWrapper from '@/utils/ClientWrapper'

export default function Cart() {
  return (
    <>
      <AppBar title="장바구니" />
      <ClientWrapper>
        <CartPage />
      </ClientWrapper>
    </>
  )
}
