import Image from 'next/image'
import { TbPencil } from 'react-icons/tb'
import { GoChevronDown, GoChevronUp } from 'react-icons/go'
import { useState } from 'react'
import Link from 'next/link'
import { useQueryClient } from '@tanstack/react-query'
import { OptionCategory } from '../store-menu'

export default function StoreMenuItem({
  storeId,
  productId,
  name,
  optionList,
  stockStatus = false,
  description = '',
  price,
}: {
  storeId?: number | null
  productId: number
  name: string
  optionList: OptionCategory[]
  stockStatus: boolean
  description: string
  price: number
}) {
  const queryClient = useQueryClient()
  const [isMoreOption, setIsMoreOption] = useState(false)
  const handleClickMenuStockBtn = async () => {
    const response = await fetch(
      `/services/store/${productId}/menu/stock-status`,
      {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization':
            'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhY2Nlc3MtdG9rZW4iLCJpYXQiOjE3Mjc4MzE0MTQsImV4cCI6MjA4NzgzMTQxNCwidXNlcklkIjoxMn0.UrVrI-WUCXdx017R4uRIl6lzxbktVSfEDjEgYe5J8UQ',
        },
      }
    )
    if (!response.ok) {
      console.error('메뉴 품절 여부 수정에 실패했습니다.')
    }
    queryClient.invalidateQueries({ queryKey: ['/store/menu', storeId] })
  }

  return (
    <div className="w-full border-b border-gray-medium flex items-start gap-5 px-7 py-5 last:border-b-0">
      <p className="border border-gray-light flex flex-none relative w-24  aspect-square  overflow-hidden rounded-lg bg-gray-medium">
        <Image
          src="/images/보쌈사진.jpg"
          alt="menu"
          fill
          className="object-cover"
        />
      </p>
      <div className="flex flex-col w-full pt-1">
        <div className="flex flex-col mb-1 w-full">
          <div className="flex justify-between items-center gap-1 w-full">
            <h5 className="font-semibold">{name}</h5>
            <Link
              href="/owner/store/menu/menu-edit"
              className="flex items-center justify-between border cursor-pointer border-primary-500 gap-[2px] rounded-full py-[2px] pl-1 pr-2"
            >
              <TbPencil className="text-primary-500 w-3 h-3" />
              <button type="button" className="text-primary-500 text-xs">
                메뉴 수정
              </button>
            </Link>
          </div>
          <p className="font-semibold">{price.toLocaleString()}원</p>
        </div>
        {description.length > 1 ? (
          <p className="text-sm mb-3">{description}</p>
        ) : null}
        <div className="pb-4">
          {isMoreOption ? (
            <div className="inline-flex flex-col gap-1 items-start">
              {optionList.map((option) => {
                return (
                  <div key={option.id}>
                    <span className="inline-block mb-2 text-xs text-center border border-primary-500 text-primary-500 py-[2px] px-3 rounded-full font-semibold">
                      {option.name}
                    </span>
                    <ul className="flex flex-col gap-1 pl-2 text-xs font-normal">
                      {option.options.map((subOption, index) => {
                        return (
                          <li key={subOption.id}>
                            {`${subOption.productOptionName} (${subOption.productOptionPrice}원)`}
                          </li>
                        )
                      })}
                    </ul>
                  </div>
                )
              })}
            </div>
          ) : (
            <div className="flex gap-2 items-center">
              <span className="inline-block text-xs text-center bg-primary-950 text-secondary-light py-[2px] px-3 rounded-full font-semibold">
                옵션
              </span>
              {optionList.length > 0 ? (
                <p className="text-xs font-normal text-text">
                  {optionList.length > 1
                    ? `${optionList[0].name} 외 ${optionList.length - 1}개`
                    : `${optionList[0].name}`}
                </p>
              ) : null}
            </div>
          )}
        </div>
        <div className="flex justify-between">
          <div
            className="flex items-center text-sm"
            onClick={handleClickMenuStockBtn}
            role="button"
            tabIndex={0}
            aria-pressed={!stockStatus}
            onKeyDown={(e) => {
              if (e.key === 'Enter' || e.key === ' ') {
                handleClickMenuStockBtn()
              }
            }}
          >
            <span className="mr-1 text-xs">품절 여부</span>
            <div
              className={`relative w-10 h-5 flex items-center ${!stockStatus ? 'bg-primary-500' : 'bg-gray-medium'} rounded-full p-1 cursor-pointer transition-colors duration-300`}
            >
              <span
                className={`w-4 h-4 bg-white rounded-full flex items-center justify-center text-xs font-bold transform ${!stockStatus ? 'translate-x-4' : 'translate-x-0'} transition-transform duration-300`}
              />
            </div>
          </div>
          <div className="flex items-center gap-[2px] text-text">
            <div
              className="text-xs font-normal flex items-center cursor-pointer"
              onClick={() => setIsMoreOption(!isMoreOption)}
              onKeyDown={(e) => {
                if (e.key === 'Enter' || e.key === ' ') {
                  setIsMoreOption(!isMoreOption)
                }
              }}
              role="button"
              tabIndex={0}
              aria-pressed={isMoreOption}
            >
              {isMoreOption ? '옵션 닫기' : '옵션 더보기'}
              {isMoreOption ? <GoChevronUp /> : <GoChevronDown />}
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
