export default function NotificationItem({ message }: { message: string }) {
  return (
    <div className="bg-white p-4 mb-4 rounded-lg shadow">
      <div>
        <p className="font-semibold">{message}</p>
        <p className="text-sm">옐로우 키친 치킨</p>
        <p className="mt-2 text-xs text-gray-dark">2024년 09월 21일 18:10</p>
      </div>
    </div>
  )
}
