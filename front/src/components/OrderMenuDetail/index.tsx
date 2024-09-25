import Image from 'next/image'
import { OrderItem } from '@/types/order'
import { CartItem } from '@/types/cart'

export default function OrderMenuDetail({
  item,
}: {
  item: OrderItem | CartItem
}) {
  // 임시 type
  return (
    <div className="flex items-start space-x-4">
      <Image
        src={item.image}
        alt={item.name}
        width={80}
        height={80}
        className="rounded-lg bg-gray-medium"
      />
      <div>
        <h3 className="text-md font-semibold">{item.name}</h3>
        <p className="text-sm">{item.quantity}개</p>
        {item.options.map((option) => (
          <p className="text-sm text-gray-dark leading-4" key={option}>
            {option}
          </p>
        ))}
      </div>
    </div>
  )
}
