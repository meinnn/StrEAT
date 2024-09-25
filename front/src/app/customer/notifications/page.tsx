import AppBar from '@/components/AppBar'
import Notifications from '@/containers/customer/notifications'

export default function CustomerNotifications() {
  return (
    <div className="h-screen bg-secondary-light">
      <AppBar title="알림" />
      <Notifications />
    </div>
  )
}
