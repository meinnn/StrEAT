import { FaCheck } from 'react-icons/fa6'
import { useRouter, useSearchParams } from 'next/navigation'
import { useEffect } from 'react'

export default function OrderSuccess() {
  const router = useRouter()
  const searchParams = useSearchParams() // URL의 쿼리 파라미터를 가져오는 훅

  useEffect(() => {
    // URL에서 쿼리 파라미터 값 가져오기
    const paymentKey = searchParams.get('paymentKey')
    const amount = searchParams.get('amount') // 결제 금액
    const orderId = searchParams.get('orderId')

    // 필수 값이 없으면 실패 페이지로 리다이렉트
    if (!paymentKey || !amount) {
      console.error('필수 결제 정보가 없습니다.')
      router.push('/customer/payment/result?status=failure')
      return
    }

    // 결제 API 호출
    const handlePayment = async () => {
      try {
        const response = await fetch('/services/payments', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({ paymentKey, amount, orderId }),
        })

        if (response.ok) {
          const data = await response.json()
          console.log(data)
          const { responseId } = data.orderId // 응답에서 orderId 가져오기

          // 성공 시 주문 완료 UI를 보여준 후 1초 뒤에 리다이렉트
          const timer = setTimeout(() => {
            router.push(`/customer/orders/${responseId}`)
          }, 1000)

          return () => clearTimeout(timer)
        }

        // 결제 실패 처리
        const errorData = await response.json()
        console.error('결제 실패:', errorData.message)
        // 결제 실패 시 실패 페이지로 리다이렉트
        router.push('/customer/payment/OrderFailure')
      } catch (error) {
        console.error('결제 요청 오류:', error)
        // 오류 시 실패 페이지로 리다이렉트
        router.push('/customer/payment/OrderFailure')
      }

      return undefined // 실패 시에도 undefined를 명시적으로 반환
    }

    // 결제 처리 함수 호출
    handlePayment()
  }, [router, searchParams]) // searchParams 훅을 의존성으로 추가

  return (
    <div className="h-screen z-50 flex flex-col items-center justify-center">
      <div className="size-48 flex items-center justify-center rounded-full bg-primary-50">
        <FaCheck className="text-primary-500" size={112} />
      </div>
      <p className="mt-8 text-3xl font-bold">주문 완료!</p>
    </div>
  )
}
