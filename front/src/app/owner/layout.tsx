import React from 'react'
import OwnerNav from '@/components/OwnerNav'

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
