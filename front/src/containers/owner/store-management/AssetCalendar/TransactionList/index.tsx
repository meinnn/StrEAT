interface Transaction {
  id: number
  date: string
  amount: number
  description: string
  isIncome?: boolean // '입금/출금' 여부를 나타내는 필드
}

const transactions: Transaction[] = [
  {
    id: 1,
    date: '2024.09.01',
    amount: -1500,
    isIncome: false, // 출금
    description: '김민수',
  },
  {
    id: 2,
    date: '2024.09.02',
    amount: 51700,
    isIncome: true, // 입금
    description: '교통요금 | 알뜰교통',
  },
  // 더미 데이터 추가...
]

export default function TransactionList() {
  return (
    <div className="transaction-list">
      {transactions.map((transaction) => (
        <div key={transaction.id} className="transaction-item">
          <p>{transaction.date}</p>
          <p>{transaction.description}</p>
          <p>{transaction.amount.toLocaleString()} 원</p>
        </div>
      ))}
    </div>
  )
}
