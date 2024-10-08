'use client'

import { useState, useEffect } from 'react'
import Image from 'next/image'
import { FaCamera, FaChevronDown } from 'react-icons/fa'

const categories = ['붕어빵', '군고구마', '와플', '떡꼬치']

export default function MenuAddDetails({ onDetailsChange, onImageChange }) {
  const [foodName, setFoodName] = useState<string>('')
  const [description, setDescription] = useState<string>('')
  const [price, setPrice] = useState<string>('')
  const [image, setImage] = useState<File | null>(null)
  const [category, setCategory] = useState<string>('카테고리 선택')
  const [isDropdownOpen, setIsDropdownOpen] = useState<boolean>(false)

  useEffect(() => {
    onDetailsChange({
      foodName,
      description,
      price: parseInt(price, 10),
      category,
    })
  }, [foodName, description, price, category, onDetailsChange])

  // 파일 업로드 핸들러
  const handleImageUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      const fileList = Array.from(e.target.files) // FileList를 배열로 변환
      setImage(fileList[0]) // 첫 번째 이미지를 선택
      onImageChange(fileList) // 배열로 전달하여 처리
    }
  }

  // 카테고리 선택 핸들러
  const handleSelectCategory = (category: string) => {
    setCategory(category)
    setIsDropdownOpen(false) // 선택 후 드롭다운 닫기
  }

  return (
    <div className="grid grid-cols-10 gap-4">
      {/* 메뉴 사진 */}
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

      {/* 음식 이름, 요리 설명, 금액 */}
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
            {category}
            <FaChevronDown className="text-gray-500 ml-2" />
          </button>
          {isDropdownOpen && (
            <ul className="absolute w-full bg-white border rounded mt-1 shadow-lg z-10">
              {categories.map((cat, index) => (
                <li
                  key={index}
                  onClick={() => handleSelectCategory(cat)}
                  className="p-2 hover:bg-gray-200 cursor-pointer"
                >
                  {cat}
                </li>
              ))}
            </ul>
          )}
        </div>
      </div>
    </div>
  )
}
