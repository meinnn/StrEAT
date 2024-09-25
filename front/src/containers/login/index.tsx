import Image from 'next/image'
import Link from 'next/link'

export default function Login() {
  return (
    <div className="h-screen flex flex-col items-center justify-center">
      <h1 className="text-5xl font-bold mb-20">StrEAT</h1>
      <Link href="/sign-up" className="mx-10 mb-4">
        <Image
          src="/images/kakao_login.png"
          alt="kakao login"
          width={600}
          height={90}
          className="object-cover"
        />
      </Link>
      <Link href="/customer" className="underline">
        비회원으로 둘러보기
      </Link>
    </div>
  )
}
