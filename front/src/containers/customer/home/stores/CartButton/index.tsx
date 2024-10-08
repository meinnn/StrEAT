'use client'

import { TiShoppingCart } from 'react-icons/ti'
import Link from 'next/link'
import { useCart } from '@/contexts/CartContext'

export default function CartButton() {
  const { cartItems } = useCart()

  return (
    <Link href="/customer/cart" className="fixed bottom-6 right-6">
      <button
        type="button"
        className="bg-primary-500 text-white p-5 rounded-full shadow-lg relative"
      >
        <TiShoppingCart size={38} />
        {cartItems.length > 0 && (
          <div className="absolute top-0 right-0 bg-text text-white text-sm font-bold rounded-full px-2 py-1 aspect-square">
            {cartItems.length}
          </div>
        )}
      </button>
    </Link>
  )
}
