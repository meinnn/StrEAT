import NotificationItem from '@/containers/customer/notifications/NotificationItem'

export const NOTIFICATIONS = [
  {
    id: 'order-status-4',
    icon: '🙌',
    title: '픽업 완료! 맛있게 드세요',
  },
  {
    id: 'order-status-3',
    icon: '🍽️',
    title: '메뉴 조리 완료! 픽업을 기다리고 있어요',
  },
  {
    id: 'order-status-2',
    icon: '🍳',
    title: '주문 수락! 맛있게 만들고 있어요',
  },
  {
    id: 'order-status-1',
    icon: '⏳',
    title: '주문 요청 완료! 사장님 수락 후 조리가 시작돼요',
  },
  {
    id: 'favorite-alert',
    icon: '🔔',
    title: '주변에 단골 가게가 영업 중이에요!',
  },
]

export default function Notifications() {
  return (
    <div className="p-4">
      {NOTIFICATIONS.map((notification, index) => (
        <NotificationItem
          key={notification.id}
          message={`${notification.icon} ${notification.title}`}
        />
      ))}
    </div>
  )
}
