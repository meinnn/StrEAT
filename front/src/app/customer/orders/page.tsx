import Orders from '@/containers/customer/orders'
import ClientWrapper from '@/utils/ClientWrapper'

export default function CustomerOrders() {
  return (
    <div>
      <ClientWrapper>
        <Orders />
      </ClientWrapper>
    </div>
  )
}
