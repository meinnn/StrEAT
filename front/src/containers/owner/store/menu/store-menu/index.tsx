import { GoPlus } from 'react-icons/go'
import FloatingButton from '@/components/FloatingButton'
import StoreMenuItem from '@/containers/owner/store/menu/StoreMenuItem'

const MENU_LIST = [
  {
    id: 1,
    name: '후라이드 치킨',
    optionList: ['엄청매운맛', '매운맛', '중간맛', '순한맛', '착한맛'],
  },
  {
    id: 2,
    name: '후라이드 치킨',
    optionList: ['엄청매운맛', '매운맛', '중간맛', '순한맛', '착한맛'],
  },
]

export default function StoreMenu() {
  return (
    <main className="py-5 pb-16">
      <h2 className="text-xl font-bold mb-2 pl-7">옐로우 키친 치킨</h2>
      <div className="flex flex-col">
        {MENU_LIST.map((menu) => {
          return (
            <StoreMenuItem
              key={menu.id}
              name={menu.name}
              optionList={menu.optionList}
            />
          )
        })}
      </div>
      <FloatingButton
        href=""
        icon={<GoPlus className="text-secondary-light w-9 h-9" />}
      />
    </main>
  )
}
