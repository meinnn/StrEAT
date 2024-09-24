const STEPS = [
  { id: 1, label: '주문 요청', status: 'completed' }, // 완료 상태
  { id: 2, label: '주문 확인', status: 'in-progress' }, // 진행 중 상태
  { id: 3, label: '픽업 대기', status: 'pending' }, // 미완료 상태
  { id: 4, label: '픽업 완료', status: 'pending' }, // 미완료 상태
]

export default function OrderProgress() {
  const teamsInFront = 4
  const menusBeingPrepared = 5

  return (
    <div className="py-8">
      {/* 진행 상태 표시 */}
      <div className="flex justify-around items-center">
        {STEPS.map((step) => (
          <div key={step.id} className="flex justify-center z-10">
            {/* 원과 텍스트 */}
            <div className="flex flex-col items-center">
              <div
                className={`w-8 h-8 flex items-center justify-center rounded-full ${
                  step.status === 'completed'
                    ? 'border-2 border-primary-300 bg-white text-primary-500'
                    : step.status === 'in-progress'
                      ? 'bg-primary-500 text-white'
                      : 'border-2 border-gray-medium bg-white text-gray-dark'
                }`}
              >
                {step.id}
              </div>

              <p
                key={step.id}
                className={`mt-1 text-sm ${
                  step.status === 'completed'
                    ? 'text-primary-500'
                    : step.status === 'in-progress'
                      ? 'text-primary-500 font-bold'
                      : 'text-gray-400'
                }`}
              >
                {step.label}
              </p>
            </div>
          </div>
        ))}
      </div>

      <div className="absolute inset-x-0 top-28 flex justify-center">
        {STEPS.slice(1).map((step, index) => (
          <div key={step.id} className="w-1/4">
            <div
              className={`flex-grow h-0.5 w-full ${
                STEPS[index + 1].status === 'pending'
                  ? 'bg-gray-medium'
                  : 'bg-primary-300'
              }`}
            />
          </div>
        ))}
      </div>

      {/* 대기 및 조리 상태 메시지 */}
      <p className="font-medium text-center text-gray-600 mt-6">
        앞 {teamsInFront}개 팀,{' '}
        <span className="text-primary-500 font-semibold">
          메뉴 {menusBeingPrepared}개
        </span>
        가 조리 중이에요
      </p>
    </div>
  )
}
