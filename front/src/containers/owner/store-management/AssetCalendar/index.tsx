'use client'

import { useState, useEffect, useCallback } from 'react'
import SingleDatePicker from '@/containers/owner/store-management/AssetCalendar/SingleDatePicker'
import TransactionList from '@/containers/owner/store-management/AssetCalendar/TransactionList'

// 거래 데이터 타입 정의
interface DailySales {
  customerName: string
  payAmount: number
}

interface Transaction {
  [x: string]: any
  dailySalesList: DailySales[]
  dailyTotalPayAmount: number
}

// 현재 날짜를 YYYY-MM-DD 형식으로 반환하는 함수
const getCurrentDate = (): string => {
  const today = new Date()
  const year = today.getFullYear()
  const month = String(today.getMonth() + 1).padStart(2, '0') // 월은 0부터 시작하므로 +1
  const day = String(today.getDate()).padStart(2, '0') // 일자도 2자리로 맞춤
  return `${year}-${month}-${day}`
}

// 쿠키에서 토큰을 가져오는 함수
const getTokenFromCookies = (): string | null => {
  const cookieValue = document.cookie
    .split('; ')
    .find((row) => row.startsWith('accessToken='))
  return cookieValue ? cookieValue.split('=')[1] : null
}

// 거래 데이터를 서버에서 불러오는 함수
const fetchTransactions = async (
  month: string,
  authToken: string // 'token' 대신 'authToken'으로 변경
): Promise<{ [key: string]: Transaction }> => {
  // 객체로 반환 타입 수정
  console.log('Fetching transactions for month:', month) // 콘솔 로그 추가
  try {
    const response = await fetch(`/services/calendar?month=${month}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${authToken}`, // 토큰을 함께 보내는 코드
      },
    })

    if (!response.ok) {
      throw new Error('Failed to fetch transactions')
    }

    const data = await response.json()
    console.log('Fetched transactions:', data)

    // 데이터를 날짜를 key로 한 객체로 변환
    return (
      data?.data?.reduce(
        (acc: { [key: string]: Transaction }, transaction: Transaction) => {
          const dateKey = transaction.someDateField // 적절한 날짜 필드를 키로 설정 (적절한 필드로 대체)
          acc[dateKey] = transaction
          return acc
        },
        {}
      ) || {}
    ) // 빈 객체를 반환
  } catch (error) {
    console.error('Error fetching transactions:', error)
    return {}
  }
}

export default function AssetCalendar() {
  const [selectedDate, setSelectedDate] = useState<string>(getCurrentDate()) // 현재 날짜를 기본값으로 설정
  const [transactions, setTransactions] = useState<
    { [key: string]: Transaction } | undefined
  >(undefined) // 거래 데이터 상태를 객체로 수정
  const [cachedMonth, setCachedMonth] = useState<string | null>(null) // 현재 캐시된 월 상태
  const [token, setToken] = useState<string | null>(null) // 토큰 상태 추가
  const [loading, setLoading] = useState<boolean>(false) // 로딩 상태 추가

  // 쿠키에서 토큰 가져오기
  useEffect(() => {
    const tokenFromCookies = getTokenFromCookies() // 'token' 대신 'tokenFromCookies'로 변경
    if (tokenFromCookies) {
      console.log('Token found:', tokenFromCookies) // 토큰을 찾았는지 로그 확인
      setToken(tokenFromCookies) // 토큰을 상태에 저장
    } else {
      console.error('Token not found in cookies') // 토큰을 찾지 못했을 때 오류 로그
    }
  }, [])

  // API 요청 함수
  const loadTransactionsForMonth = useCallback(
    async (month: string, authToken: string) => {
      if (!authToken) {
        console.error('No token available for fetching transactions') // 토큰이 없을 때
        return // 토큰이 없으면 요청을 보내지 않음
      }

      if (month === cachedMonth) {
        console.log(`Transactions for month ${month} are already cached.`)
        return // 이미 같은 달의 데이터를 캐시했으면 다시 요청하지 않음
      }

      setLoading(true) // 로딩 시작
      const newTransactions = await fetchTransactions(month, authToken) // 'authToken' 사용
      setTransactions(newTransactions) // 불러온 데이터로 상태 업데이트
      setCachedMonth(month) // 캐시된 월 업데이트
      setLoading(false) // 로딩 끝
    },
    [cachedMonth] // cachedMonth가 변경될 때만 함수가 재생성됨
  )

  // token 상태가 존재할 때만 API 호출
  useEffect(() => {
    if (!token) return // 토큰이 없으면 API 호출하지 않음

    const selectedMonth = selectedDate.split('-')[1] // 월을 추출
    console.log('Selected month:', selectedMonth) // 선택된 월 확인
    loadTransactionsForMonth(selectedMonth, token) // token이 설정된 후에만 호출
  }, [token, selectedDate, loadTransactionsForMonth]) // loadTransactionsForMonth 추가

  // 객체의 값들을 배열로 변환하여 SingleDatePicker에 전달
  const transactionsArray = transactions ? Object.values(transactions) : []

  return (
    <div>
      {loading && <p>Loading...</p>} {/* 로딩 중일 때 표시 */}
      {/* 거래 데이터를 SingleDatePicker로 전달 */}
      <SingleDatePicker
        onChange={(date: string) => setSelectedDate(date)} // 올바르게 onChange 전달
        transactions={transactionsArray} // 객체를 배열로 변환하여 전달
      />
      {/* 선택한 날짜의 거래 내역을 TransactionList로 전달 */}
      <TransactionList
        transactions={transactions}
        selectedDate={selectedDate}
        loadMoreTransactions={() => {}} // 더 많은 데이터를 불러오는 함수 (여기서는 사용 안 함)
        hasMore={false} // 더 불러올 데이터가 없는지 여부 전달 (여기서는 false로 처리)
      />
    </div>
  )
}
