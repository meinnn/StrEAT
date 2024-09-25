import OrderInfo from '@/containers/owner/order-management/OrderInfo'

interface OrderInfoModalProps {
  onClose: () => void
}

export default function OrderInfoModal({
  onClose,
}: OrderInfoModalProps): JSX.Element {
  return (
    <div className="z-50 fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
      <div className="bg-white p-6 rounded-lg shadow-lg w-full max-w-md">
        {/* 주문 정보 컴포넌트를 import하여 사용 */}
        <OrderInfo />
        <button
          type="button"
          onClick={onClose}
          className="bg-primary-400 text-white py-2 px-4 rounded-lg mt-4"
        >
          닫기
        </button>
      </div>
    </div>
  )
}
