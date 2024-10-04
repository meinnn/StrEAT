'use client'

import { RiSearchLine } from 'react-icons/ri'
import { ChangeEvent, useState } from 'react'
import { useMapCenter } from '@/contexts/MapCenterContext'
import { useRouter } from 'next/navigation'
import { IoCloseOutline } from 'react-icons/io5'

interface Location {
  id: string
  title: string
  address: string
  mapx: string
  mapy: string
}

export default function LocationSearch() {
  const { setCenter } = useMapCenter()
  const router = useRouter()

  const [searchQuery, setSearchQuery] = useState('')
  const [searchResults, setSearchResults] = useState<Location[]>([])
  const [isSearching, setIsSearching] = useState(false)

  // 임 시 데 이 터
  const [recentSearches, setRecentSearches] = useState<Location[]>([
    {
      id: '1',
      title: '역삼역 2호선',
      address: '서울특별시 강남구 테헤란로 156',
      mapx: '1270363764',
      mapy: '375006431',
    },
    {
      id: '2',
      title: '멀티캠퍼스 역삼',
      address: '서울특별시 강남구 테헤란로 212',
      mapx: '1270394660',
      mapy: '375012880',
    },
    {
      id: '3',
      title: '롯데월드타워',
      address: '서울특별시 송파구 올림픽로 300',
      mapx: '1271025624',
      mapy: '375125701',
    },
  ])

  // 최근 검색 기록 삭제
  const handleRemove = (id: string) => {
    setRecentSearches((prevSearches) =>
      prevSearches.filter((location) => location.id !== id)
    )
  }

  // 사용자가 입력할 때 호출되는 함수
  const handleInputChange = async (e: ChangeEvent<HTMLInputElement>) => {
    const query = e.target.value
    setSearchQuery(query)

    if (query.length > 0) {
      setIsSearching(true) // 검색 중 상태를 표시
      setSearchResults([]) // 검색 결과 리셋

      const res = await fetch(
        `/services/naver-search?query=${encodeURIComponent(query)}`
      )
      const data = await res.json()
      // 검색 결과에서 roadAddress가 있으면 address로 저장
      const formattedResults = data.items.map((item: any) => ({
        ...item,
        id: `${item.mapx}-${item.mapy}`,
        address: item.roadAddress ? item.roadAddress : item.address,
      }))

      setSearchResults(formattedResults || [])
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

  const locations = searchQuery.length > 0 ? searchResults : recentSearches

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
        {/* 검색 결과 출력 또는 최근 검색 위치 출력 */}
        {searchQuery.length === 0 && (
          <h2 className="text-xl font-semibold mb-2">최근 검색 위치</h2>
        )}
        <ul>
          {locations.map((location) => (
            <li
              key={`${location.mapx}-${location.mapy}`}
              className="flex justify-between items-center border-b border-gray-200 py-4"
            >
              <div
                role="presentation"
                className="w-full cursor-pointer"
                onClick={() =>
                  handleLocationClick(location.mapx, location.mapy)
                }
              >
                <p className="font-semibold mb-1">
                  {parseHtmlString(location.title)}
                </p>
                <p className="text-xs text-primary-950">{location.address}</p>
              </div>
              {/* 삭제 버튼 */}
              {searchQuery.length === 0 && (
                <button
                  type="button"
                  onClick={() => handleRemove(location.id)}
                  className="text-gray-dark self-start"
                >
                  <IoCloseOutline size={18} />
                </button>
              )}
            </li>
          ))}
        </ul>
      </div>
      {/* 로딩 상태 표시 */}
      {isSearching && <p className="text-sm text-gray-500 m-4">검색 중...</p>}
    </div>
  )
}
