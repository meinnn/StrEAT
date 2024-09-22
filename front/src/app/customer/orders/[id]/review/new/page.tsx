import AppBar from '@/components/AppBar'
import CustomerCreateReview from '@/containers/customer/orders/[id]/review/new'

const STORE_INFO = {
  store: '맛있는 족발/보쌈',
  storeImage: '/images/보쌈사진.jpg',
  orders: ['매운 족발 小 1개', '보통 족발 小 2개', '외 3개'],
}

export default function CustomerCreateReviewPage() {
  return (
    <div>
      <AppBar title="리뷰 작성" />
      <div>
        <CustomerCreateReview
          store={STORE_INFO.store}
          storeImage={STORE_INFO.storeImage}
          orders={STORE_INFO.orders}
        />
      </div>
    </div>
  )
}
