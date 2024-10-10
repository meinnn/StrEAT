import React, { useState } from 'react'
import { HiArrowLongRight } from 'react-icons/hi2'
import { useForm } from 'react-hook-form'
import { useRouter } from 'next/navigation'
import { useStoreReigstInfo } from '@/contexts/storeRegistContext'

export default function BusinessRegistrationNumber() {
  const router = useRouter()
  const [isCorrect, setIsCorrect] = useState<boolean | null>(null)
  const { register, handleSubmit, watch } = useForm()
  const { storeRegistInfo, setStoreRegistInfo } = useStoreReigstInfo()

  const businessRegistrationNumber = watch('businessRegistrationNumber')

  const handleClickConfirmNumberBtn = async (number: string) => {
    const res = await fetch(`/services/store/business-registration-number`, {
      method: 'POST',
      body: JSON.stringify({
        b_no: number,
      }),
    })

    const data = await res.json()

    if (data.status_code === 'OK') {
      if (
        data?.data[0]?.tax_type ===
        '국세청에 등록되지 않은 사업자등록번호입니다.'
      ) {
        setIsCorrect(false)
      } else {
        setIsCorrect(true)
        setStoreRegistInfo({
          ...storeRegistInfo,
          businessRegistrationNumber,
        })
        router.push('/owner/store/regist')
      }
    } else {
      console.error('사업자등록번호 확인에 실패했습니다.')
    }
  }

  const onSubmit = (data: { businessRegistrationNumber?: string }) => {
    if (!data.businessRegistrationNumber) return
    handleClickConfirmNumberBtn(data.businessRegistrationNumber)
  }

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
          onClick={() => {
            if (
              !businessRegistrationNumber ||
              businessRegistrationNumber.length < 10
            )
              return
            handleClickConfirmNumberBtn(businessRegistrationNumber)
          }}
          className={`${businessRegistrationNumber && businessRegistrationNumber.length > 0 ? 'text-primary-400 cursor-pointer' : 'text-gray-dark'} absolute bottom-8 right-6 h-7 w-7`}
        />
      </div>
    </main>
  )
}
