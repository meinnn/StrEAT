import { NextResponse } from 'next/server'

// eslint-disable-next-line import/prefer-default-export
export async function GET() {
  const clientId = process.env.NEXT_PUBLIC_KAKAO_APP_KEY // 환경 변수에서 REST API 키 가져오기
  const redirectUri = process.env.NEXT_PUBLIC_KAKAO_REDIRECT_URI // 환경 변수에서 리다이렉트 URI 가져오기
  const responseType = 'code'

  // 카카오 로그인 URL 생성
  const kakaoAuthUrl = `https://kauth.kakao.com/oauth/authorize?client_id=${clientId}&redirect_uri=${redirectUri}&response_type=${responseType}`

  // 사용자에게 카카오 로그인 페이지로 리다이렉트
  return NextResponse.redirect(kakaoAuthUrl)
}
