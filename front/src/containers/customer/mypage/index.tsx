'use client'

import { useState, useEffect } from 'react'
import Image from 'next/image'
import Link from 'next/link'
import { MdEventNote } from 'react-icons/md'
import { RiHeart3Line, RiNotification2Line } from 'react-icons/ri'
import { useRouter } from 'next/navigation'
import Skeleton from 'react-loading-skeleton'
import 'react-loading-skeleton/dist/skeleton.css'

interface UserInfo {
  name: string
  profileImgSrc: string
  orderStatusAlert: boolean
  dibsStoreAlert: boolean
}

async function fetchUserInfo(): Promise<UserInfo> {
  const response = await fetch('/services/users/profile')
  return response.json()
}

async function updateNotification(alertType: string, alertOn: boolean) {
  const response = await fetch('/services/users/alert', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      alertOn,
      alertType,
    }),
  })

  if (!response.ok) {
    console.error('Failed to update notification')
  }
}

export default function MyPage() {
  const [userInfo, setUserInfo] = useState<UserInfo | null>(null)
  const [orderNotification, setOrderNotification] = useState<boolean | null>(
    null
  )
  const [storeNotification, setStoreNotification] = useState<boolean | null>(
    null
  )

  const router = useRouter()

  useEffect(() => {
    const getUserInfo = async () => {
      const data = await fetchUserInfo()
      setUserInfo(data)
      setOrderNotification(data.orderStatusAlert)
      setStoreNotification(data.dibsStoreAlert)
    }

    getUserInfo().then()
  }, [])

  const handleLogout = async () => {
    const response = await fetch('/services/users/logout', {
      method: 'POST',
    })

    if (response.ok) {
      router.push('/')
    } else {
      console.error('Logout failed')
    }
  }

  const handleWithdrawal = async () => {
    const response = await fetch('/services/users/withdraw', {
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
      <div className="flex flex-col items-center mb-6">
        {userInfo ? (
          <>
            <Image
              src={userInfo.profileImgSrc}
              alt="프로필 이미지"
              width={96}
              height={96}
              className="rounded-full bg-gray-medium"
            />
            <h2 className="text-xl font-semibold mt-4">{userInfo.name}</h2>
          </>
        ) : (
          // 스켈레톤 UI
          <div className="flex flex-col items-center">
            <Skeleton circle height={96} width={96} />
            <Skeleton width={120} height={24} style={{ marginTop: '1rem' }} />
          </div>
        )}
      </div>

      <div className="flex items-center justify-evenly bg-white py-6 rounded-xl border border-gray-medium">
        <Link
          href="/customer/mypage/reviews"
          className="flex flex-col items-center"
        >
          <MdEventNote className="text-primary-500" size={20} />
          <p className="text-sm font-medium mt-2">내 리뷰</p>
        </Link>

        <div className="h-8 border-l border-gray-medium" />

        <Link
          href="/customer/mypage/notifications"
          className="flex flex-col items-center"
        >
          <RiNotification2Line className="text-primary-500" size={20} />
          <p className="text-sm font-medium mt-2">알림</p>
        </Link>

        <div className="h-8 border-l border-gray-medium" />

        <Link href="/customer/favorites" className="flex flex-col items-center">
          <RiHeart3Line className="text-primary-500" size={20} />
          <p className="text-sm font-medium mt-2">찜 목록</p>
        </Link>
      </div>

      <div className="px-4">
        <div className="flex justify-between items-center my-6">
          <div>
            <p className="font-medium">주문 현황 알림</p>
            <p className="text-xs text-gray-dark">
              실시간 주문 상태를 알려드려요
            </p>
          </div>
          <div
            role="presentation"
            onClick={() => {
              const newState = !orderNotification
              setOrderNotification(newState)
              updateNotification('order-status-alert', newState).then()
            }}
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
          <div
            role="presentation"
            onClick={() => {
              const newState = !storeNotification
              setStoreNotification(newState)
              updateNotification('dibs-store-alert', newState).then()
            }}
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
