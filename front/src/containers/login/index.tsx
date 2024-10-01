'use client'

import Image from 'next/image'
import Link from 'next/link'

export default function Login() {
  const handleKakaoLogin = async () => {
    window.location.href = '/services/users/login/kakao'
  }

  return (
    <div className="h-screen flex flex-col items-center justify-center">
      <h1 className="text-5xl font-bold mb-20">StrEAT</h1>
      <button type="button" onClick={handleKakaoLogin} className="mx-10 mb-4">
        <Image
          src="/images/kakao_login.png"
          alt="카카오 로그인"
          width={600}
          height={90}
          className="w-full object-cover"
        />
      </button>
      <Link href="/customer" className="underline">
        비회원으로 둘러보기
      </Link>
    </div>
  )
}
