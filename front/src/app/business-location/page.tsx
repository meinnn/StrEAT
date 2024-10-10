/* eslint-disable consistent-return */

'use client'

import { useEffect, useState, useCallback, useRef } from 'react'
import { RiSearchLine } from 'react-icons/ri'
import useNaverMap from '@/hooks/useNaverMap'
import { useMapCenter } from '@/contexts/MapCenterContext'

// naver.maps 관련 타입
type NaverCoord = naver.maps.Coord
type NaverMap = naver.maps.Map
type NaverMarker = naver.maps.Marker

export default function LocationPicker() {
  const [searchVal, setSearchVal] = useState<string>('') // 타입 명시
  const [currentAddress, setCurrentAddress] =
    useState<string>('선택된 위치가 없습니다.')
  const { center, setCenter } = useMapCenter()
  const { map, currentLocation } = useNaverMap('map', { zoom: 16 })
  const markerRef = useRef<NaverMarker | null>(null) // 타입 명시

  const mark = (lat: number, lng: number) => {
    // 타입 명시
    if (!map) return
    if (markerRef.current) {
      markerRef.current.setMap(null) // 이전 마커 제거
    }
    markerRef.current = new naver.maps.Marker({
      position: new naver.maps.LatLng(lat, lng),
      map,
      icon: {
        content: `<img src="/images/truck.png" class="w-8 h-6"/>`,
      },
    })
  }

  const fetchAddressFromCoords = useCallback((coords: NaverCoord) => {
    if (window.naver && window.naver.maps && window.naver.maps.Service) {
      window.naver.maps.Service.reverseGeocode(
        {
          coords,
          orders: naver.maps.Service.OrderType.ROAD_ADDR,
          // [
          //   naver.maps.Service.OrderType.ADDR,
          //   naver.maps.Service.OrderType.ROAD_ADDR,
          // ],
        },
        (status: naver.maps.Service.Status, response: any) => {
          if (status === naver.maps.Service.Status.OK) {
            const { jibunAddress, roadAddress } = response.v2.address
            setCurrentAddress(roadAddress || jibunAddress)
          } else {
            console.error('Failed to reverse geocode coordinates')
          }
        }
      )
    }
  }, [])

  const handleInputChange = async (query: string) => {
    const res = await fetch(
      `/services/naver-search?query=${encodeURIComponent(query)}`
    )
    const data = await res.json()

    if (data && data.items && data.items.length > 0 && map) {
      const item = data.items[0]
      const lat = parseFloat(item.mapy) / 1e7
      const lng = parseFloat(item.mapx) / 1e7
      const newCenter = new naver.maps.LatLng(lat, lng)
      setCenter(newCenter)
      map.setCenter(newCenter)
    }
  }

  const searchAddressToCoordinate = (address: string) => {
    if (!map) return
    if (window.naver && window.naver.maps && window.naver.maps.Service) {
      naver.maps.Service.geocode(
        {
          query: address,
        },
        (status: naver.maps.Service.Status, response: any) => {
          if (status === window.naver.maps.Service.Status.ERROR) {
            return alert('Something went wrong!')
          }
          console.log('response:', response)

          if (response.v2.meta.totalCount === 0) {
            return alert(`No results found.`)
          }

          console.log('response.v2.addresses:', response.v2.addresses)

          const item = response.v2.addresses[0]
          const point = new window.naver.maps.LatLng(item.y, item.x)
          setCenter(point)
          mark(item.y, item.x)
          map.setCenter(point)
        }
      )
    }
  }

  useEffect(() => {
    if (!map) return

    const listener = naver.maps.Event.addListener(map, 'click', (e: any) => {
      const clickedLatLng: NaverCoord = e.coord

      if (markerRef.current) {
        markerRef.current.setMap(null)
      }

      mark(clickedLatLng.y, clickedLatLng.x)
      fetchAddressFromCoords(clickedLatLng)
    })

    return () => {
      naver.maps.Event.removeListener(listener)
    }
  }, [map, fetchAddressFromCoords])

  return (
    <div
      id="map"
      className="relative h-full px-3 pt-4 max-w-96 top-0 left-0 overflow-hidden bg-gray-dark max-h-[36rem] h-full w-full rounded-xl"
    >
      <div className="w-full pt-4 px-3 absolute z-10 top-0 left-0">
        <form
          onSubmit={(e: React.FormEvent<HTMLFormElement>) => {
            e.preventDefault()
            handleInputChange(searchVal)
          }}
          className="flex items-center w-full shadow-md rounded-xl overflow-hidden pl-4 bg-white"
        >
          <RiSearchLine className="text-primary-400 w-6 h-6" />
          <input
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setSearchVal(e.target.value)
            }
            type="text"
            placeholder="위치를 검색해주세요"
            className="outline-none placeholder-gray-dark w-full py-3 pr-4 pl-2 text-text"
          />
          <button type="submit" className="hidden">
            제출
          </button>
        </form>
      </div>
      <div className="absolute w-full z-[110] flex flex-col bottom-0 left-0 overflow-hidden bg-red-100 rounded-t-lg">
        <div className="flex flex-col gap-3 bg-white px-6 pt-3 pb-4 text-text">
          <h4 className="font-medium text-lg">현재 선택된 위치</h4>
          <p>{currentAddress}</p>
        </div>
        <button
          type="button"
          className="bg-primary-500 py-5 text-secondary-light"
        >
          위치 등록하기
        </button>
      </div>
    </div>
  )
}
