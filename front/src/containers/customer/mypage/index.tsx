'use client'

import { useState } from 'react'
import Image from 'next/image'
import Link from 'next/link'
import { MdEventNote } from 'react-icons/md'
import { RiHeart3Line, RiNotification2Line } from 'react-icons/ri'
import { useRouter } from 'next/navigation'

export default function MyPage() {
  // 알림 스위치 상태 관리
  const [orderNotification, setOrderNotification] = useState(true)
  const [storeNotification, setStoreNotification] = useState(true)

  const router = useRouter()

  const handleLogout = async () => {
    const response = await fetch('/api/logout', {
      method: 'POST',
    })

    if (response.ok) {
      router.push('/') // 로그인 페이지로 이동
    } else {
      console.error('Logout failed')
    }
  }

  const handleWithdrawal = async () => {
    const response = await fetch('/api/withdraw', {
      method: 'DELETE',
    })

    if (response.ok) {
      router.push('/')
    } else {
      console.error('Withdrawal failed')
    }
  }

  return (
    <div className="p-6">
      {/* 사용자 정보 */}
      <div className="flex flex-col items-center mb-6">
        <Image
          src="/images/보쌈사진.jpg" // 실제 이미지 경로로 교체
          alt="프로필 이미지"
          width={96}
          height={96}
          className="rounded-full bg-gray-medium"
        />
        <h2 className="text-xl font-semibold mt-4">익명의 아옹이</h2>
        <div className="flex items-center justify-center gap-x-1 mt-1">
          <Image
            src="/images/kakao.png"
            alt="kakao"
            width={16}
            height={16}
            className="rounded-full"
          />
          <p className="text-sm text-gray-dark">anonymous@ya.ong</p>
        </div>
      </div>

      {/* 메뉴 */}
      <div className="flex items-center justify-evenly bg-white py-6 rounded-xl border border-gray-medium">
        {/* 내 리뷰 */}
        <Link
          href="/customer/mypage/reviews"
          className="flex flex-col items-center"
        >
          <MdEventNote className="text-primary-500" size={20} />
          <p className="text-sm font-medium mt-2">내 리뷰</p>
        </Link>

        <div className="h-8 border-l border-gray-medium" />

        {/* 알림 */}
        <Link
          href="/customer/notifications"
          className="flex flex-col items-center"
        >
          <RiNotification2Line className="text-primary-500" size={20} />
          <p className="text-sm font-medium mt-2">알림</p>
        </Link>

        <div className="h-8 border-l border-gray-medium" />

        {/* 찜 목록 */}
        <Link href="/customer/favorites" className="flex flex-col items-center">
          <RiHeart3Line className="text-primary-500" size={20} />
          <p className="text-sm font-medium mt-2">찜 목록</p>
        </Link>
      </div>

      {/* 알림 스위치 */}
      <div className="px-4">
        <div className="flex justify-between items-center my-6">
          <div>
            <p className="font-medium">주문 현황 알림</p>
            <p className="text-xs text-gray-dark">
              실시간 주문 상태를 알려드려요
            </p>
          </div>
          {/* 주문 현황 알림 스위치 */}
          <div
            role="presentation"
            onClick={() => setOrderNotification(!orderNotification)}
            className={`${
              orderNotification ? 'bg-primary-500' : 'bg-gray-medium'
            } relative inline-flex h-8 w-14 items-center rounded-full cursor-pointer`}
          >
            <span
              className={`${
                orderNotification ? 'translate-x-7' : 'translate-x-1'
              } inline-block size-6 transform bg-white rounded-full transition`}
            />
          </div>
        </div>

        <div className="flex justify-between items-center mb-6">
          <div>
            <p className="font-medium">단골 가게 알림</p>
            <p className="text-xs text-gray-dark">
              찜한 가게가 주변에 있을 때 알려드려요
            </p>
          </div>
          {/* 단골 가게 알림 스위치 */}
          <div
            role="presentation"
            onClick={() => setStoreNotification(!storeNotification)}
            className={`${
              storeNotification ? 'bg-primary-500' : 'bg-gray-medium'
            } relative inline-flex h-8 w-14 items-center rounded-full cursor-pointer`}
          >
            <span
              className={`${
                storeNotification ? 'translate-x-7' : 'translate-x-1'
              } inline-block size-6 transform bg-white rounded-full transition`}
            />
          </div>
        </div>
      </div>

      <div className="h-1 my-8 bg-gray-medium mx-[-24px]" />

      {/* 기타 메뉴 */}
      <div className="px-4 space-y-4">
        <button
          type="button"
          onClick={handleLogout}
          className="w-full text-left"
        >
          로그아웃
        </button>
        <button
          type="button"
          onClick={handleWithdrawal}
          className="w-full text-left text-red-500"
        >
          회원 탈퇴
        </button>
      </div>
    </div>
  )
}
