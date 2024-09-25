export interface MenuItemOption {
  id: number
  desc: string
}

export interface MenuOptionCategory {
  id: number
  name: string
  min_select: number
  max_select: number
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
