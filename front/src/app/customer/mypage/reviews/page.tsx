import AppBar from '@/components/AppBar'
import MyReviewList from '@/containers/customer/mypage/reviews'

export default function MyReviewPage() {
  return (
    <div>
      <AppBar title="내 리뷰 조회" />
      <main className="bg-secondary-light">
        <MyReviewList />
      </main>
    </div>
  )
}
