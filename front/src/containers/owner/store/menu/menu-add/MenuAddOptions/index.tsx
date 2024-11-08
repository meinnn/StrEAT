'use client'

/* eslint-disable react/no-array-index-key */
import { useState, useEffect } from 'react'
import { LuPencil } from 'react-icons/lu'
import { FaRegTrashAlt, FaPlus } from 'react-icons/fa'

interface Option {
  name: string
  price: string
}

interface OptionGroup {
  name: string
  options: Option[]
  isRequired: boolean
  minOptions: number
  maxOptions: number
}

interface MenuAddOptionsProps {
  onOptionsChange: (optionGroups: OptionGroup[]) => void
}

export default function MenuAddOptions({
  onOptionsChange,
}: MenuAddOptionsProps) {
  const [optionGroups, setOptionGroups] = useState<OptionGroup[]>([
    {
      name: '옵션 1',
      options: [{ name: '선택 1', price: '' }],
      isRequired: false,
      minOptions: 0,
      maxOptions: 1,
    },
  ])

  useEffect(() => {
    onOptionsChange(optionGroups)
  }, [optionGroups, onOptionsChange])

  // 옵션 그룹 추가
  const handleAddOptionGroup = () => {
    setOptionGroups([
      ...optionGroups,
      {
        name: `옵션 ${optionGroups.length + 1}`,
        options: [{ name: '선택 1', price: '' }],
        isRequired: false,
        minOptions: 0,
        maxOptions: 1,
      },
    ])
  }

  // 옵션 그룹 삭제
  const handleDeleteOptionGroup = (groupIndex: number) => {
    const newOptionGroups = optionGroups.filter((_, i) => i !== groupIndex)
    setOptionGroups(newOptionGroups)
  }

  // 옵션 추가
  const handleAddOption = (groupIndex: number) => {
    const newOptionGroups = [...optionGroups]
    newOptionGroups[groupIndex].options.push({ name: '선택 1', price: '' })
    setOptionGroups(newOptionGroups)
  }

  // 옵션 삭제
  const handleDeleteOption = (groupIndex: number, optionIndex: number) => {
    const newOptionGroups = [...optionGroups]
    newOptionGroups[groupIndex].options = newOptionGroups[
      groupIndex
    ].options.filter((_, i) => i !== optionIndex)
    setOptionGroups(newOptionGroups)
  }

  // 옵션 그룹 이름 변경
  const handleGroupNameChange = (groupIndex: number, newName: string) => {
    const newOptionGroups = [...optionGroups]
    newOptionGroups[groupIndex].name = newName
    setOptionGroups(newOptionGroups)
  }

  // 선택 여부 토글
  const handleToggle = (groupIndex: number) => {
    const newOptionGroups = [...optionGroups]
    newOptionGroups[groupIndex].isRequired =
      !newOptionGroups[groupIndex].isRequired
    newOptionGroups[groupIndex].minOptions = newOptionGroups[groupIndex]
      .isRequired
      ? 1
      : 0
    setOptionGroups(newOptionGroups)
  }

  return (
    <div className="mb-4 mt-6 pr-4 pl-4">
      {optionGroups.map((group, groupIndex) => (
        <div
          key={`group-${groupIndex}`} // 배열 인덱스 대신 고유한 문자열을 사용
          className="mb-6 border-2 border-gray-300 rounded-md p-4"
        >
          <div className="mb-4 flex justify-between items-center">
            {/* 옵션 그룹 이름 수정 필드 */}
            <div className="flex items-center">
              <input
                type="text"
                value={group.name}
                onChange={(e) =>
                  handleGroupNameChange(groupIndex, e.target.value)
                }
                className="p-2 rounded-md border border-gray-300 w-24 font-bold"
              />
              <LuPencil className="ml-2" />
            </div>
            {/* 선택 여부 토글 */}
            <div className="flex items-center text-sm">
              <span className="mr-2">선택 여부</span>
              <div
                onClick={() => handleToggle(groupIndex)}
                className={`relative w-14 h-7 flex items-center ${
                  group.isRequired ? 'bg-secondary' : 'bg-gray-300'
                } rounded-full p-1 cursor-pointer transition-colors duration-300`}
              >
                <div
                  className={`w-6 h-6 bg-white rounded-full shadow-md flex items-center justify-center text-xs font-bold transform ${
                    group.isRequired ? 'translate-x-6' : 'translate-x-0'
                  } transition-transform duration-300`}
                >
                  {group.isRequired ? '필수' : '선택'}
                </div>
              </div>
            </div>
          </div>
          {/* 옵션들 */}
          <div className="border-2 border-gray-500 rounded-md p-4">
            {group.options.map((option, optionIndex) => (
              <div
                key={`option-${groupIndex}-${optionIndex}`} // 배열 인덱스 대신 고유한 문자열을 사용
                className="flex items-center mb-2"
              >
                <input
                  type="text"
                  value={option.name}
                  onChange={(e) => {
                    const newOptionGroups = [...optionGroups]
                    newOptionGroups[groupIndex].options[optionIndex].name =
                      e.target.value
                    setOptionGroups(newOptionGroups)
                  }}
                  className="p-2 rounded w-full"
                />
                <input
                  type="text"
                  placeholder="추가 금액"
                  value={option.price}
                  onChange={(e) => {
                    const newOptionGroups = [...optionGroups]
                    newOptionGroups[groupIndex].options[optionIndex].price =
                      e.target.value
                    setOptionGroups(newOptionGroups)
                  }}
                  className="border border-gray-300 p-2 rounded-xl ml-2 w-24 h-8 text-sm"
                />
                <button
                  onClick={() => handleDeleteOption(groupIndex, optionIndex)}
                  className="ml-2 text-black"
                >
                  <FaRegTrashAlt />
                </button>
              </div>
            ))}
            <button
              onClick={() => handleAddOption(groupIndex)}
              className="mt-2 text-gray-500 flex items-center"
            >
              <div className="mr-2">
                <FaPlus />
              </div>
              선택 추가
            </button>
          </div>
          {/* 옵션 수 선택 */}
          <div className="mt-4">
            <div className="text-md font-bold">
              <h1>옵션 수</h1>
            </div>
            <div className="flex justify-between items-center mt-2">
              <div className="flex items-center">
                <label className="mr-2">최소</label>
                <select
                  value={group.minOptions}
                  onChange={(e) => {
                    const newOptionGroups = [...optionGroups]
                    newOptionGroups[groupIndex].minOptions = parseInt(
                      e.target.value,
                      10
                    )
                    setOptionGroups(newOptionGroups)
                  }}
                  className="border border-gray-300 rounded-md p-2 w-20 h-8 text-sm"
                >
                  {Array.from(
                    { length: group.options.length + 1 },
                    (_, i) => i
                  ).map((value) => (
                    <option key={`min-${groupIndex}-${value}`} value={value}>
                      {value}개
                    </option>
                  ))}
                </select>
              </div>
              <div className="flex items-center">
                <label className="mr-2">최대</label>
                <select
                  value={group.maxOptions}
                  onChange={(e) => {
                    const newOptionGroups = [...optionGroups]
                    newOptionGroups[groupIndex].maxOptions = parseInt(
                      e.target.value,
                      10
                    )
                    setOptionGroups(newOptionGroups)
                  }}
                  className="border border-gray-300 rounded-md p-2 w-20 h-8 text-sm"
                >
                  {Array.from(
                    { length: group.options.length },
                    (_, i) => i + 1
                  ).map((value) => (
                    <option key={`max-${groupIndex}-${value}`} value={value}>
                      {value}개
                    </option>
                  ))}
                </select>
              </div>
            </div>
          </div>
          {/* 옵션 그룹 삭제하기 버튼 */}
          <div className="mt-6 flex justify-center">
            <button
              onClick={() => handleDeleteOptionGroup(groupIndex)}
              className="bg-primary-500 text-white font-bold py-2 px-6 rounded-md w-full"
            >
              삭제하기
            </button>
          </div>
        </div>
      ))}

      {/* 옵션 그룹 추가하기 버튼 */}
      <div className="mb-4 flex justify-center">
        <button
          onClick={handleAddOptionGroup}
          className=" text-gray-400 py-2 px-6 flex items-center"
        >
          <FaPlus className="mr-2" />
          옵션 추가하기
        </button>
      </div>
    </div>
  )
}
