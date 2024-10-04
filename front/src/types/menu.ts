export interface MenuItemOption {
  id: number
  productOptionName: string
}

export interface MenuOptionCategory {
  id: number
  name: string
  minSelect: number
  maxSelect: number
  isEssential: boolean
  options: MenuItemOption[]
}

export interface MenuItem {
  id: number
  name: string
  image: string
  description: string
  description_full: string
  price: number
  option_categories: MenuOptionCategory[]
}

export interface OptionCategoryItem {
  id: number
  productId: number
  productOptionCategoryId: 1
  productOptionName: string
  productOptionPrice: number
}

export interface OptionCategory {
  id: number
  productId: number
  name: string
  isEssential: boolean
  minSelect: number
  maxSelect: number
  options: OptionCategoryItem[]
}

export interface Menu {
  id: number
  name: string
  price: number
  description: string
  categories: number[]
  optionCategories: OptionCategory[]
  photos: string[]
}
