import Image from 'next/image'

export default function OrderMenuDetail({ item }: { item: any }) {
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
        {item.options.map((option: any) => (
          <p className="text-sm text-gray-dark leading-4" key={option}>
            {option}
          </p>
        ))}
      </div>
    </div>
  )
}
