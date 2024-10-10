import Drawer from '@/components/Drawer'
import Image from 'next/image'

const MENU_CATEGORIES = [
  {
    title: '식사류',
    items: [
      { name: '족발/보쌈', imageUrl: '/images/menu/족발.png' },
      { name: '고기/구이', imageUrl: '/images/menu/고기.png' },
      { name: '회/일식', imageUrl: '/images/menu/회.png' },
      { name: '치킨', imageUrl: '/images/menu/chicken.png' },
      { name: '피자', imageUrl: '/images/menu/피자.png' },
      { name: '패스트푸드', imageUrl: '/images/menu/패스트푸드.png' },
      { name: '양식', imageUrl: '/images/menu/양식.png' },
      { name: '찜/탕/찌개', imageUrl: '/images/menu/찜탕찌개.png' },
      { name: '중식', imageUrl: '/images/menu/중식.png' },
      { name: '아시안', imageUrl: '/images/menu/아시안.png' },
      { name: '분식', imageUrl: '/images/menu/분식.png' },
      { name: '음료', imageUrl: '/images/menu/음료.png' },
      { name: '주류', imageUrl: '/images/menu/주류.png' },
      { name: '기타', imageUrl: '/images/menu/기타.png' },
    ],
  },
  {
    title: '간식류',
    items: [
      { name: '붕어빵', imageUrl: '/images/menu/bungeobbang.png' },
      { name: '어묵', imageUrl: '/images/menu/어묵.png' },
      { name: '호떡', imageUrl: '/images/menu/호떡.png' },
      { name: '타코야끼', imageUrl: '/images/menu/takoyaki.png' },
      { name: '계란빵', imageUrl: '/images/menu/계란빵.png' },
      { name: '꼬치', imageUrl: '/images/menu/skewer.png' },
      { name: '군고구마', imageUrl: '/images/menu/sweet_potato.png' },
      { name: '와플', imageUrl: '/images/menu/waffle.png' },
      { name: '토스트', imageUrl: '/images/menu/토스트.png' },
      { name: '옥수수', imageUrl: '/images/menu/옥수수.png' },
      { name: '달고나', imageUrl: '/images/menu/dalgona.png' },
      { name: '카페/디저트', imageUrl: '/images/menu/카페디저트.png' },
      { name: '탕후루', imageUrl: '/images/menu/tanghulu.png' },
    ],
  },
]

export default function MenuCategoryModal({
  onClose,
  onSelect,
}: {
  onClose: () => void
  onSelect: (menuName: string) => void
}) {
  const handleMenuSelect = (menuName: string) => {
    onSelect(menuName) // 선택한 메뉴 전달
    onClose() // 모달 닫기
  }

  return (
    <Drawer onClose={onClose}>
      {MENU_CATEGORIES.map((category) => (
        <div key={category.title} className="mb-6 p-2">
          <h2 className="text-lg font-semibold">{category.title}</h2>
          <div className="grid grid-cols-5 gap-3 mt-2">
            {category.items.map((item) => (
              <div
                role="presentation"
                key={item.name}
                className="flex flex-col justify-center items-center gap-1"
                onClick={() => handleMenuSelect(item.name)} // 메뉴 선택 처리
              >
                <button
                  type="button"
                  className="flex flex-col items-center bg-secondary w-16 h-16 rounded-full"
                >
                  <Image
                    src={item.imageUrl}
                    alt={item.name}
                    width={64}
                    height={64}
                    className="object-cover aspect-square p-1.5"
                  />
                </button>
                <span className="text-xs cursor-pointer">{item.name}</span>
              </div>
            ))}
          </div>
        </div>
      ))}
    </Drawer>
  )
}
