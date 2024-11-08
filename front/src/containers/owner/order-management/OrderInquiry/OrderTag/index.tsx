import { Condition } from '..'

interface OrderTagProps {
  condition: Condition
  setCondition: React.Dispatch<React.SetStateAction<Condition>>
}

const TAGS = [
  {
    id: 'CREDIT_CARD',
    label: '카드',
  },
  {
    id: 'ACCOUNT_TRANSFER',
    label: '계좌이체',
  },
  {
    id: 'SIMPLE_PAYMENT',
    label: '간편결제',
  },
  {
    id: 'CASH',
    label: '현금',
  },
  {
    id: 'RECEIVED',
    label: '수령완료',
  },
  {
    id: 'REJECTED',
    label: '주문거절',
  },
]

export default function OrderTag({ condition, setCondition }: OrderTagProps) {
  const handleTagClick = (tag: string) => {
    if (tag === 'RECEIVED' || tag === 'REJECTED') {
      const nStatusTag = [...condition.statusTag]

      if (nStatusTag.includes(tag)) {
        const index = nStatusTag.indexOf(tag)
        nStatusTag.splice(index, 1)
      } else {
        nStatusTag.push(tag)
      }

      setCondition((pre) => ({ ...pre, statusTag: nStatusTag }))
    } else {
      // 카드, 계좌이체, 간편결제, 현금
      const nPaymentMethodTag = [...condition.paymentMethodTag]

      if (nPaymentMethodTag.includes(tag)) {
        const index = nPaymentMethodTag.indexOf(tag)
        nPaymentMethodTag.splice(index, 1)
      } else {
        nPaymentMethodTag.push(tag)
      }

      setCondition((pre) => ({ ...pre, paymentMethodTag: nPaymentMethodTag }))
    }
  }

  return (
    <div className="flex flex-wrap justify-center gap-2 mt-4 mx-2 mb-4">
      {/* 태그 버튼들 */}
      <div className="grid grid-cols-3 gap-2 w-full">
        {TAGS.map((tag) => (
          <button
            type="button"
            key={tag.id}
            onClick={() => handleTagClick(tag.id)}
            className={`w-full py-2 border-2 rounded-lg ${
              [...condition.statusTag, ...condition.paymentMethodTag].includes(
                tag.id
              )
                ? 'border-primary-400 text-primary-400'
                : 'border-gray-300 text-gray-500'
            } transition-colors duration-300 ease-in-out`}
          >
            {tag.label}
          </button>
        ))}
      </div>
    </div>
  )
}
