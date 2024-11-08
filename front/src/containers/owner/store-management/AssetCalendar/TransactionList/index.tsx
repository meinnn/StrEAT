import { useEffect, useRef } from 'react'
import { FaArrowDown } from 'react-icons/fa'

interface DailySales {
  customerName: string
  payAmount: number
}

interface Transaction {
  dailySalesList: DailySales[]
  dailyTotalPayAmount: number
}

interface TransactionListProps {
  transactions: { [key: string]: Transaction } | undefined // transactions는 객체로 변경
  selectedDate: string
  loadMoreTransactions: () => void // 더 많은 거래 내역을 불러오는 함수
  hasMore: boolean // 더 불러올 데이터가 있는지 여부
}

export default function TransactionList({
  transactions = {},
  selectedDate,
  loadMoreTransactions,
  hasMore,
}: TransactionListProps) {
  const loader = useRef<HTMLDivElement | null>(null) // IntersectionObserver 감지용 ref

  // 선택한 날짜의 day 값 추출
  const day = Number(selectedDate.split('-')[2])

  // 선택한 날짜에 맞는 거래 내역 필터링 (day와 일치하는 트랜잭션 찾기)
  const filteredTransaction = transactions[day] || {}

  const dailySalesList = filteredTransaction?.dailySalesList || [] // 해당 날짜의 dailySalesList

  useEffect(() => {
    // IntersectionObserver 설정
    const observer = new IntersectionObserver(
      (entries) => {
        const target = entries[0]
        if (target.isIntersecting && hasMore) {
          loadMoreTransactions() // 스크롤이 끝에 도달하면 더 많은 데이터를 불러옴
        }
      },
      { threshold: 1.0 }
    )

    const currentLoader = loader.current

    if (currentLoader) {
      observer.observe(currentLoader) // 로더 div를 관찰
    }

    return () => {
      if (currentLoader) {
        observer.unobserve(currentLoader) // 컴포넌트가 언마운트될 때 관찰 중지
      }
    }
  }, [loader, hasMore, loadMoreTransactions])

  return (
    <div className="transaction-list p-4">
      {/* 선택한 날짜 출력 */}
      <h2 className="text-base font-semibold text-black mb-4 ml-1">
        {new Date(selectedDate).toLocaleDateString('ko-KR', {
          year: 'numeric',
          month: 'long',
          day: 'numeric',
          weekday: 'long',
        })}
      </h2>

      {dailySalesList.length > 0 ? (
        dailySalesList.map((sale) => (
          <div key={sale.customerName} className="transaction-item mb-4">
            <div className="flex items-center space-x-4 mb-4">
              <div className="bg-blue-500 text-white p-2 rounded-full">
                <FaArrowDown className="text-white text-2xl" />
              </div>
              <div className="flex flex-col">
                <p className="text-blue-500 text-lg font-semibold">
                  {sale.customerName} + {sale.payAmount.toLocaleString()} 원
                </p>
              </div>
            </div>
          </div>
        ))
      ) : (
        <div className="ml-1">해당 날짜에 거래 내역이 없습니다.</div>
      )}
    </div>
  )
}
