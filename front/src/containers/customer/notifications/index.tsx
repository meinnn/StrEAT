import NotificationItem from '@/containers/customer/notifications/NotificationItem'
import { cookies } from 'next/headers'

interface NotificationResponse {
  id: string
  checked: boolean
  createdAt: string
  title: string
  message: string
  orderId: number | null
  storeId: number
}

async function fetchNotificationList(): Promise<NotificationResponse[]> {
  const cookieStore = cookies()
  const token = cookieStore.get('accessToken')?.value

  const response = await fetch(
    `https://j11a307.p.ssafy.io/api/push-alert/all?pgno=0&spp=10`,
    {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }
  )

  if (!response.ok) {
    throw new Error('Failed to fetch notification list')
  }

  const result = await response.json()
  return result.data.pushAlertResponses
}

export default async function Notifications() {
  const notifications = await fetchNotificationList()

  return (
    <div className="p-4">
      {notifications.map((notification) => (
        <NotificationItem key={notification.id} notification={notification} />
      ))}
    </div>
  )
}
