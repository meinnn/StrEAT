import BackButtonWithImage from '@/components/BackButtonWithImage'
import MenuOptions from '@/containers/customer/home/stores/menu/MenuOptions'

export default function MenuDetails() {
  // 임시 데이터
  const MENU_INFO = {
    name: '후라이드 치킨',
    description:
      '주방장이 혼신의 힘을 다해 튀긴 개쩌는 후라이드 \n겉바속촉 부위 선택 가능',
    price: 1000000000,
    option_categories: [
      {
        id: 1,
        name: '부분육 선택',
        min_select: 1,
        max_select: 1, // 1개만 선택 가능 -> RadioButton
        options: [
          { id: 1, desc: '한마리' },
          { id: 2, desc: '순살 변경' },
          { id: 3, desc: '윙&봉 변경' },
        ],
      },
      {
        id: 2,
        name: '소스 추가 선택',
        is_essential: false,
        min_select: 2,
        max_select: 2, // 여러 개 선택 가능 -> Checkbox
        options: [
          { id: 4, desc: '양념치킨 소스' },
          { id: 5, desc: '스모크 소스' },
        ],
      },
      {
        id: 3,
        name: '소스 추가 선택',
        is_essential: false,
        min_select: 0,
        max_select: 2, // 여러 개 선택 가능 -> Checkbox
        options: [
          { id: 6, desc: '양념치킨 소스' },
          { id: 7, desc: '스모크 소스' },
          { id: 8, desc: '다른 소스' },
        ],
      },
    ],
  }

  return (
    <div className="mb-20">
      <BackButtonWithImage src="/" alt="메뉴 사진" title={MENU_INFO.name} />

      <div className="m-6">
        <h1 className="text-2xl font-bold">{MENU_INFO.name}</h1>
        <p className="mt-1 leading-5 text-gray-dark whitespace-pre-line">
          {MENU_INFO.description}
        </p>
      </div>

      <MenuOptions menuInfo={MENU_INFO} />
    </div>
  )
}
