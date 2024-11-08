'use client'

import React, { useEffect, useState } from 'react'
import { WiThermometer, WiRaindrop } from 'react-icons/wi' // 날씨 관련 아이콘
import useNaverMap from '@/hooks/useNaverMap'

// 날씨 데이터의 타입 정의
interface WeatherData {
  weather: { main: string; description: string }[]
  main: { temp: number }
  clouds: { all: number }
}

// 날씨에 따른 이모티콘 반환 함수
const getWeatherEmoji = (weather: string) => {
  switch (weather) {
    case 'Clear':
      return '🌞' // 맑음
    case 'Clouds':
      return '☁️' // 구름
    case 'Rain':
      return '🌧' // 비
    case 'Snow':
      return '❄️' // 눈
    default:
      return '🌤' // 기본값 (흐림)
  }
}

export default function OwnerHome() {
  const { map, currentLocation } = useNaverMap('map', {
    zoom: 16,
  })
  const [weatherData, setWeatherData] = useState<WeatherData | null>(null)

  // Next.js API Route로 날씨 정보를 가져오는 함수
  const fetchWeather = async (lat: number, lon: number) => {
    try {
      const response = await fetch(
        `/services/weather?lat=${lat}&lon=${lon}&lang=kr`
      )
      if (response.ok) {
        const data = await response.json()
        setWeatherData(data)
      } else {
        console.error('날씨 정보를 가져오지 못했습니다.')
      }
    } catch (error) {
      console.error('API 호출 실패:', error)
    }
  }

  // 사용자의 위치 정보를 가져오는 함수
  const fetchLocation = () => {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        const { latitude, longitude } = position.coords
        fetchWeather(latitude, longitude) // 위치 정보를 가져온 후 날씨 API 요청
      },
      (error) => console.error('위치 정보를 가져오지 못했습니다:', error)
    )
  }

  useEffect(() => {
    fetchLocation() // 컴포넌트가 마운트될 때 위치 정보를 가져옴
  }, [])

  return (
    <div
      id="map"
      className="bg-gray-medium relative"
      style={{ height: 'calc(100vh - 4rem)' }}
    >
      {/* 상단 날씨 정보 섹션 */}
      <section className="w-full p-2 top-2 absolute h-28 bg-none z-50">
        <div className="flex justify-between items-center bg-white w-full h-full shadow-md rounded-md p-4">
          {weatherData ? (
            <div className="flex items-center">
              {/* 날씨 이모티콘 및 설명 */}
              <div className="flex flex-col items-center mr-4 ml-4">
                <div className="text-4xl">
                  {getWeatherEmoji(weatherData.weather[0].main)}
                </div>
                <p className="text-sm mt-1">
                  {weatherData.weather[0].description}
                </p>
              </div>

              {/* 온도 및 강수 확률 */}
              <div className="flex flex-col ml-4">
                <div className="flex items-center text-lg mt-2">
                  <WiThermometer className="text-xl mr-1" />
                  <span>{weatherData.main.temp}°</span>
                  <WiRaindrop className="text-xl ml-4 mr-1" />
                  <span>강수확률 {weatherData.clouds.all}%</span>
                </div>
                <div className="w-full">
                  <div className="flex w-full h-full ml-1 mt-2">
                    <p className="text-base">
                      오늘은 장사하기 좋은 날이에요 😊
                    </p>
                  </div>
                </div>
              </div>
            </div>
          ) : (
            <p>날씨 정보를 가져오는 중...</p>
          )}
        </div>
      </section>

      {/* 하단 섹션 */}
      <section className="flex flex-col items-center z-50 shadow-up-shadow bg-secondary-light rounded-t-xl w-full absolute bottom-0 px-6 pb-10 pt-7">
        <h2 className="text-xl font-semibold pb-6">
          영업 장소가 이 장소가 맞나요?
        </h2>
        <p className="pb-8">서울특별시 강남구 테헤란로 212</p>
        <button className="bg-primary-500 text-secondary-light rounded-md text-xl font-semibold w-full py-4">
          오늘의 영업 시작하기
        </button>
      </section>
    </div>
  )
}
