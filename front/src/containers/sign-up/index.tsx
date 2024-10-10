'use client'

import { useState } from 'react'
import { PiChefHat, PiForkKnife } from 'react-icons/pi'
import { LuMoveRight } from 'react-icons/lu'
import { useRouter } from 'next/navigation'

export default function SignUp() {
  const [selectedRole, setSelectedRole] = useState<
    'owners' | 'customers' | null
  >(null)
  const router = useRouter()

  const handleSignUp = async () => {
    const response = await fetch('/services/users/sign-up', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ userType: selectedRole }), // userType을 요청 본문에 포함
    })

    const data = await response.json()
    if (response.ok) {
      if (selectedRole === 'customers') router.push('/customers')
      else router.push('/owner')
    }

    console.error(data.message)
  }

  return (
    <div className="h-screen flex flex-col items-center justify-center space-y-10">
      <h1 className="text-5xl font-bold">StrEAT</h1>
      <p className="font-xl font-medium">어떤 회원으로 가입할까요?</p>
      <div className="flex space-x-4">
        {/* 사장님 버튼 */}
        <button
          type="button"
          className={`border size-40 rounded-lg flex flex-col space-y-2 items-center justify-center transition-colors ${
            selectedRole === 'owners'
              ? 'bg-primary-500 text-white'
              : 'border-primary-500 text-primary-500'
          }`}
          onClick={() => setSelectedRole('owners')}
        >
          <PiChefHat size={48} />
          <p>사장님</p>
        </button>

        {/* 손님 버튼 */}
        <button
          type="button"
          className={`border size-40 rounded-lg flex flex-col space-y-2 items-center justify-center transition-colors ${
            selectedRole === 'customers'
              ? 'bg-primary-500 text-white'
              : 'border-primary-500 text-primary-500'
          }`}
          onClick={() => setSelectedRole('customers')}
        >
          <PiForkKnife size={48} />
          <p>손님</p>
        </button>
      </div>
      <button type="button" onClick={handleSignUp}>
        <LuMoveRight
          size={48}
          className={`fixed bottom-6 right-6 transition-colors ${
            selectedRole ? 'text-primary-950' : 'text-gray-medium'
          }`}
        />
      </button>
    </div>
  )
}
