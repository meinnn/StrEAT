'use client'

import { useState } from 'react'
import { IoCloseOutline } from 'react-icons/io5'

export default function RecentSearch() {
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
    <>
      {recentSearches.length > 0 && (
        <div>
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
    </>
  )
}
