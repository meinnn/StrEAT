import Image from 'next/image'
import Link from 'next/link'

export default function MenuList({ storeId }: { storeId: string }) {
  // 임시 데이터
  const MENU_CATEGORIES = [
    {
      id: 1,
      title: '추천 메뉴',
      items: [
        {
          id: 1,
          name: '후라이드 치킨',
          description: '주인장이 혼신의 힘을 다해 튀긴 개쩌는 후라이드',
          price: '1,000,000,000원',
          imageUrl: '/images/fried_chicken.png',
        },
        {
          id: 2,
          name: '양념 치킨',
          description: '달콤한 양념과 치킨의 완벽한 조화',
          price: '1,000,000,000원',
          imageUrl: '/images/seasoned_chicken.png',
        },
      ],
    },
    {
      id: 2,
      title: '세트 메뉴',
      items: [
        {
          id: 3,
          name: '치킨 세트',
          description: '후라이드 치킨과 콜라 세트',
          price: '1,200,000,000원',
          imageUrl: '/images/chicken_set.png',
        },
      ],
    },
    {
      id: 3,
      title: '사이드 메뉴',
      items: [
        {
          id: 4,
          name: '감자튀김',
          description: '바삭바삭한 감자튀김',
          price: '500,000,000원',
          imageUrl: '/images/fries.png',
        },
        {
          id: 5,
          name: '콜라',
          description: '시원한 콜라 한 캔',
          price: '100,000,000원',
          imageUrl: '/images/cola.png',
        },
      ],
    },
  ]

  return (
    <div className="m-6">
      {MENU_CATEGORIES.map((category) => (
        <div key={category.id} className="mb-8">
          <h2 className="text-lg font-bold mb-4">{category.title}</h2>
          {category.items.map((item) => (
            <Link
              href={`/customer/stores/${storeId}/menu/${item.id}`}
              key={item.id}
              className="flex items-center mb-4 px-2"
            >
              <Image
                src={item.imageUrl}
                alt={item.name}
                width={80}
                height={80}
                className="w-20 h-20 object-cover rounded-lg bg-gray-medium"
              />
              <div className="ml-4">
                <h3 className="font-semibold">{item.name}</h3>
                <p className="text-xs">{item.description}</p>
                <p className="text-sm font-semibold mt-2">{item.price}</p>
              </div>
            </Link>
          ))}
        </div>
      ))}
    </div>
  )
}
