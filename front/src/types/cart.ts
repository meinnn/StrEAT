import { MenuOptionCategory } from '@/types/menu'

export interface CartItem {
  id: number
  name: string
  image: string
  price: number
  quantity: number
  checked: boolean
  options: string[] // 사용자가 선택한 옵션
  optionCategories: MenuOptionCategory[]
}
