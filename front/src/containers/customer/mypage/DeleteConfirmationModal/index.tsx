export default function DeleteConfirmationModal({
  isOpen,
  onConfirm,
  onCancel,
}: {
  isOpen: boolean
  onConfirm: () => void
  onCancel: () => void
}) {
  if (!isOpen) return null

  return (
    <div className="inset-0 fixed z-50 w-full flex justify-center items-center bg-[rgba(0,0,0,0.3)] backdrop-blur-sm">
      <div className="bg-secondary-medium rounded-lg w-full top-4 animate-modal-short-slide-up max-w-96 shadow-md flex flex-col items-center p-3 gap-3 pt-5 h-32">
        <p className="font-semibold">리뷰를 삭제하시겠습니까?</p>
        <p className="text-xs text-gray-dark">다시 되돌릴 수 없습니다.</p>
        <div className="flex gap-2 w-full">
          <button
            onClick={onCancel}
            className="bg-gray-medium rounded-md w-full py-2 text-sm"
          >
            취소
          </button>
          <button
            onClick={onConfirm}
            className="bg-primary-500 text-secondary-light rounded-md w-full py-2 text-sm"
          >
            네, 삭제하겠습니다
          </button>
        </div>
      </div>
    </div>
  )
}
