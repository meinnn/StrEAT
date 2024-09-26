import Image from 'next/image'
import { TbPencil } from 'react-icons/tb'
import { GoChevronDown, GoChevronUp } from 'react-icons/go'
import { useState } from 'react'
import Link from 'next/link'

export default function StoreMenuItem({
  name,
  optionList,
}: {
  name: string
  optionList: string[]
}) {
  const [isMoreOption, setIsMoreOption] = useState(false)
  const [isSoldOutToggle, setIsSoldOutToggle] = useState(false)

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
        <div className="flex flex-col mb-2 w-full">
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
          <p className="font-semibold">1,000,000,000원</p>
        </div>
        <div className="pb-4">
          {isMoreOption ? (
            <div className="inline-flex flex-col gap-1 items-start">
              <span className="inline-block text-xs text-center border border-primary-500 text-primary-500 py-[2px] px-3 rounded-full font-semibold">
                옵션1
              </span>
              <ul className="flex flex-col gap-1 pl-2 text-xs font-normal">
                {optionList.map((option) => (
                  <li key={option}>{option}</li>
                ))}
              </ul>
            </div>
          ) : (
            <p className="text-xs font-normal text-text">
              옵션 이런거 저런거 외 1개
            </p>
          )}
        </div>
        <div className="flex justify-between">
          <div
            className="flex items-center text-sm"
            onClick={() => setIsSoldOutToggle(!isSoldOutToggle)}
            role="button"
            tabIndex={0}
            aria-pressed={isSoldOutToggle}
            onKeyDown={(e) => {
              if (e.key === 'Enter' || e.key === ' ') {
                setIsSoldOutToggle(!isSoldOutToggle)
              }
            }}
          >
            <span className="mr-1 text-xs">품절 여부</span>
            <div
              className={`relative w-10 h-5 flex items-center ${isSoldOutToggle ? 'bg-primary-500' : 'bg-gray-medium'} rounded-full p-1 cursor-pointer transition-colors duration-300`}
            >
              <span
                className={`w-4 h-4 bg-white rounded-full flex items-center justify-center text-xs font-bold transform ${isSoldOutToggle ? 'translate-x-4' : 'translate-x-0'} transition-transform duration-300`}
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
