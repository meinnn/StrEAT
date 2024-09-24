import MyPage from '@/containers/customer/mypage'
import AppBar from '@/components/AppBar'
import InAppNotification from '@/containers/customer/notifications/InAppNotification'

export default function CustomerMyPage() {
  return (
    <>
      <AppBar title="마이 페이지" />
      <MyPage />
      <InAppNotification />
    </>
  )
}
