'use client'

import { loadTossPayments } from '@tosspayments/tosspayments-sdk'
import { useEffect, useState, useCallback } from 'react'
import { useCart } from '@/contexts/CartContext'
import { useSearchParams } from 'next/navigation' // URL 쿼리 파라미터를 가져오기 위한 훅

const clientKey =
  process.env.NEXT_PUBLIC_TOSS_CLIENT_KEY ||
  'test_ck_Z61JOxRQVEnQpON5xeMzrW0X9bAq'
const customerKey = 'SfRLtISYsjv6yX7CV2Wuz'

export default function PaymentCheckoutPage() {
  const [payment, setPayment] = useState<any>(null)

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
    if (!payment) {
      console.error('Payment is not initialized')
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
    } catch (error) {
      console.error('Payment error:', error)
    }
  }, [cartItems, payment, selectedPaymentMethod, orderId])

  // 컴포넌트가 렌더링될 때 결제 요청 실행
  useEffect(() => {
    if (payment) {
      requestPayment().then()
    }
  }, [payment, requestPayment])

  return (
    <div>
      <h2>결제 이동 중...</h2>
    </div>
  )
}
