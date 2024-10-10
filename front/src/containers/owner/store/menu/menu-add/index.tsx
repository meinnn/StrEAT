'use client'

import { useState } from 'react'
import { FaArrowLeft } from 'react-icons/fa'
import MenuAddDetails from '@/containers/owner/store/menu/menu-add/MenuAddDetails'
import MenuAddOptions from '@/containers/owner/store/menu/menu-add/MenuAddOptions'

interface MenuDetails {
  foodName: string
  description: string
  price: string // price는 string으로 정의
  categoryId: number
}

interface Option {
  name: string
  price: string
}

interface OptionGroup {
  name: string
  maxOptions: number
  minOptions: number
  options: Option[]
}

export default function MenuAdd() {
  const [details, setDetails] = useState<MenuDetails | null>(null)
  const [options, setOptions] = useState<OptionGroup[] | null>(null)
  const [images, setImages] = useState<File[]>([]) // 이미지 상태 배열로 설정
  const [loading, setLoading] = useState(false)

  const handleSave = async () => {
    if (!details || !options) {
      console.error('Details 또는 Options가 설정되지 않았습니다.')
      return
    }

    setLoading(true)

    const productInfo = {
      name: details.foodName,
      description: details.description,
      price: parseInt(details.price, 10), // 저장할 때 숫자로 변환
      categoryId: details.categoryId,
      optionCategories:
        options?.map((group) => ({
          name: group.name,
          maxSelect: group.maxOptions,
          minSelect: group.minOptions,
          productOptions: group.options.map((option) => ({
            productOptionName: option.name,
            productOptionPrice: parseInt(option.price, 10), // 옵션 가격도 숫자로 변환
          })),
        })) || [], // 옵션이 없을 경우 빈 배열을 사용
    }

    const formData = new FormData()
    formData.append('productInfo', JSON.stringify(productInfo))
    images.forEach((image) => {
      formData.append('images', image)
    })

    try {
      const response = await fetch('/services/menu/menu-add', {
        method: 'POST',
        body: formData,
      })

      if (response.ok) {
        window.location.href = '/owner/store'
      } else {
        const errorData = await response.json()
        console.error('저장 실패:', errorData.message)
      }
    } catch (error) {
      console.error('저장 실패:', error)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div>
      <div className="max-w-xl mx-auto flex flex-col">
        <div className="relative mb-6 mt-4 w-full flex items-center">
          <button
            type="button"
            className="text-xl text-primary-950 absolute left-4"
          >
            <FaArrowLeft />
          </button>
          <h1 className="text-2xl font-bold text-primary-950 mx-auto">
            메뉴 추가
          </h1>
        </div>

        <div className="w-full">
          <MenuAddDetails
            onDetailsChange={setDetails}
            onImageChange={setImages}
          />
        </div>

        <div className="w-full">
          <MenuAddOptions onOptionsChange={setOptions} />
        </div>

        <div className="mt-2 flex justify-center mb-6 px-4">
          <button
            onClick={handleSave}
            className="bg-primary-500 text-white font-bold py-2 rounded-md w-full"
          >
            {loading ? '저장 중...' : '저장하기'}
          </button>
        </div>
      </div>
    </div>
  )
}
