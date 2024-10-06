'use client'

import { useState } from 'react'
import SingleDatePicker from '@/containers/owner/store-management/AssetCalendar/SingleDatePicker'
import TransactionList from '@/containers/owner/store-management/AssetCalendar/TransactionList'

// 초기 거래 데이터
const initialTransactions = [
  {
    id: 1,
    date: '2024-10-01',
    income: 150000,
    outcome: 160000,
    description: '김민수',
  },
  {
    id: 2,
    date: '2024-10-02',
    income: 150000,
    outcome: 160000,
    description: '김민수',
  },
  {
    id: 3,
    date: '2024-10-03',
    income: 170000,
    outcome: 10000,
    description: '김민수',
  },
  {
    id: 4,
    date: '2024-10-03',
    income: 170000,
    outcome: 10000,
    description: '김민수',
  },
  {
    id: 5,
    date: '2024-10-03',
    income: 170000,
    outcome: 10000,
    description: '김민수',
  },
  {
    id: 6,
    date: '2024-10-03',
    income: 170000,
    outcome: 10000,
    description: '김민수',
  },
  // 추가 거래 데이터...
]

export default function AssetCalendar() {
  const [selectedDate, setSelectedDate] = useState<string>('2024-09-01')
  const [transactions, setTransactions] = useState(initialTransactions) // 거래 데이터 상태
  const [hasMore, setHasMore] = useState(true) // 더 많은 데이터를 불러올 수 있는지 여부

  // 더 많은 거래 내역을 불러오는 함수
  const loadMoreTransactions = () => {
    // API 호출을 통해 새로운 데이터를 불러온다고 가정
    const newTransactions = [
      {
        id: transactions.length + 1,
        date: '2024-10-04',
        income: 200000,
        outcome: 50000,
        description: '박영수',
      },
      {
        id: transactions.length + 2,
        date: '2024-10-05',
        income: 220000,
        outcome: 30000,
        description: '이순신',
      },
      // 더 많은 데이터...
    ]

    // 불러온 데이터가 없으면 hasMore를 false로 설정
    if (newTransactions.length === 0) {
      setHasMore(false)
    } else {
      // 새로운 데이터를 기존 데이터에 추가
      setTransactions((prev) => [...prev, ...newTransactions])
    }
  }

  return (
    <div>
      {/* 거래 데이터를 SingleDatePicker로 전달 */}
      <SingleDatePicker
        onChange={setSelectedDate}
        transactions={transactions}
      />
      {/* 선택한 날짜의 거래 내역을 TransactionList로 전달 */}
      <TransactionList
        transactions={transactions}
        selectedDate={selectedDate}
        loadMoreTransactions={loadMoreTransactions} // 더 많은 데이터를 불러오는 함수 전달
        hasMore={hasMore} // 더 불러올 데이터가 있는지 여부 전달
      />
    </div>
  )
}
