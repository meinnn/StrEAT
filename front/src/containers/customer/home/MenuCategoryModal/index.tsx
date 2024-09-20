import Image from 'next/image'
import { IoCloseOutline } from 'react-icons/io5'
import { useState } from 'react'

const menuCategories = [
  {
    title: '식사류',
    items: [
      { name: '족발/보쌈', imageUrl: '/images/족발.png' },
      { name: '고기/구이', imageUrl: '/images/고기.png' },
      { name: '회/일식', imageUrl: '/images/회.png' },
      { name: '치킨', imageUrl: '/images/치킨.png' },
      { name: '피자', imageUrl: '/images/피자.png' },
      { name: '패스트푸드', imageUrl: '/images/패스트푸드.png' },
      { name: '양식', imageUrl: '/images/양식.png' },
      { name: '찜/탕/찌개', imageUrl: '/images/찜탕찌개.png' },
      { name: '중식', imageUrl: '/images/중식.png' },
      { name: '아시안', imageUrl: '/images/아시안.png' },
      { name: '분식', imageUrl: '/images/분식.png' },
      { name: '음료', imageUrl: '/images/음료.png' },
      { name: '주류', imageUrl: '/images/주류.png' },
      { name: '기타', imageUrl: '/images/기타.png' },
    ],
  },
  {
    title: '간식류',
    items: [
      { name: '붕어빵', imageUrl: '/images/붕어빵.png' },
      { name: '어묵', imageUrl: '/images/어묵.png' },
      { name: '호떡', imageUrl: '/images/호떡.png' },
      { name: '타코야끼', imageUrl: '/images/타코야끼.png' },
      { name: '계란빵', imageUrl: '/images/계란빵.png' },
      { name: '꼬치', imageUrl: '/images/꼬치.png' },
      { name: '군고구마', imageUrl: '/images/군고구마.png' },
      { name: '와플', imageUrl: '/images/와플.png' },
      { name: '토스트', imageUrl: '/images/토스트.png' },
      { name: '옥수수', imageUrl: '/images/옥수수.png' },
      { name: '달고나', imageUrl: '/images/달고나.png' },
      { name: '카페/디저트', imageUrl: '/images/카페디저트.png' },
      { name: '탕후루', imageUrl: '/images/탕후루.png' },
    ],
  },
]

export default function MenuCategoryModal({
  onClose,
}: {
  onClose: () => void
}) {
  const [isClosing, setIsClosing] = useState(false)

  const handleClose = () => {
    setIsClosing(true)
    setTimeout(onClose, 300) // 애니메이션 지속 시간 후 모달 닫기
  }

  return (
    <>
      {/* 배경 어둡게 처리 */}
      <div
        role="presentation"
        className={`fixed inset-0 bg-black bg-opacity-50 z-40 transition-opacity duration-300 ${
          isClosing ? 'opacity-0' : 'opacity-100'
        }`}
        onClick={handleClose}
      />

      {/* 슬라이드 인/아웃 모달 */}
      <div
        className={`fixed inset-x-0 bottom-16 z-50 bg-white p-4 rounded-t-2xl shadow-lg ${
          isClosing ? 'animate-slide-down' : 'animate-slide-up'
        }`}
      >
        <button type="button" className="float-right" onClick={handleClose}>
          <IoCloseOutline size={24} />
        </button>
        <div className="mt-6">
          {menuCategories.map((category) => (
            <div key={category.title} className="mb-6 p-2">
              <h2 className="text-lg font-semibold">{category.title}</h2>
              <div className="grid grid-cols-5 gap-3 mt-2">
                {category.items.map((item) => (
                  <div
                    key={item.name}
                    className="flex flex-col justify-center items-center gap-1"
                  >
                    <button
                      type="button"
                      className="flex flex-col items-center bg-gray-200 w-14 h-14 rounded-full"
                    >
                      {/* <Image */}
                      {/*  src={item.imageUrl} */}
                      {/*  alt={item.name} */}
                      {/*  width={56} */}
                      {/*  height={56} */}
                      {/*  className="object-cover mb-2" */}
                      {/* > */}
                    </button>
                    <span className="text-xs cursor-pointer">{item.name}</span>
                  </div>
                ))}
              </div>
            </div>
          ))}
        </div>
      </div>
    </>
  )
}
