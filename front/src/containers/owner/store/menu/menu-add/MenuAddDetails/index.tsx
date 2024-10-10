'use client'

import { useState, useEffect } from 'react'
import Image from 'next/image'
import { FaCamera, FaChevronDown } from 'react-icons/fa'

interface MenuAddDetailsProps {
  onDetailsChange: (details: {
    foodName: string
    description: string
    price: string // price를 string으로 수정
    categoryId: number
  }) => void
  onImageChange: (fileList: File[]) => void
}

export default function MenuAddDetails({
  onDetailsChange,
  onImageChange,
}: MenuAddDetailsProps) {
  const [foodName, setFoodName] = useState<string>('')
  const [description, setDescription] = useState<string>('')
  const [price, setPrice] = useState<string>('') // price는 string으로 관리
  const [image, setImage] = useState<File | null>(null)
  const [category, setCategory] = useState<{ id: number; name: string }>({
    id: 0,
    name: '카테고리 선택',
  })
  const [categories, setCategories] = useState<{ id: number; name: string }[]>(
    []
  )
  const [isDropdownOpen, setIsDropdownOpen] = useState<boolean>(false)

  useEffect(() => {
    onDetailsChange({
      foodName,
      description,
      price, // price는 string으로 전달
      categoryId: category.id,
    })
  }, [foodName, description, price, category, onDetailsChange])

  const fetchCategories = async () => {
    try {
      const response = await fetch('/services/menu/category')
      if (response.ok) {
        const data = await response.json()
        setCategories(data.data)
      } else {
        console.error('카테고리 데이터를 가져오지 못했습니다.')
      }
    } catch (error) {
      console.error('API 호출 실패:', error)
    }
  }

  useEffect(() => {
    fetchCategories()
  }, [])

  const handleImageUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      const fileList = Array.from(e.target.files)
      setImage(fileList[0])
      onImageChange(fileList)
    }
  }

  const handleSelectCategory = (selectedCategory: {
    id: number
    name: string
  }) => {
    setCategory(selectedCategory)
    setIsDropdownOpen(false)
  }

  return (
    <div className="grid grid-cols-10 gap-4">
      <div className="col-span-6 rounded-lg pl-2">
        <label
          htmlFor="menu-image"
          className="block cursor-pointer text-center rounded-lg"
        >
          {image ? (
            <Image
              src={URL.createObjectURL(image)}
              alt="메뉴 사진"
              width={331}
              height={256}
              className="w-full h-80 rounded-lg"
            />
          ) : (
            <div className="bg-gray-200 rounded-lg w-full h-80 flex flex-col justify-center items-center text-gray-500">
              <FaCamera /> <br />
              메뉴 사진
            </div>
          )}
        </label>
        <input
          id="menu-image"
          type="file"
          accept="image/*"
          onChange={handleImageUpload}
          className="hidden"
        />
      </div>

      <div className="col-span-4 pr-2">
        <div className="mb-1">
          <h3 className="block font-bold text-black">음식 이름</h3>
          <input
            type="text"
            value={foodName}
            onChange={(e) => setFoodName(e.target.value)}
            className="w-full p-2 border bg-gray-200 rounded"
            placeholder="음식 이름 입력"
          />
        </div>
        <div className="mb-1">
          <h3 className="block font-bold text-black">요리 설명</h3>
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            className="w-full p-2 border bg-gray-200 rounded h-20 resize-none"
            placeholder="요리 설명 입력"
          />
        </div>
        <div className="mb-1">
          <h3 className="block font-bold text-black">금액</h3>
          <input
            type="text"
            value={price}
            onChange={(e) => setPrice(e.target.value)}
            className="w-full p-2 border bg-gray-200 rounded"
            placeholder="금액 입력"
          />
        </div>
        <div className="mb-1 relative">
          <h3 className="block font-bold text-black">음식 카테고리</h3>
          <button
            className="w-full p-2 border bg-gray-200 rounded flex justify-between items-center"
            onClick={() => setIsDropdownOpen(!isDropdownOpen)}
          >
            {category.name}
            <FaChevronDown className="text-gray-500 ml-2" />
          </button>
          {isDropdownOpen && (
            <ul className="absolute w-full bg-white border rounded mt-1 shadow-lg z-10 max-h-40 overflow-y-auto">
              {categories.map((cat) => (
                <li
                  key={cat.id}
                  onClick={() => handleSelectCategory(cat)}
                  className="p-2 hover:bg-gray-200 cursor-pointer"
                >
                  {cat.name}
                </li>
              ))}
            </ul>
          )}
        </div>
      </div>
    </div>
  )
}
