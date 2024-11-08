import Image from 'next/image'
import { ProductItem } from '@/types/order'

export default function OrderMenuDetail({ item }: { item: ProductItem }) {
  return (
    <div className="flex items-start space-x-4">
      <Image
        src={item.productSrc || '/images/default_img.jpg'}
        alt={item.productName}
        width={80}
        height={80}
        className="rounded-lg bg-gray-medium"
      />
      <div>
        <h3 className="text-md font-semibold">{item.productName}</h3>
        <p className="text-sm">{item.quantity}개</p>
        {item.optionList.map((option) => (
          <p
            className="text-sm text-gray-dark leading-4"
            key={option.optionName}
          >
            {option.optionName}
          </p>
        ))}
      </div>
    </div>
  )
}
