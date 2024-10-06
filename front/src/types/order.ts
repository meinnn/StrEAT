export interface OptionItem {
  optionName: string
  optionPrice: number
}

export interface ProductItem {
  productName: string
  quantity: number
  productPrice: number
  productSrc: string
  optionList: OptionItem[]
}

export interface OrderDetailData {
  ordersId: number
  orderNumber: string
  orderCreatedAt: string
  orderReceivedAt: string
  paymentMethod: 'CREDIT_DARD' | 'CASH' | 'SIMPLE_PAYMENT' | 'ACCOUNT_TRANSFER'
  status:
    | 'REJECTED'
    | 'WAITING_FOR_PROCESSING'
    | 'PROCESSING'
    | 'WAITING_FOR_RECEIPT'
    | 'RECEIVED'
  menuCount: number
  totalPrice: number
  storeId: number
  storeName: string
  storeSrc: string
  waitingTeam: number
  waitingMenu: number
  productList: ProductItem[]
}
