import React from 'react'

type OrderStatus =
  | 'REJECTED'
  | 'WAITING_FOR_PROCESSING'
  | 'PROCESSING'
  | 'WAITING_FOR_RECEIPT'
  | 'RECEIVED'

interface WaitingStatus {
  team: number
  menu: number
}

// 각 OrderStatus에 해당하는 단계 인덱스 매핑
const STATUS_STEPS_MAP: { [key in OrderStatus]: number } = {
  REJECTED: 0,
  WAITING_FOR_PROCESSING: 1,
  PROCESSING: 2,
  WAITING_FOR_RECEIPT: 3,
  RECEIVED: 4,
}

const STEPS = [
  { id: 1, label: '주문 요청' },
  { id: 2, label: '주문 확인' },
  { id: 3, label: '픽업 대기' },
  { id: 4, label: '픽업 완료' },
]

export default function OrderProgress({
  orderStatus,
  waitingStatus,
}: {
  orderStatus: OrderStatus
  waitingStatus: WaitingStatus
}) {
  // orderStatus에 따라 각 step의 status 결정
  const currentStepId = STATUS_STEPS_MAP[orderStatus]

  const stepsWithStatus = STEPS.map((step) => {
    if (step.id < currentStepId) {
      return { ...step, status: 'COMPLETED' }
    }
    if (step.id === currentStepId) {
      return { ...step, status: 'IN_PROGRESS' }
    }
    return { ...step, status: 'PENDING' }
  })

  const getStepStyle = (status: string) => {
    if (status === 'COMPLETED') {
      return 'border-2 border-primary-300 bg-white text-primary-500'
    }
    if (status === 'IN_PROGRESS') {
      return 'bg-primary-500 text-white'
    }
    return 'border-2 border-gray-medium bg-white text-gray-dark'
  }

  const getLabelStyle = (status: string) => {
    if (status === 'COMPLETED') {
      return 'text-primary-500'
    }
    if (status === 'IN_PROGRESS') {
      return 'text-primary-500 font-bold'
    }
    return 'text-gray-dark'
  }

  const renderStatusMessage = () => {
    switch (currentStepId) {
      case 0:
        return '' // 아무것도 출력하지 않음
      case 1:
        return (
          <p className="font-medium text-center mt-7">
            주문 수락을 기다리고 있어요...
          </p>
        )
      case 2:
        return (
          <>
            앞 {waitingStatus.team}개 팀,{' '}
            <span className="text-primary-500 font-semibold">
              메뉴 {waitingStatus.menu}개
            </span>
            가 조리 중이에요
          </>
        )
      case 3:
        return (
          <p className="font-medium text-center mt-7">
            가게에서 메뉴를 픽업해 주세요!
          </p>
        )
      case 4:
        return '' // 아무것도 출력하지 않음
      default:
        return ''
    }
  }

  return (
    <div className="py-8">
      {/* 진행 상태 표시 */}
      <div className="flex justify-around items-center">
        {stepsWithStatus.map((step) => (
          <div key={step.id} className="flex justify-center z-10">
            {/* 원과 텍스트 */}
            <div className="flex flex-col items-center">
              <div
                className={`w-8 h-8 flex items-center justify-center rounded-full ${getStepStyle(
                  step.status
                )}`}
              >
                {step.id}
              </div>

              <div className={`mt-1 text-sm ${getLabelStyle(step.status)}`}>
                {step.label}
              </div>
            </div>
          </div>
        ))}
      </div>

      <div className="absolute inset-x-0 top-28 flex justify-center">
        {stepsWithStatus.slice(1).map((step, index) => (
          <div key={step.id} className="w-1/4">
            <div
              className={`flex-grow h-0.5 w-full ${
                stepsWithStatus[index + 1].status === 'PENDING'
                  ? 'bg-gray-medium'
                  : 'bg-primary-300'
              }`}
            />
          </div>
        ))}
      </div>

      {/* 대기 및 조리 상태 메시지 */}
      <div className="font-medium text-center mt-7">
        {renderStatusMessage()}
      </div>
    </div>
  )
}
