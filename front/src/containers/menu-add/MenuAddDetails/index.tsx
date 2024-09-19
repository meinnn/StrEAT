"use client"

import { useState } from 'react'

const MenuAddDetails = () => {
  const [foodName, setFoodName] = useState<string>('')
  const [description, setDescription] = useState<string>('')
  const [price, setPrice] = useState<string>('')
  const [image, setImage] = useState<File | null>(null)

  // 파일 업로드 핸들러
  const handleImageUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files[0]) {
      setImage(e.target.files[0])
    }
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
            <img
              src={URL.createObjectURL(image)}
              alt="메뉴 사진"
              className="w-full h-64 rounded-lg"
            />
          ) : (
            <div className="bg-gray-200 rounded-lg w-full h-64 flex flex-col justify-center items-center text-gray-500">
              📷 <br />
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
        <div className="mb-2">
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
            className="w-full p-2 border bg-gray-200 rounded h-20"
            placeholder="요리 설명 입력"
          />
        </div>
        <div className="mb-2">
          <h3 className="block font-bold text-black">금액</h3>
          <input
            type="text"
            value={price}
            onChange={(e) => setPrice(e.target.value)}
            className="w-full p-2 border bg-gray-200 rounded"
            placeholder="금액 입력"
          />
        </div>
      </div>
    </div>
  )
}

export default MenuAddDetails
