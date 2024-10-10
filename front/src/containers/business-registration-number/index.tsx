import React, { useState } from 'react'
import { HiArrowLongRight } from 'react-icons/hi2'
import { useForm } from 'react-hook-form'

export default function BusinessRegistrationNumber() {
  const [isCorrect] = useState<boolean | null>(null)
  const { register, handleSubmit, watch } = useForm()

  const onSubmit = (data: { businessRegistrationNumber?: string }) => {}

  const businessRegistrationNumber = watch('businessRegistrationNumber')

  return (
    <main className="bg-slate-200 h-screen">
      <div className="relative pt-32 flex flex-col gap-16 font-medium mx-auto px-8 max-w-xl w-full bg-white h-full">
        <h2 className="text-2xl text-primary-950 font-semibold">
          사업자등록번호를 입력해주세요
        </h2>
        <div className="flex flex-col gap-1">
          <h5 className="font-medium text-text">사업자등록번호</h5>
          <form
            className="flex flex-col gap-3"
            onSubmit={handleSubmit(onSubmit)}
          >
            <input
              // eslint-disable-next-line react/jsx-props-no-spreading
              {...register('businessRegistrationNumber', {
                required: true,
                maxLength: {
                  value: 10,
                  message: '10글자를 입력해주세요',
                },
              })}
              type="text"
              className="border-b-2 outline-none border-primary-400 w-full pt-1 pb-2 text-2xl font-semibold text-primary-950"
            />
            {isCorrect === false ? (
              <p className="text-xs text-primary-400">
                등록되지 않은 사업자등록번호입니다. 다시 입력해주세요.
              </p>
            ) : null}
          </form>
        </div>
        <HiArrowLongRight
          className={`${businessRegistrationNumber && businessRegistrationNumber.length > 0 ? 'text-primary-400 cursor-pointer' : 'text-gray-dark'} absolute bottom-8 right-6 h-7 w-7`}
        />
      </div>
    </main>
  )
}
