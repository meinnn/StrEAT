'use client'

import OrderSuccess from '@/containers/customer/payment/OrderSuccess'
import { useSearchParams } from 'next/navigation'
import OrderFailure from '@/containers/customer/payment/OrderFailure'

export default function PaymentResult() {
  const searchParams = useSearchParams()
  const status = searchParams.get('status') // success or failure

  return (
    <div> {status === 'success' ? <OrderSuccess /> : <OrderFailure />}</div>
  )
}
