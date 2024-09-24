'use client'

import Image from 'next/image'
import { useState } from 'react'
import { GoPlus } from 'react-icons/go'
import AppBar from '@/components/AppBar'

export default function OwnerStoreSettingBusinessLocation() {
  const [checkedItems, setCheckedItems] = useState(new Array(8).fill(false))
  const checkedCount = checkedItems.filter(Boolean).length

  const handleCheckboxChange = (index: number) => {
    const updatedCheckedItems = [...checkedItems]
    updatedCheckedItems[index] = !updatedCheckedItems[index]
    setCheckedItems(updatedCheckedItems)
  }

  return (
    <section>
      <AppBar title="내 점포" />
      <main className="flex flex-col gap-3 px-6 py-5">
        <h3 className="pl-2 text-xl font-medium ">영업 위치</h3>
        <div className="flex justify-center items-start w-full">
          <div className="grid grid-cols-3 grid-rows-2 gap-4 place-items-center w-full pb-36">
            {new Array(8).fill(0).map((_, index) => {
              return (
                <div
                  key={_}
                  className="relative w-full aspect-square max-w-[114px] overflow-hidden bg-gray-medium rounded-lg"
                >
                  <input
                    type="checkbox"
                    checked={checkedItems[index]}
                    onChange={() => handleCheckboxChange(index)}
                    className="cursor-pointer accent-primary-500 bg-white border-gray-dark w-6 h-6 absolute z-10 rounded-md top-1 left-1"
                  />
                  <Image
                    src="/images/보쌈사진.jpg"
                    alt="store-location"
                    fill
                    className="absolute object-cover"
                    priority
                  />
                </div>
              )
            })}
            <button className="flex justify-center items-center w-full aspect-square max-w-[114px] bg-gray-medium rounded-lg">
              <GoPlus className="text-gray-dark w-5 h-5" />
            </button>
          </div>
          <div className="flex gap-2 self-center p-4 fixed bottom-0 w-full bg-white z-10">
            {checkedCount >= 1 &&
              (checkedCount > 1 ? (
                <button className="w-full py-4 bg-primary-500 text-secondary-light rounded-lg font-normal">
                  삭제하기
                </button>
              ) : (
                <>
                  <button className="w-full py-4 outline outline-primary-500 text-primary-500 rounded-lg font-normal">
                    수정하기
                  </button>
                  <button className="w-full py-4  bg-primary-500 text-secondary-light rounded-lg font-normal">
                    삭제하기
                  </button>
                </>
              ))}
          </div>
        </div>
      </main>
    </section>
  )
}
