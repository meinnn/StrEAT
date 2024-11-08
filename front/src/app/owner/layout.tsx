'use client'

import React from 'react'
import OwnerNav from '@/components/OwnerNav'
import ClientWrapper from '@/utils/ClientWrapper'

export default function CustomerLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <ClientWrapper>
      <section>
        <div className="pb-16">{children}</div>
        <OwnerNav />
      </section>
    </ClientWrapper>
  )
}
