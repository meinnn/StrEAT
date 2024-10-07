'use client'

import OrderSuccess from '@/containers/customer/payment/OrderSuccess'
import OrderFailure from '@/containers/customer/payment/OrderFailure'

export default function PaymentResult({
  searchParams,
}: {
  searchParams: { [status: string]: string | string[] | undefined }
}) {
  const { status } = searchParams // success or failure

  return (
    <div> {status === 'success' ? <OrderSuccess /> : <OrderFailure />}</div>
  )
}
