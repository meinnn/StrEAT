import React from 'react'
import CustomerNav from '@/components/CustomerNav'

export default function CustomerLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <section>
      {children}
      <CustomerNav />
    </section>
  )
}
