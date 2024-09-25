import CustomerHomeContainer from '@/containers/customer/home'
import OngoingOrder from '@/containers/customer/home/OngoingOrder'

export default function CustomerHome() {
  return (
    <>
      <OngoingOrder />
      <CustomerHomeContainer />
    </>
  )
}
