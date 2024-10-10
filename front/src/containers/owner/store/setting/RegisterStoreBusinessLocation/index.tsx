/* eslint-disable consistent-return */

'use client'

import { useCallback, useEffect, useRef, useState } from 'react'
import { MdOutlineCameraAlt } from 'react-icons/md'
import Image from 'next/image'
import { useRouter } from 'next/navigation'
import { useQueryClient } from '@tanstack/react-query'
import { ImageFile } from '@/containers/customer/orders/ReviewImageUploader'
import useNaverMap from '@/hooks/useNaverMap'
import { useOwnerInfo } from '@/hooks/useOwnerInfo'

type ApiResponse<T> = {
  message?: string
  error?: string
  data?: T
}

export default function RegisterStoreBusinessLocation() {
  const router = useRouter()
  const queryClient = useQueryClient()
  const {
    data: ownerInfo,
    error: ownerInfoError,
    isLoading: ownerInfoLoading,
  } = useOwnerInfo()
  const [nickname, setNickname] = useState() // 별칭
  const [locationImage, setLocationImage] = useState<ImageFile | null>(null) // 영업 위치 사진
  const [selectedLocation, setSelectedLocation] = useState({
    latitude: null,
    longitude: null,
    address: null,
  })
  const { map, currentLocation } = useNaverMap('map', {
    zoom: 16,
  })
  const markerRef = useRef<naver.maps.Marker | null>(null)

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
            setSelectedLocation((pre) => ({
              ...pre,
              address: roadAddress || jibunAddress,
            }))
          } else {
            console.error('Failed to reverse geocode coordinates')
          }
        }
      )
    }
  }, [])

  const handleChangeFile = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0]
    if (file) {
      const preview = URL.createObjectURL(file)
      setLocationImage({ preview, file })
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
        address: null,
      })

      console.log('내가 선택한 위치:', clickedLatLng.y, clickedLatLng.x)

      fetchAddressFromCoords(clickedLatLng)
    })

    return () => {
      naver.maps.Event.removeListener(listener)
    }
  }, [map, fetchAddressFromCoords])

  const handleClickRegisterButton = async () => {
    const { latitude, longitude, address } = selectedLocation
    if (!locationImage || !nickname) return
    if (!latitude || !longitude || !address) return

    const formData = new FormData()
    formData.append(
      'locationInfo',
      JSON.stringify({
        nickname,
        address: selectedLocation.address,
        latitude: selectedLocation.latitude,
        longitude: selectedLocation.longitude,
      })
    )

    console.log('사장정보', ownerInfo)

    // eslint-disable-next-line no-plusplus
    for (let i = 0; i < 2; i++) {
      formData.append('images', locationImage.file, locationImage.file.name)
    }
    // formData.append('images', locationImage.file, locationImage.file.name)

    const response = await fetch(
      `/services/store/${ownerInfo?.storeId}/business-location`,
      {
        method: 'POST',
        body: formData,
      }
    )

    if (response.ok) {
      router.back()
      queryClient.invalidateQueries({
        queryKey: ['/store/business-location', ownerInfo?.storeId],
      })
    } else {
      const statusCode = response.status
      const errorData: ApiResponse<null> = await response.json()
      console.error('Error:', errorData.error)

      if (statusCode === 400) {
        console.error('점포가 이미 존재합니다.')
      } else {
        console.error('에러가 발생했습니다. 다시 시도해주세요')
      }
    }
  }

  return (
    <section className="overflow-hidden flex flex-col items-center pt-6 absolute z-50 bg-white w-full max-w-80 rounded-lg">
      <h1 className="mb-3 font-medium text-text">영업 장소를 등록해주세요</h1>
      <div className="flex flex-col items-center gap-3 px-3 w-full mb-3">
        <input
          type="text"
          onChange={(e: any) => setNickname(e.target.value)}
          className="border-b w-full py-1 px-2 border-gray-dark outline-none"
          placeholder="별칭을 입력해주세요"
        />
        <p className="text-xs px-2 font-normal text-text">
          {selectedLocation.address
            ? selectedLocation.address
            : '현재 선택된 장소가 없습니다.'}
        </p>
        <div className=" bg-gray-medium w-full h-28 rounded overflow-hidden text-text">
          <label
            htmlFor="file-upload"
            className="cursor-pointer flex flex-col justify-center items-center bg-gray-medium w-full h-full gap-1"
          >
            {locationImage ? (
              <div className="w-full h-40 relative">
                <Image
                  src={locationImage?.preview || ''}
                  className="object-cover"
                  alt="점포 위치 사진"
                  fill
                />
              </div>
            ) : (
              <>
                <MdOutlineCameraAlt className="w-6 h-6" />
                <p className="text-[10px] font-normal">
                  영업 장소 이미지를 추가해주세요
                </p>
              </>
            )}
          </label>
          <input
            onChange={handleChangeFile}
            id="file-upload"
            type="file"
            className="hidden"
          />
        </div>
      </div>
      <div id="map" className=" w-full h-48" />
      <div className="flex gap-2 self-center px-3 py-3 w-full bg-white">
        <button
          onClick={() => router.back()}
          className="text-xs w-full py-2 bg-gray-medium text-text rounded-lg font-normal"
        >
          취소
        </button>
        <button
          onClick={handleClickRegisterButton}
          className="text-xs w-full py-2 bg-primary-500 text-secondary-light rounded-lg font-normal"
        >
          등록하기
        </button>
      </div>
    </section>
  )
}
