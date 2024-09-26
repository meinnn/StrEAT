import AppBar from '@/components/AppBar'
import ReviewItem from './ReviewItem'

const REVIEW_LIST = [
  {
    id: 1,
    user: {
      profileUrl: '/images/보쌈사진.jpg',
      nickname: '김사람',
    },
    createdAt: '2024.09.09 (월)',
    score: 3,
    reviewImageList: [
      '/images/보쌈사진.jpg',
      '/images/보쌈사진.jpg',
      '/images/보쌈사진.jpg',
    ],
    content:
      '안녕하세요. 보쌈과 족발이 정말 맛있네요. 굿입니다. 안녕하세요. 보쌈과 족발이 정말 맛있네요. 굿입니다. 안녕하세요. 보쌈과 족발이 정말 맛있네요. 굿입니다. 보쌈과 족발이 정말 맛있네요. 굿입니다. 보쌈과 족발이 정말 맛있네요.',
    orderList: [
      '매운 족발 小 1개',
      '매운 족발 小 1개',
      '매운 족발 小 1개',
      '매운 족발 小 1개',
      '매운 족발 小 1개',
      '매운 족발 小 1개',
      '매운 족발 小 1개',
      '매운 족발 小 1개',
      '매운 족발 小 1개',
      '매운 족발 小 1개',
    ],
  },
  {
    id: 2,
    user: {
      profileUrl: '/images/보쌈사진.jpg',
      nickname: '김사람',
    },
    createdAt: '2024.09.09 (월)',
    score: 5,
    reviewImageList: [
      '/images/보쌈사진.jpg',
      '/images/보쌈사진.jpg',
      '/images/보쌈사진.jpg',
    ],
    content:
      '안녕하세요. 보쌈과 족발이 정말 맛있네요. 굿입니다. 안녕하세요. 보쌈과 족발이 정말 맛있네요. 굿입니다. 안녕하세요. 보쌈과 족발이 정말 맛있네요. 굿입니다. 보쌈과 족발이 정말 맛있네요. 굿입니다. 보쌈과 족발이 정말 맛있네요.',
    orderList: [
      '매운 족발 小 1개',
      '매운 족발 小 1개',
      '매운 족발 小 1개',
      '매운 족발 小小小小 1개',
      '매운 족발 小小小小小小小小 1개',
      '매운 족발 小 1개',
      '매운 족발 小 1개',
      '매운 족발 小 1개',
      '매운 족발 小小小 1개',
      '매운 족발 小 1개',
    ],
  },
]

export default function StoreReview() {
  return (
    <div>
      <AppBar title={`리뷰 조회 `} option={`(${REVIEW_LIST.length})`} />
      <main className="mb-32">
        {REVIEW_LIST.map((review) => {
          return (
            <ReviewItem
              key={review.id}
              user={review.user}
              createdAt={review.createdAt}
              score={review.score}
              reviewImageList={review.reviewImageList}
              content={review.content}
              orderList={review.orderList}
            />
          )
        })}
      </main>
    </div>
  )
}
