export interface OptionCategoryItem {
  id: number
  productOptionName: string
  productOptionPrice: number
}

export interface OptionCategory {
  id: number
  name: string
  isEssential: boolean
  minSelect: number
  maxSelect: number
  options: OptionCategoryItem[]
}

export interface Menu {
  id: number
  storeId: number
  name: string
  price: number
  description: string
  optionCategories: OptionCategory[]
  image: string
}

export interface CartOptionCategoryItem extends OptionCategoryItem {
  isSelected: boolean
}

export interface CartOptionCategory extends OptionCategory {
  options: CartOptionCategoryItem[]
}

export interface CartMenu extends Omit<Menu, 'description'> {
  cartId: number
  quantity: number
  optionNameList: string[] // 사용자가 선택한 옵션 이름 리스트
  checked: boolean
  optionCategories: CartOptionCategory[]
}
