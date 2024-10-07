interface QuantityControlProps {
  quantity: number
  setQuantity: (value: (prev: number) => number) => void
  type: 'default' | 'change'
}

export default function QuantityControl({
  quantity,
  setQuantity,
  type,
}: QuantityControlProps) {
  const handleQuantityChange = (amount: number) => {
    setQuantity((prev) => Math.max(1, prev + amount))
  }

  return (
    <div
      className={`${type === 'change' ? 'border border-gray-medium rounded-lg py-4 px-5 m-2' : 'p-6'}`}
    >
      <div className="flex justify-between items-center text-lg font-bold">
        <h2>수량</h2>
        <div className="flex items-center">
          <button
            type="button"
            className={`flex justify-center items-center rounded-full size-7 ${
              quantity === 1
                ? 'bg-gray-medium text-gray-dark'
                : 'border border-primary-500 text-primary-500 hover:bg-primary-500 hover:text-white active:bg-primary-600'
            }`}
            onClick={() => handleQuantityChange(-1)}
            disabled={quantity === 1}
          >
            -
          </button>
          <span className="mx-4 text-xl">{quantity}</span>
          <button
            type="button"
            className="flex justify-center items-center border border-primary-500 text-primary-500 hover:bg-primary-500 hover:text-white active:bg-primary-600 rounded-full size-7"
            onClick={() => handleQuantityChange(1)}
          >
            +
          </button>
        </div>
      </div>
    </div>
  )
}
