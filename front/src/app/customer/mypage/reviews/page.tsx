'use client'

import AppBar from '@/components/AppBar'
import MyReviewList from '@/containers/customer/mypage/reviews'
import ClientWrapper from '@/utils/ClientWrapper'

export default function MyReviewPage() {
  return (
    <div>
      <AppBar title="내 리뷰 조회" />
      <ClientWrapper>
        <main className="bg-secondary-light pb-32">
          <MyReviewList />
        </main>
      </ClientWrapper>
    </div>
  )
}
