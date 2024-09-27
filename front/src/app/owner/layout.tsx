import OwnerNav from '@/components/OwnerNav'
import React from 'react'

export default function CustomerLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <section>
      <div className="pb-16">{children}</div>
      <OwnerNav />
    </section>
  )
}
