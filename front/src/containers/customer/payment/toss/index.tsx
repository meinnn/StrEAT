'use client'

import { loadTossPayments } from '@tosspayments/tosspayments-sdk'
import { useEffect, useState, useCallback, Suspense } from 'react'
import { useSearchParams } from 'next/navigation'
import { useCart } from '@/contexts/CartContext'

const clientKey =
  process.env.NEXT_PUBLIC_TOSS_CLIENT_KEY ||
  'test_ck_Z61JOxRQVEnQpON5xeMzrW0X9bAq'
const customerKey = 'SfRLtISYsjv6yX7CV2Wuz'

function PaymentCheckoutComponent() {
  const [payment, setPayment] = useState<any>(null)
  const [isPaymentRequested, setIsPaymentRequested] = useState(false) // 결제 요청 상태 관리

  const { cartItems } = useCart() // storeId 제거

  const [selectedPaymentMethod, setSelectedPaymentMethod] = useState<
    string | null
  >(null)

  const selectPaymentMethod = (method: string) => {
    setSelectedPaymentMethod(method)
  }

  const searchParams = useSearchParams() // URL의 쿼리 파라미터 추출
  const orderId = searchParams.get('orderId') // 'orderId' 파라미터를 URL에서 가져옴

  useEffect(() => {
    const fetchPayment = async () => {
      try {
        const tossPayments = await loadTossPayments(clientKey)
        const paymentInstance = tossPayments.payment({
          customerKey,
        })
        setPayment(paymentInstance)
      } catch (error) {
        console.error('Error fetching payment:', error)
      }
    }

    fetchPayment().then()
  }, [])

  // 결제 요청 함수
  const requestPayment = useCallback(async () => {
    if (!payment || isPaymentRequested) {
      // 이미 요청이 발생한 경우 중복 방지
      return
    }

    if (!orderId) {
      console.error('Order ID가 없습니다.')
      return
    }

    const amount = {
      currency: 'KRW',
      value:
        cartItems
          .filter((item) => item.checked)
          .reduce((acc, item) => acc + item.price, 0) ?? 0,
    }

    try {
      await payment.requestPayment({
        method: selectedPaymentMethod || 'CARD',
        amount,
        orderId, // URL에서 받은 orderId 사용
        orderName: `${cartItems[0].name} 외 ${cartItems.length - 1}건`,
        successUrl: `${window.location.origin}/customer/payment/result?status=success`,
        failUrl: `${window.location.origin}/customer/payment/result?status=failure`,
        customerEmail: 'customer123@gmail.com',
        customerName: '김싸피',
        customerMobilePhone: '01012341234',
        card: {
          useEscrow: false,
          flowMode: 'DEFAULT',
          useCardPoint: false,
          useAppCardOnly: false,
        },
      })
      setIsPaymentRequested(true) // 결제 요청 완료 상태로 변경
    } catch (error) {
      console.error('결제 요청 오류:', error)
    }
  }, [cartItems, payment, selectedPaymentMethod, orderId, isPaymentRequested])

  // 컴포넌트가 렌더링될 때 결제 요청 실행
  useEffect(() => {
    if (payment && !isPaymentRequested) {
      requestPayment().then()
    }
  }, [payment, requestPayment, isPaymentRequested])

  return (
    <div>
      <h2>결제 이동 중...</h2>
    </div>
  )
}

export default function PaymentCheckoutPage() {
  return (
    <Suspense fallback={<div>Loading...</div>}>
      <PaymentCheckoutComponent />
    </Suspense>
  )
}
