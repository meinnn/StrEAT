"use client"

import { useState } from 'react'

const MenuAddOption = () => {
  const [options, setOptions] = useState([{ name: '선택 1', price: '' }])
  const [isRequired, setIsRequired] = useState(false)  // 선택 여부 토글
  const [minOptions, setMinOptions] = useState(0)  // 최소 옵션 수
  const [maxOptions, setMaxOptions] = useState(1)  // 최대 옵션 수

  // 옵션 추가
  const handleAddOption = () => {
    setOptions([...options, { name: '선택 1', price: '' }])
  }

  // 옵션 삭제
  const handleDeleteOption = (index: number) => {
    const newOptions = options.filter((_, i) => i !== index)
    setOptions(newOptions)
  }

  // 선택 여부 토글
  const handleToggle = () => {
    setIsRequired(!isRequired)
    if (!isRequired) {
        setMinOptions(1) // 필수일 때 최소 1부터
    } else {
        setMinOptions(0) // 선택일 때 최소 0부터
    }
  }

  return (
    <div className="mb-4 mt-6 pr-4 pl-4">
      <div className="mb-2 flex justify-between items-center">
        <h2 className="text-md font-semibold">옵션 1</h2>
        <div className="flex items-center text-sm">
          <span className="mr-2">선택 여부</span>
          <div
            onClick={handleToggle}
            className={`relative w-14 h-7 flex items-center bg-${isRequired ? 'secondary' : 'gray-300'} rounded-full p-1 cursor-pointer transition-colors duration-300`}
          >
            <div
              className={`w-6 h-6 bg-white rounded-full shadow-md flex items-center justify-center text-xs font-bold transform ${isRequired ? 'translate-x-6' : 'translate-x-0'} transition-transform duration-300`}
            >
              {isRequired ? '필수' : '선택'}
            </div>
          </div>
        </div>
      </div>

      <div className="border-2 border-gray-500 rounded-md p-4">
        {options.map((option, index) => (
          <div key={index} className="flex items-center mb-2">
            <input
              type="text"
              value={option.name}
              onChange={(e) => {
                const newOptions = [...options]
                newOptions[index].name = e.target.value
                setOptions(newOptions)
              }}
              className="p-2 rounded w-full"
            />
            <input
              type="text"
              placeholder="추가 금액"
              value={option.price}
              onChange={(e) => {
                const newOptions = [...options]
                newOptions[index].price = e.target.value
                setOptions(newOptions)
              }}
              className="border border-gray-300 p-2 rounded-xl ml-2 w-24 h-8 text-sm"
            />
            <button
              onClick={() => handleDeleteOption(index)}
              className="ml-2 text-red-500"
            >
              🗑️
            </button>
          </div>
        ))}
        <button
          onClick={handleAddOption}
          className="mt-2 text-gray-500 flex items-center"
        >
          ➕ 선택 추가
        </button>
      </div>

      {/* 옵션 수 선택 */}
      <div className="mt-4">
        <div>
            <h1>옵션 수</h1>
        </div>
        <div className="flex justify-between items-center">
          <div className="flex items-center">
            <label className="mr-2">최소</label>
            <select
              value={minOptions}
              onChange={(e) => setMinOptions(parseInt(e.target.value))}
              className="border border-gray-300 rounded-md p-2 w-20 h-8 text-sm"
            >
              {Array.from({ length: options.length + 1 }, (_, i) => i).map((value) => (
                <option key={value} value={value}>
                  {value}개
                </option>
              ))}
            </select>
          </div>
          <div className="flex items-center">
            <label className="mr-2">최대</label>
            <select
              value={maxOptions}
              onChange={(e) => setMaxOptions(parseInt(e.target.value))}
              className="border border-gray-300 rounded-md p-2 w-20 h-8 text-sm"
            >
              {Array.from({ length: options.length }, (_, i) => i + 1).map((value) => (
                <option key={value} value={value}>
                  {value}개
                </option>
              ))}
            </select>
          </div>
        </div>
      </div>

      {/* 삭제하기 버튼 */}
      <div className="mt-6 mb-6 flex justify-center">
        <button className="bg-primary-500 text-white font-bold py-2 px-6 rounded-md w-full">
          삭제하기
        </button>
      </div>
    </div>
  )
}

export default MenuAddOption
