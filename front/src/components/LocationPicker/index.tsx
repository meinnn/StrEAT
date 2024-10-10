/* eslint-disable func-names */

'use client'

/* eslint-disable consistent-return */
/* eslint-disable no-use-before-define */
/* eslint-disable no-alert */
import { useEffect, useState, useCallback, useRef } from 'react'
import { RiSearchLine } from 'react-icons/ri'
import { useRouter } from 'next/navigation'
import useNaverMap from '@/hooks/useNaverMap'
import { useMapCenter } from '@/contexts/MapCenterContext'
import { useStoreLocation } from '@/contexts/StoreLocationContext'

export default function LocationPicker() {
  const router = useRouter()
  const { setStoreLocation } = useStoreLocation()
  const [searchVal, setSearchVal] = useState('')
  const [currentAddress, setCurrentAddress] =
    useState<string>('선택된 위치가 없습니다.')
  const { center, setCenter } = useMapCenter()
  const { map, currentLocation } = useNaverMap('map', {
    zoom: 16,
  })
  const markerRef = useRef<naver.maps.Marker | null>(null)
  const [selectedLocation, setSelectedLocation] = useState({
    latitude: null,
    longitude: null,
  })

  const mark = (lat: any, lng: any) => {
    if (!map) return
    markerRef.current = new naver.maps.Marker({
      position: new naver.maps.LatLng(lat, lng),
      map,
      icon: {
        content: `<img src="/images/truck.png" class="w-8 h-6"/>`,
      },
    })
  }

  const fetchAddressFromCoords = useCallback((coords: naver.maps.Coord) => {
    if (window.naver && window.naver.maps && window.naver.maps.Service) {
      window.naver.maps.Service.reverseGeocode(
        {
          coords,
          orders: naver.maps.Service.OrderType.ROAD_ADDR,
          // orders: [
          //   naver.maps.Service.OrderType.ADDR,
          //   naver.maps.Service.OrderType.ROAD_ADDR,
          // ],
        },
        (status: any, response: any) => {
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

  // 주소를 좌표로 변환하는 함수
  const searchAddressToCoordinate = (address: string) => {
    if (!map) return
    if (window.naver && window.naver.maps && window.naver.maps.Service) {
      naver.maps.Service.geocode(
        {
          query: address,
        },
        (status: any, response: any) => {
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
      const clickedLatLng = e.coord

      if (markerRef.current) {
        markerRef.current.setMap(null)
      }

      mark(clickedLatLng.y, clickedLatLng.x)
      setSelectedLocation({
        latitude: clickedLatLng.y,
        longitude: clickedLatLng.x,
      })

      console.log('내가 선택한 위치:', clickedLatLng.y, clickedLatLng.x)

      fetchAddressFromCoords(clickedLatLng)
    })

    return () => {
      naver.maps.Event.removeListener(listener)
    }
  }, [map, fetchAddressFromCoords])

  return (
    <div
      id="map"
      className="h-screen relative px-3 pt-4 max-w-96 top-0 left-0 overflow-hidden bg-gray-dark max-h-[36rem] h-full w-full rounded-xl"
    >
      <div className="w-full pt-4 px-3 absolute z-10 top-0 left-0">
        <form
          onSubmit={(e: any) => {
            e.preventDefault()
            // searchAddressToCoordinate(searchVal)
            handleInputChange(searchVal)
          }}
          className="flex items-center w-full shadow-md rounded-xl overflow-hidden pl-4 bg-white"
        >
          <RiSearchLine className="text-primary-400 w-6 h-6" />
          <input
            onChange={(e: any) => setSearchVal(e.target.value)}
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
          onClick={() => {
            setStoreLocation({
              latitude: selectedLocation.latitude,
              longitude: selectedLocation.longitude,
              address: currentAddress,
            })
            router.back()
          }}
          type="button"
          className="bg-primary-500 py-5 text-secondary-light"
        >
          위치 등록하기
        </button>
      </div>
    </div>
  )
}
