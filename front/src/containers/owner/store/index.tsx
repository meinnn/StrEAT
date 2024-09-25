'use client'

import Image from 'next/image'
import StoreBusinessLocation from '@/containers/owner/store/StoreBusinessLocation'
import StoreBusinessHours from '@/containers/owner/store/StoreBusinessHours'
import StoreInformation from '@/containers/owner/store/StoreInformation'

export default function OwnerStore() {
  return (
    <div className="bg-white">
      <main className="pb-8 flex flex-col">
        <div className="relative w-full h-40 overflow-hidden bg-gray-medium">
          <Image
            src="/images/보쌈사진.jpg"
            alt="store"
            fill
            className="object-cover"
          />
        </div>
        <StoreInformation />
        <StoreBusinessLocation />
        <StoreBusinessHours />
      </main>
    </div>
  )
}
