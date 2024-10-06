import { useEffect, useRef } from 'react'
import { FaArrowDown, FaArrowUp } from 'react-icons/fa'

interface Transaction {
  id: number
  date: string
  income?: number // 입금 필드
  outcome?: number // 출금 필드
  description?: string // 거래 설명 필드
}

interface TransactionListProps {
  transactions: Transaction[] | undefined // undefined 대비
  selectedDate: string
  loadMoreTransactions: () => void // 더 많은 거래 내역을 불러오는 함수
  hasMore: boolean // 더 불러올 데이터가 있는지 여부
}

export default function TransactionList({
  transactions = [],
  selectedDate,
  loadMoreTransactions,
  hasMore,
}: TransactionListProps) {
  const loader = useRef<HTMLDivElement | null>(null) // IntersectionObserver 감지용 ref

  // 선택한 날짜에 맞는 거래 내역 필터링
  const filteredTransactions = transactions.filter(
    (transaction) =>
      new Date(transaction.date).toLocaleDateString('ko-KR') ===
      new Date(selectedDate).toLocaleDateString('ko-KR')
  )

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

    if (loader.current) {
      observer.observe(loader.current) // 로더 div를 관찰
    }

    return () => {
      if (loader.current) {
        observer.unobserve(loader.current) // 컴포넌트가 언마운트될 때 관찰 중지
      }
    }
  }, [loader, hasMore, loadMoreTransactions])

  return (
    <div className="transaction-list p-4">
      {/* 선택한 날짜 출력 */}
      <h2 className="text-base font-semibold text-black mb-2">
        {new Date(selectedDate).toLocaleDateString('ko-KR', {
          year: 'numeric',
          month: 'long',
          day: 'numeric',
          weekday: 'long',
        })}
      </h2>

      {filteredTransactions.length > 0 ? (
        filteredTransactions.map((transaction) => (
          <div key={transaction.id} className="transaction-item mb-4">
            {/* 입출금 여부에 따른 아이콘 및 금액을 순차적으로 표시 */}
            {transaction.income !== undefined && (
              <div className="flex items-center space-x-4 mb-4">
                <div className="bg-blue-500 text-white p-2 rounded-full">
                  <FaArrowDown className="text-white text-2xl" />
                </div>
                <div className="flex flex-col">
                  <p className="text-blue-500 text-lg font-semibold">
                    +{transaction.income.toLocaleString()} 원
                  </p>
                  <p className="text-gray-500 text-sm">
                    {transaction.description || '거래 내역 없음'}
                  </p>
                </div>
              </div>
            )}

            {transaction.outcome !== undefined && (
              <div className="flex items-center space-x-4 mb-2">
                <div className="bg-red-500 text-white p-2 rounded-full">
                  <FaArrowUp className="text-white text-2xl" />
                </div>
                <div className="flex flex-col">
                  <p className="text-red-500 text-lg font-semibold">
                    -{transaction.outcome.toLocaleString()} 원
                  </p>
                  <p className="text-gray-500 text-sm">
                    {transaction.description || '거래 내역 없음'}
                  </p>
                </div>
              </div>
            )}
          </div>
        ))
      ) : (
        <p>해당 날짜에 거래 내역이 없습니다.</p>
      )}
    </div>
  )
}
