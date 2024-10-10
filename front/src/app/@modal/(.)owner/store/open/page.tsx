'use client'

import { useSearchParams } from 'next/navigation'
import LocationConfirmCancelModal from '@/containers/owner/store/LocationConfirmCancelModal'
import ModalLayout from '@/components/ModalLayout'

export default function StoreModal() {
  return (
    <ModalLayout>
      <LocationConfirmCancelModal />
    </ModalLayout>
  )
}
