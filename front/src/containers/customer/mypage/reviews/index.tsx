import ReviewItem from '@/containers/customer/mypage/components/ReviewListItem'

const REVIEW_LIST = [
  {
    id: 1,
    storeTitle: '맛있는 보쌈/족발',
    storeImage: '/images/보쌈사진.jpg',
    date: '2024.09.09 (월)',
    order: '매운 족발 小 1개 외 4개',
    content: `안녕하세요. 보쌈과 족발이 정말 맛있네요. 굿입니다. 안녕하세요. 보쌈과
  족발이 정말 맛있네요. 굿입니다. 안녕하세요. 보쌈과 족발이 정말
  맛있네요. 굿입니다. 보쌈과 족발이 정말 맛있네요. 굿입니다. 보쌈과
  족발이 정말 맛있네요. 굿입니다. 보쌈과 족발이 정말 맛있네요.`,
  },
  {
    id: 2,
    storeTitle: '맛있는 보쌈/족발',
    storeImage: '/images/보쌈사진.jpg',
    date: '2024.09.09 (월)',
    order: '매운 족발 小 1개 외 4개',
    content: `안녕하세요. 보쌈과 족발이 정말 맛있네요. 굿입니다. 안녕하세요. 보쌈과
  족발이 정말 맛있네요. 굿입니다. 안녕하세요. 보쌈과 족발이 정말
  맛있네요. 굿입니다. 보쌈과 족발이 정말 맛있네요. 굿입니다. 보쌈과
  족발이 정말 맛있네요. 굿입니다. 보쌈과 족발이 정말 맛있네요.`,
  },
  {
    id: 3,
    storeTitle: '맛있는 보쌈/족발',
    storeImage: '/images/보쌈사진.jpg',
    date: '2024.09.08 (일)',
    order: '매운 족발 小 1개 외 4개',
    content: `안녕하세요. 보쌈과 족발이 정말 맛있네요. 굿입니다. 안녕하세요. 보쌈과
  족발이 정말 맛있네요. 굿입니다. 안녕하세요. 보쌈과 족발이 정말
  맛있네요. 굿입니다. 보쌈과 족발이 정말 맛있네요. 굿입니다. 보쌈과
  족발이 정말 맛있네요. 굿입니다. 보쌈과 족발이 정말 맛있네요.`,
  },

  {
    id: 4,
    storeTitle: '맛있는 보쌈/족발',
    storeImage: '/images/보쌈사진.jpg',
    date: '2024.09.08 (일)',
    order: '매운 족발 小 1개 외 4개',
    content: `안녕하세요. 보쌈과 족발이 정말 맛있네요. 굿입니다. 안녕하세요. 보쌈과
  족발이 정말 맛있네요. 굿입니다. 안녕하세요. 보쌈과 족발이 정말
  맛있네요. 굿입니다. 보쌈과 족발이 정말 맛있네요. 굿입니다. 보쌈과
  족발이 정말 맛있네요. 굿입니다. 보쌈과 족발이 정말 맛있네요.`,
  },
]

export default function MyReviewList() {
  let lastDate = ''

  return (
    <div>
      {REVIEW_LIST.map((review) => {
        const isVisibleDate = lastDate !== review.date
        lastDate = review.date

        return (
          <div key={review.id}>
            {isVisibleDate && (
              <h2 className="pl-4 pt-7 pb-3 text-lg font-medium text-text">
                {review.date}
              </h2>
            )}
            <ReviewItem
              store={review.storeTitle}
              storeImage={review.storeImage}
              date={review.date}
              order={review.order}
              content={review.content}
            />
          </div>
        )
      })}
    </div>
  )
}
