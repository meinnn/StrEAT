'use client'

import { useState, useEffect } from 'react'
import Image from 'next/image'
import { FaCamera, FaChevronDown } from 'react-icons/fa'

export default function MenuAddDetails({ onDetailsChange, onImageChange }) {
  const [foodName, setFoodName] = useState<string>('')
  const [description, setDescription] = useState<string>('')
  const [price, setPrice] = useState<string>('')
  const [image, setImage] = useState<File | null>(null)
  const [category, setCategory] = useState<{ id: number; name: string }>({
    id: 0,
    name: '카테고리 선택',
  })
  const [categories, setCategories] = useState<{ id: number; name: string }[]>(
    []
  ) // 서버로부터 받은 카테고리 리스트
  const [isDropdownOpen, setIsDropdownOpen] = useState<boolean>(false)

  useEffect(() => {
    onDetailsChange({
      foodName,
      description,
      price: parseInt(price, 10),
      categoryId: category.id, // 선택된 카테고리의 id를 부모로 전달
    })
  }, [foodName, description, price, category, onDetailsChange])

  // API 호출로 카테고리 데이터를 가져오는 함수
  const fetchCategories = async () => {
    try {
      const response = await fetch('/services/menu/category') // Next 서버 경로로 GET 요청
      if (response.ok) {
        const data = await response.json()
        setCategories(data.data) // 카테고리 이름과 id를 배열로 변환하여 상태에 저장
      } else {
        console.error('카테고리 데이터를 가져오지 못했습니다.')
      }
    } catch (error) {
      console.error('API 호출 실패:', error)
    }
  }

  useEffect(() => {
    fetchCategories() // 컴포넌트가 마운트될 때 카테고리 데이터를 불러옴
  }, [])

  // 파일 업로드 핸들러
  const handleImageUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      const fileList = Array.from(e.target.files) // FileList를 배열로 변환
      setImage(fileList[0]) // 첫 번째 이미지를 선택
      onImageChange(fileList) // 배열로 전달하여 처리
    }
  }

  // 카테고리 선택 핸들러
  const handleSelectCategory = (selectedCategory: {
    id: number
    name: string
  }) => {
    setCategory(selectedCategory)
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
