import { cookies } from 'next/headers'
import { redirect } from 'next/navigation'
import Login from '@/containers/login'

export default async function Home() {
  const cookieStore = cookies()
  const accessToken = cookieStore.get('accessToken')?.value // 쿠키에서 accessToken 읽기

  if (accessToken) {
    // 액세스 토큰이 존재하는 경우 자동 로그인 API 호출
    const response = await fetch(
      'https://j11a307.p.ssafy.io/api/users/login-auto',
      {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${accessToken}`, // Bearer 토큰 추가
        },
      }
    )

    if (response.ok) {
      // 로그인 성공 시 리다이렉트
      const data = await response.json()
      if (data.userType === 'NOT_SELECTED') {
        redirect('/sign-up')
      } else if (data.userType === 'CUSTOMER') {
        redirect('/customer')
      } else if (data.userType === 'OWNER') {
        redirect('/owner')
      }
    }
    // 실패한 경우는 아무것도 하지 않고 로그인 화면 표시
  }

  return <Login />
}
