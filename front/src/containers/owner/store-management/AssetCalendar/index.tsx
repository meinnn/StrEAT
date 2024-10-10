'use client'

import { useState, useEffect } from 'react'
import SingleDatePicker from '@/containers/owner/store-management/AssetCalendar/SingleDatePicker'
import TransactionList from '@/containers/owner/store-management/AssetCalendar/TransactionList'

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
  token: string
): Promise<any[]> => {
  console.log('Fetching transactions for month:', month) // 콘솔 로그 추가
  try {
    const response = await fetch(`/services/calendar?month=${month}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`, // 토큰을 함께 보내는 코드
      },
    })

    if (!response.ok) {
      throw new Error('Failed to fetch transactions')
    }

    const data = await response.json()
    console.log('Fetched transactions:', data) // 데이터 응답을 로그로 출력
    return data?.data || [] // 실제 데이터를 매핑
  } catch (error) {
    console.error('Error fetching transactions:', error)
    return []
  }
}

export default function AssetCalendar() {
  const [selectedDate, setSelectedDate] = useState<string>(getCurrentDate()) // 현재 날짜를 기본값으로 설정
  const [transactions, setTransactions] = useState<any[]>([]) // 거래 데이터 상태
  const [cachedMonth, setCachedMonth] = useState<string | null>(null) // 현재 캐시된 월 상태
  const [token, setToken] = useState<string | null>(null) // 토큰 상태 추가
  const [loading, setLoading] = useState<boolean>(false) // 로딩 상태 추가

  useEffect(() => {
    // 쿠키에서 토큰 가져오기
    const token = getTokenFromCookies()
    if (token) {
      console.log('Token found:', token) // 토큰을 찾았는지 로그 확인
      setToken(token) // 토큰을 상태에 저장
    } else {
      console.error('Token not found in cookies') // 토큰을 찾지 못했을 때 오류 로그
    }
  }, [])

  // API 요청 함수
  const loadTransactionsForMonth = async (month: string) => {
    if (!token) {
      console.error('No token available for fetching transactions') // 토큰이 없을 때
      return // 토큰이 없으면 요청을 보내지 않음
    }

    if (month === cachedMonth) {
      console.log(`Transactions for month ${month} are already cached.`)
      return // 이미 같은 달의 데이터를 캐시했으면 다시 요청하지 않음
    }

    setLoading(true) // 로딩 시작
    const newTransactions = await fetchTransactions(month, token)
    setTransactions(newTransactions) // 불러온 데이터로 상태 업데이트
    setCachedMonth(month) // 캐시된 월 업데이트
    setLoading(false) // 로딩 끝
  }

  // 페이지 로드 시와 달 변경 시 API 요청
  useEffect(() => {
    const selectedMonth = selectedDate.split('-')[1] // 월을 추출
    console.log('Selected month:', selectedMonth) // 선택된 월 확인

    if (token) {
      loadTransactionsForMonth(selectedMonth) // 페이지 로드 시 API 호출
    }
  }, [token]) // token이 업데이트되면 바로 API 요청

  // 달 변경 시 API 요청
  useEffect(() => {
    const selectedMonth = selectedDate.split('-')[1] // 월을 추출
    console.log('Selected month:', selectedMonth) // 선택된 월 확인
    loadTransactionsForMonth(selectedMonth).then()
  }, [selectedDate]) // selectedDate가 업데이트될 때도 다시 요청

  return (
    <div>
      {loading && <p>Loading...</p>} {/* 로딩 중일 때 표시 */}
      {/* 거래 데이터를 SingleDatePicker로 전달 */}
      <SingleDatePicker
        onChange={(date: string) => setSelectedDate(date)} // 올바르게 onChange 전달
        transactions={transactions}
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
