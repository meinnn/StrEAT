import Link from 'next/link'

interface NotificationResponse {
  id: string
  checked: boolean
  createdAt: string
  title: string
  message: string
  orderId: number | null
  storeId: number
}

export default function NotificationItem({
  notification,
}: {
  notification: NotificationResponse
}) {
  return (
    <div className="bg-white p-4 mb-4 rounded-lg shadow">
      <Link
        href={
          notification.orderId
            ? `/customer/orders/${notification.orderId}`
            : `/customer/stores/${notification.storeId}`
        }
      >
        <p className="font-semibold">{notification.message}</p>
        <p className="text-sm">{notification.title}</p>
        <p className="mt-2 text-xs text-gray-dark">{notification.createdAt}</p>
      </Link>
    </div>
  )
}
