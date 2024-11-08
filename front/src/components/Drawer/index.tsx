import { IoCloseOutline } from 'react-icons/io5'
import { ReactNode, useState } from 'react'

interface DrawerProps {
  title?: string
  onClose: () => void
  children: ReactNode
}

export default function Drawer({ title, onClose, children }: DrawerProps) {
  const [isClosing, setIsClosing] = useState(false)

  const handleClose = () => {
    setIsClosing(true)
    setTimeout(onClose, 300) // 애니메이션 지속 시간 후 모달 닫기
  }

  return (
    <>
      {/* 배경 어둡게 처리 */}
      <div
        role="presentation"
        className={`fixed inset-0 bg-black bg-opacity-50 z-40 transition-opacity duration-300 ${
          isClosing ? 'opacity-0' : 'opacity-100'
        }`}
        onClick={handleClose}
      />

      {/* 슬라이드 인/아웃 모달 */}
      <div
        className={`fixed inset-x-0 bottom-0 z-[200] bg-white rounded-t-2xl shadow-lg overflow-hidden transition-transform duration-300 ${
          isClosing ? 'animate-slide-down' : 'animate-slide-up'
        }`}
        style={{ maxHeight: 'calc(100vh - 6em)' }}
      >
        {/* 상단 타이틀 영역 */}
        <div className="sticky top-0 py-5 px-6 flex items-center justify-between bg-white">
          <h2 className="text-xl font-semibold">{title}</h2>
          <button type="button" className="float-right" onClick={handleClose}>
            <IoCloseOutline size={24} />
          </button>
        </div>

        {/* 스크롤 가능 영역 */}
        <div
          className="overflow-auto px-4"
          style={{ maxHeight: 'calc(100vh - 12em)' }}
        >
          {children}
        </div>
      </div>
    </>
  )
}
