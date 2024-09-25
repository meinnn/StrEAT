import AppBar from '@/components/AppBar'
import OrderItem from '@/containers/customer/orders/OrderItem'

const ORDER_LIST = [
  {
    id: 1,
    date: '2024.09.09 (월)',
    imageUrl: '/images/보쌈사진.jpg',
    title: '맛있는 보쌈/족발',
    orderList: [
      '매운 족발 小 1개',
      '보통 족발 中 2개',
      '매운 족발 大 1개',
      '매운 족발 大 1개',
    ],
    progress: 2,
    review: false,
  },
  {
    id: 2,
    date: '2024.09.09 (월)',
    imageUrl: '/images/보쌈사진.jpg',
    title: '맛있는 보쌈/족발',
    orderList: ['매운 족발 小 1개', '보통 족발 中 2개', '매운 족발 大 1개'],
    progress: 4,
    review: false,
  },
  {
    id: 3,
    date: '2024.09.08 (일)',
    imageUrl: '/images/보쌈사진.jpg',
    title: '맛있는 보쌈/족발',
    orderList: ['매운 족발 小 1개', '보통 족발 中 2개', '매운 족발 大 1개'],
    progress: 4,
    review: true,
  },
  {
    id: 4,
    date: '2024.09.08 (일)',
    imageUrl: '/images/보쌈사진.jpg',
    title: '맛있는 보쌈/족발',
    orderList: ['매운 족발 小 1개', '보통 족발 中 2개', '매운 족발 大 1개'],
    progress: 4,
    review: true,
  },
]

export default function Orders() {
  let lastDate = ''

  return (
    <div className="bg-secondary-light">
      <AppBar title="주문내역 조회" />
      <main className=" px-4 flex flex-col pb-32">
        <div className="flex flex-col gap-1">
          {ORDER_LIST.map((order) => {
            const isVisibleDate = lastDate !== order.date
            lastDate = order.date

            return (
              <div key={order.id}>
                {isVisibleDate && (
                  <h2 className="font-medium pb-5 pt-7">{order.date}</h2>
                )}
                <OrderItem
                  key={order.id}
                  id={order.id}
                  date={order.date}
                  imageUrl={order.imageUrl}
                  title={order.title}
                  orderList={order.orderList}
                  progress={order.progress}
                  review={order.review}
                />
              </div>
            )
          })}
        </div>
      </main>
    </div>
  )
}
