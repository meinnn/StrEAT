import { NextResponse } from 'next/server'
import { cookies } from 'next/headers'

// eslint-disable-next-line import/prefer-default-export
export async function DELETE() {
  const cookieStore = cookies()
  const token = cookieStore.get('accessToken')?.value // 쿠키에서 access_token 가져오기

  if (!token) {
    return NextResponse.json({ message: 'Unauthorized' }, { status: 401 })
  }

  const response = await fetch(
    'https://j11a307.p.ssafy.io/api/users/withdraw',
    {
      method: 'DELETE',
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }
  )

  if (response.ok) {
    // 회원탈퇴 성공 시, 쿠키 삭제 및 성공 메시지 반환
    cookieStore.delete('accessToken')
    return NextResponse.json({ message: 'Withdrawal successful' })
  }
  // 실패 시 에러 메시지 반환
  return NextResponse.json({ message: 'Withdrawal failed' }, { status: 500 })
}
