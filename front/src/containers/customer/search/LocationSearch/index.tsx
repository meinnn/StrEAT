'use client'

import { RiSearchLine } from 'react-icons/ri'
import { IoCloseOutline } from 'react-icons/io5'
import { useState } from 'react'

export default function LocationSearch() {
  // 임 시 데 이 터
  const [recentSearches, setRecentSearches] = useState([
    { id: 1, name: '역삼역 2호선', address: '서울 강남구 테헤란로 지하 156' },
    { id: 2, name: '역삼역 2호선', address: '서울 강남구 테헤란로 지하 156' },
  ])

  const handleRemove = (id: number) => {
    setRecentSearches((prevSearches) =>
      prevSearches.filter((location) => location.id !== id)
    )
  }

  return (
    <div className="p-4">
      <div className="flex items-center border border-gray-medium rounded-xl p-3 bg-white">
        <RiSearchLine className="text-primary-500 mr-2" size={20} />
        <input
          type="text"
          placeholder="구, 동, 건물명, 역 등으로 검색"
          className="w-full bg-transparent outline-none text-sm"
        />
      </div>

      {recentSearches.length > 0 && (
        <div className="mt-6 px-4">
          <h2 className="text-xl font-semibold mb-2">최근 검색 위치</h2>
          <ul>
            {recentSearches.map((location) => (
              <li
                key={location.id}
                className="flex justify-between items-center border-b border-gray-200 py-4"
              >
                <div>
                  <p className="font-semibold mb-1">{location.name}</p>
                  <p className="text-xs text-primary-950">{location.address}</p>
                </div>
                <button
                  type="button"
                  onClick={() => handleRemove(location.id)}
                  className="text-gray-dark self-start"
                >
                  <IoCloseOutline size={18} />
                </button>
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  )
}
