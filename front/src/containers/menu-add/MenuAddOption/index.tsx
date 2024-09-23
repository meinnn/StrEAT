"use client";

import { useState } from 'react';
import { LuPencil } from "react-icons/lu";

const MenuAddOption = () => {
  const [optionGroups, setOptionGroups] = useState([
    { name: '옵션 1' , options: [{ name: '선택 1', price: '' }], isRequired: false, minOptions: 0, maxOptions: 1 },
  ]);

  // 옵션 그룹 추가
  const handleAddOptionGroup = () => {
    setOptionGroups([
      ...optionGroups,
      { name: `옵션 ${optionGroups.length + 1}`, options: [{ name: '선택 1', price: '' }], isRequired: false, minOptions: 0, maxOptions: 1 },
    ]);
  };

  // 옵션 그룹 삭제
  const handleDeleteOptionGroup = (groupIndex: number) => {
    const newOptionGroups = optionGroups.filter((_, i) => i !== groupIndex);
    setOptionGroups(newOptionGroups);
  };

  // 옵션 추가
  const handleAddOption = (groupIndex: number) => {
    const newOptionGroups = [...optionGroups];
    newOptionGroups[groupIndex].options.push({ name: '선택 1', price: '' });
    setOptionGroups(newOptionGroups);
  };

  // 옵션 삭제
  const handleDeleteOption = (groupIndex: number, optionIndex: number) => {
    const newOptionGroups = [...optionGroups];
    newOptionGroups[groupIndex].options = newOptionGroups[groupIndex].options.filter((_, i) => i !== optionIndex);
    setOptionGroups(newOptionGroups);
  };

  // 옵션 그룹 이름 변경
  const handleGroupNameChange = (groupIndex: number, newName: string) => {
    const newOptionGroups = [...optionGroups];
    newOptionGroups[groupIndex].name = newName;
    setOptionGroups(newOptionGroups);
  };

  // 선택 여부 토글
  const handleToggle = (groupIndex: number) => {
    const newOptionGroups = [...optionGroups];
    newOptionGroups[groupIndex].isRequired = !newOptionGroups[groupIndex].isRequired;
    newOptionGroups[groupIndex].minOptions = newOptionGroups[groupIndex].isRequired ? 1 : 0;
    setOptionGroups(newOptionGroups);
  };

  return (
    <div className="mb-4 mt-6 pr-4 pl-4">
      {optionGroups.map((group, groupIndex) => (
        <div key={groupIndex} className="mb-10 border-2 border-gray-300 rounded-md p-4"> {/* 그룹 간 간격 조정 */}
          <div className="mb-4 flex justify-between items-center">
            {/* 옵션 그룹 이름 수정 필드 */}
            <div className="flex items-center">
              <input
                type="text"
                value={group.name}
                onChange={(e) => handleGroupNameChange(groupIndex, e.target.value)}
                className="p-2 rounded-md border border-gray-300 w-auto font-bold"
              />
              <LuPencil className="ml-2" />
            </div>
            {/* 선택 여부 토글 */}
            <div className="flex items-center text-sm">
              <span className="mr-2">선택 여부</span>
              <div
                onClick={() => handleToggle(groupIndex)}
                className={`relative w-14 h-7 flex items-center ${group.isRequired ? 'bg-secondary' : 'bg-gray-300'} rounded-full p-1 cursor-pointer transition-colors duration-300`}
              >
                <div
                  className={`w-6 h-6 bg-white rounded-full shadow-md flex items-center justify-center text-xs font-bold transform ${group.isRequired ? 'translate-x-6' : 'translate-x-0'} transition-transform duration-300`}
                >
                  {group.isRequired ? '필수' : '선택'}
                </div>
              </div>
            </div>
          </div>

          {/* 옵션들 */}
          <div className="border-2 border-gray-500 rounded-md p-4">
            {group.options.map((option, optionIndex) => (
              <div key={optionIndex} className="flex items-center mb-2">
                <input
                  type="text"
                  value={option.name}
                  onChange={(e) => {
                    const newOptionGroups = [...optionGroups];
                    newOptionGroups[groupIndex].options[optionIndex].name = e.target.value;
                    setOptionGroups(newOptionGroups);
                  }}
                  className="p-2 rounded w-full"
                />
                <input
                  type="text"
                  placeholder="추가 금액"
                  value={option.price}
                  onChange={(e) => {
                    const newOptionGroups = [...optionGroups];
                    newOptionGroups[groupIndex].options[optionIndex].price = e.target.value;
                    setOptionGroups(newOptionGroups);
                  }}
                  className="border border-gray-300 p-2 rounded-xl ml-2 w-24 h-8 text-sm"
                />
                <button
                  onClick={() => handleDeleteOption(groupIndex, optionIndex)}
                  className="ml-2 text-red-500"
                >
                  🗑️
                </button>
              </div>
            ))}
            <button
              onClick={() => handleAddOption(groupIndex)}
              className="mt-2 text-gray-500 flex items-center"
            >
              ➕ 선택 추가
            </button>
          </div>

          {/* 옵션 수 선택 */}
          <div className="mt-4">
            <div className="text-md font-bold">
              <h1>옵션 수</h1>
            </div>
            <div className="flex justify-between items-center mt-2"> {/* 간격 조정 */}
              <div className="flex items-center">
                <label className="mr-2">최소</label>
                <select
                  value={group.minOptions}
                  onChange={(e) => {
                    const newOptionGroups = [...optionGroups];
                    newOptionGroups[groupIndex].minOptions = parseInt(e.target.value);
                    setOptionGroups(newOptionGroups);
                  }}
                  className="border border-gray-300 rounded-md p-2 w-20 h-8 text-sm"
                >
                  {Array.from({ length: group.options.length + 1 }, (_, i) => i).map((value) => (
                    <option key={value} value={value}>
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
                    const newOptionGroups = [...optionGroups];
                    newOptionGroups[groupIndex].maxOptions = parseInt(e.target.value);
                    setOptionGroups(newOptionGroups);
                  }}
                  className="border border-gray-300 rounded-md p-2 w-20 h-8 text-sm"
                >
                  {Array.from({ length: group.options.length }, (_, i) => i + 1).map((value) => (
                    <option key={value} value={value}>
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
          {groupIndex === optionGroups.length - 1 && (
            <div className="border-t-2 border-gray-300 my-6"></div>
          )}
        </div>
      ))}

      {/* 옵션 그룹 추가하기 버튼 */}
      <div className="mb-6 flex justify-center">
        <button
          onClick={handleAddOptionGroup}
          className=" text-gray-400 py-2 px-6"
        >
          옵션 추가하기
          <div>➕</div>
        </button>
      </div>

      {/* 저장하기 버튼 */}
      <div className="mt-4 flex justify-center">
        <button className="bg-primary-500 text-white font-bold py-2 px-6 rounded-md w-full">
          저장하기
        </button>
      </div>
    </div>
  );
};

export default MenuAddOption;
