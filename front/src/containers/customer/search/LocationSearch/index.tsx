'use client'

import { RiSearchLine } from 'react-icons/ri'
import { ChangeEvent, useState } from 'react'
import RecentSearch from '@/containers/customer/search/RecentSearch'
import { useMapCenter } from '@/contexts/MapCenterContext'
import { useRouter } from 'next/navigation'

interface Location {
  title: string
  link: string
  category: string
  description: string
  telephone: string
  address: string
  roadAddress: string
  mapx: string
  mapy: string
}

export default function LocationSearch() {
  const { setCenter } = useMapCenter()
  const router = useRouter()

  const [searchQuery, setSearchQuery] = useState('')
  const [searchResults, setSearchResults] = useState<Location[]>([])
  const [isSearching, setIsSearching] = useState(false)

  // 사용자가 입력할 때 호출되는 함수
  const handleInputChange = async (e: ChangeEvent<HTMLInputElement>) => {
    const query = e.target.value
    setSearchQuery(query)

    if (query.length > 0) {
      setIsSearching(true) // 검색 중 상태를 표시
      setSearchResults([]) // 검색 결과 리셋

      const res = await fetch(
        `/api/naver-search?query=${encodeURIComponent(query)}`
      )
      const data = await res.json()
      setSearchResults(data.items || [])
      setIsSearching(false) // 검색 완료 상태
    } else {
      setSearchResults([]) // 입력이 없을 경우 결과 초기화
    }
  }

  function parseHtmlString(htmlString: string): string {
    // <b> 태그를 제거
    const parsedString = htmlString.replace(/<\/?b>/g, '')
    // &amp;를 &로 변환
    return parsedString.replace(/&amp;/g, '&')
  }

  // 검색 결과를 클릭했을 때 호출되는 함수
  const handleLocationClick = (mapx: string, mapy: string) => {
    const lat = parseFloat(mapy) / 1e7 // 10^7 단위로 변환 (KATECH 좌표계 )
    const lng = parseFloat(mapx) / 1e7
    const newCenter = new naver.maps.LatLng(lat, lng)
    setCenter(newCenter)
    router.push('/customer')
  }

  return (
    <div className="p-4">
      <div className="flex items-center border border-gray-medium rounded-xl p-3 bg-white">
        <RiSearchLine className="text-primary-500 mr-2" size={20} />
        <input
          type="text"
          placeholder="구, 동, 건물명, 역 등으로 검색"
          className="w-full bg-transparent outline-none text-sm"
          value={searchQuery}
          onChange={handleInputChange}
        />
      </div>
      <div className="mt-6 px-4">
        {/* 검색 결과 출력 또는 RecentSearch 출력 */}
        {searchQuery.length > 0 ? (
          <ul>
            {searchResults.map((location) => (
              <li
                key={`${location.mapx}-${location.mapy}`}
                className="flex justify-between items-center border-b border-gray-200 py-4"
              >
                <div
                  role="presentation"
                  className="w-full"
                  onClick={() =>
                    handleLocationClick(location.mapx, location.mapy)
                  }
                >
                  <p className="font-semibold mb-1">
                    {parseHtmlString(location.title)}
                  </p>
                  <p className="text-xs text-primary-950">{location.address}</p>
                </div>
              </li>
            ))}
          </ul>
        ) : (
          <RecentSearch />
        )}
      </div>
      {/* 로딩 상태 표시 */}
      {isSearching && <p className="text-sm text-gray-500 mt-2">검색 중...</p>}
    </div>
  )
}
