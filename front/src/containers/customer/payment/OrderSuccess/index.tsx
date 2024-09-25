import { FaCheck } from 'react-icons/fa6'
import { useRouter } from 'next/navigation'
import { useEffect } from 'react'

export default function OrderSuccess() {
  const router = useRouter()

  useEffect(() => {
    // 1초 후에 /customer로 리다이렉트
    const timer = setTimeout(() => {
      router.push('/customer')
    }, 1000)

    // 컴포넌트가 언마운트될 때 타이머 정리
    return () => clearTimeout(timer)
  }, [])

  return (
    <div className="h-screen z-50 flex flex-col items-center justify-center">
      <div className="size-48 flex items-center justify-center rounded-full bg-primary-50">
        <FaCheck className="text-primary-500" size={112} />
      </div>
      <p className="mt-8 text-3xl font-bold">주문 완료!</p>
    </div>
  )
}
