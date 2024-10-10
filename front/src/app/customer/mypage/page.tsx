import MyPage from '@/containers/customer/mypage'
import AppBar from '@/components/AppBar'

export default function CustomerMyPage() {
  return (
    <>
      <AppBar title="마이 페이지" />
      <MyPage />
    </>
  )
}
