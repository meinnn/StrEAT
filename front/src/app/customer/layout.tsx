import React from 'react'
import CustomerNav from '@/components/CustomerNav'
import ClientWrapper from '@/utils/ClientWrapper'
import { CartProvider } from '@/contexts/CartContext'

export default function CustomerLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <section>
      <ClientWrapper>
        <CartProvider>
          {children}
          <CustomerNav />
        </CartProvider>
      </ClientWrapper>
    </section>
  )
}
