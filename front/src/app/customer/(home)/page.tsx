import CustomerHomeContainer from '@/containers/customer/home'
import { GoChevronRight } from 'react-icons/go'
import Link from 'next/link'
import OngoingOrder from '@/containers/customer/home/OngoingOrder'

export default function CustomerHome() {
  return (
    <>
      <OngoingOrder />
      <CustomerHomeContainer />
    </>
  )
}
