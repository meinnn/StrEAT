'use client'

import { loadTossPayments } from '@tosspayments/tosspayments-sdk'
import { useEffect, useState } from 'react'

const clientKey =
  process.env.NEXT_PUBLIC_TOSS_CLIENT_KEY ||
  'test_ck_Z61JOxRQVEnQpON5xeMzrW0X9bAq'
const customerKey = 'SfRLtISYsjv6yX7CV2Wuz'

export default function PaymentCheckoutPage() {
  const [payment, setPayment] = useState<any>(null)
  const [amount] = useState({
    currency: 'KRW',
    value: 5000000000,
  })

  const [selectedPaymentMethod, setSelectedPaymentMethod] = useState<
    string | null
  >(null)

  const selectPaymentMethod = (method: string) => {
    setSelectedPaymentMethod(method)
  }

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

    fetchPayment()
  }, [])

  const requestPayment = async () => {
    if (!payment) {
      console.error('Payment is not initialized')
      return
    }

    try {
      await payment.requestPayment({
        method: selectedPaymentMethod || 'CARD',
        amount: amount, // amount는 숫자형 값으로 전달
        orderId: 'YVyBWblH0boLvfy5NgZJU',
        orderName: '토스 티셔츠 외 2건',
        successUrl: `${window.location.origin}/ordersuccess`,
        failUrl: `${window.location.origin}/orderfailure`,
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
  }

  return (
    <div>
      <h2>아래 결제하기 버튼 누르쇼</h2>
      <button
        type="button"
        className="button bg-primary-400 text-white"
        onClick={requestPayment}
      >
        결제하기
      </button>
    </div>
  )
}
