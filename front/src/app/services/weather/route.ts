import { NextResponse } from 'next/server'

// eslint-disable-next-line import/prefer-default-export
export async function GET(req: Request) {
  const { searchParams } = new URL(req.url)
  const lat = searchParams.get('lat')
  const lon = searchParams.get('lon')

  const apiKey = process.env.NEXT_PUBLIC_OPENWEATHER_API_KEY // 환경변수에 설정된 API 키 사용
  const response = await fetch(
    `https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=${apiKey}&units=metric&lang=kr`
  )

  if (response.ok) {
    const weatherData = await response.json()
    return NextResponse.json(weatherData)
  }
  return NextResponse.json(
    { message: 'Error fetching weather data' },
    { status: 500 }
  )
}
