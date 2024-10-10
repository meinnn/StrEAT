'use client'

import ModalLayout from '@/components/ModalLayout'
import OrderDetail from '@/components/OrderDetail'

export default function OrderDetailModal({
  params,
}: {
  params: { id: string }
}) {
  const { id: orderId } = params

  return (
    <ModalLayout>
      <OrderDetail orderId={orderId} />
    </ModalLayout>
  )
}
