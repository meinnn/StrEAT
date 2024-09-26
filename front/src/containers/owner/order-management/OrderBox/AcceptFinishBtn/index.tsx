import { useState } from 'react'

interface AcceptFinishBtnProps {
  onAccept: () => void // 부모로부터 전달받을 함수
}

export default function AcceptFinishBtn({ onAccept }: AcceptFinishBtnProps) {
  const [isAccepted, setIsAccepted] = useState(false)

  const handleClick = () => {
    setIsAccepted(true)
    onAccept() // 부모에게 상태 변경을 알림
  }

  return (
    <button
      onClick={handleClick}
      className={`px-2 py-2 font-bold rounded-md border-2 h-20 w-14 text-sm ${
        isAccepted
          ? 'bg-white text-blue-500 border-blue-500'
          : 'bg-white text-red-600 border-red-600'
      }`}
    >
      {isAccepted ? '완료 처리하기' : '주문 수락'}
    </button>
  )
}
