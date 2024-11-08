'use client'

import { useQuery } from '@tanstack/react-query'
import Image from 'next/image'
import Link from 'next/link'
import { useRouter } from 'next/navigation'
import { GoChevronRight } from 'react-icons/go'
import { useOwnerInfo } from '@/hooks/useOwnerInfo'
import { useMyStoreInfo } from '@/hooks/useMyStoreInfo'

interface Announcement {
  entryConditions: string
  eventDays: string
  eventName: string
  eventPlace: string
  eventTimes: string
  recruitPostTitle: number
  recruitSize: string
  specialNotes: string
}
export default function OwnerAll() {
  const router = useRouter()
  const {
    data: ownerInfo,
    error: ownerInfoError,
    isLoading: ownerInfoLoading,
  } = useOwnerInfo()
  const {
    data: storeInfoData,
    error: storeInfoError,
    isLoading: storeInfoLoading,
  } = useMyStoreInfo(ownerInfo?.storeId)

  const getAnnouncementList = async () => {
    const response = await fetch(`/services/announcements/list`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization':
          'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhY2Nlc3MtdG9rZW4iLCJpYXQiOjE3Mjc4MzE0MTQsImV4cCI6MjA4NzgzMTQxNCwidXNlcklkIjoxMn0.UrVrI-WUCXdx017R4uRIl6lzxbktVSfEDjEgYe5J8UQ',
      },
    })
    if (!response.ok) {
      console.error('푸드트럭 공고 조회에 실패했습니다')
    }
    return response.json()
  }

  const {
    data: announcementData,
    error,
    isLoading,
  } = useQuery<
    {
      getAnnouncementListDTOList: Announcement[]
    },
    Error
  >({
    queryKey: ['/announcements/list'],
    queryFn: getAnnouncementList,
  })

  if (isLoading || ownerInfoLoading || storeInfoLoading) {
    return <p>로딩중</p>
  }

  if (error || ownerInfoError || storeInfoError) {
    return <p>에러 발생</p>
  }

  return (
    <div className="pt-12 bg-navbar-all-gradient pb-32">
      <h1 className="font-semibold mb-4 text-xl pl-11">
        {ownerInfo?.name}님, 안녕하세요
      </h1>

      <section className="px-7 flex flex-col gap-5 pb-12">
        <div className="text-text pt-5 pb-7 px-5 flex bg-secondary-light flex-col gap-4 border rounded-lg border-gray-medium">
          <div className="flex gap-2 items-center">
            <p className="relative h-4 w-6">
              <Image
                src="/images/truck.png"
                alt="푸드트럭 아이콘"
                fill
                priority
              />
            </p>
            <h4 className="font-medium">현재 운영중인 점포</h4>
          </div>
          <p className="font-bold text-xl">{storeInfoData?.storeInfo.name}</p>
        </div>
        <div className="text-text pt-5 pb-7 px-5 flex bg-secondary-medium flex-col gap-6 border rounded-lg border-gray-medium">
          <div className="flex justify-between">
            <h4 className="font-medium">등록된 계좌 정보</h4>
            <button
              type="button"
              className="flex items-center text-xs font-normal"
            >
              수정하기
              <GoChevronRight />
            </button>
          </div>
          <div className="flex gap-2 items-center text-text">
            <span className="w-8 h-8 bg-gray-medium rounded-full" />
            <div className="flex flex-col gap-1">
              <p className="text-xs font-medium">우리은행</p>
              <p className="font-bold text-xl">1002-000-00000000</p>
            </div>
          </div>
        </div>
      </section>

      <hr className="h-1 border-none bg-gray-medium" />

      <section className="px-8 pt-6 pb-10">
        <div className="flex justify-between items-center mb-5 pr-4">
          <h4 className="font-medium text-gray-dark text-sm">푸드트럭 공고</h4>
          <Link href="/owner/all/announcement">
            <button
              type="button"
              className="flex items-center text-xs font-normal"
            >
              전체보기
              <GoChevronRight />
            </button>
          </Link>
        </div>
        <div className="flex flex-col border border-gray-medium rounded-xl px-2 pt-3 pb-2">
          <h5 className="text-primary-500 text-xs pl-2">최신 공고</h5>
          {announcementData?.getAnnouncementListDTOList
            .slice(0, 2)
            .map((announcement, index) => {
              return (
                <div
                  onClick={() => {
                    const queryString = encodeURIComponent(
                      JSON.stringify(announcement)
                    )
                    router.push(
                      `/owner/all/announcement/${index}?value=${queryString}`
                    )
                  }}
                  className="cursor-pointer text-text flex flex-col gap-2 border-b py-4 px-2 last:border-b-0"
                  key={`${announcement.eventName} ${announcement.eventPlace}`}
                >
                  <div className="flex justify-between items-center">
                    <div className="flex gap-4 items-center">
                      <p className="font-medium">
                        {announcement.recruitPostTitle}
                      </p>
                      <span className="whitespace-nowrap flex py-1 items-center text-xs border border-primary-500 rounded-full text-primary-500 px-5 text-[10px] font-semibold">
                        {(
                          announcement.recruitPostTitle as unknown as string
                        )?.slice(0, 4) === '-마감-'
                          ? '모집마감'
                          : '모집중'}
                      </span>
                    </div>
                    <GoChevronRight />
                  </div>
                  <ul className="text-xs flex flex-col gap-2 font-normal">
                    <li className="flex">
                      <span className="w-14">운영장소</span>
                      <p>{announcement.eventPlace}</p>
                    </li>
                    <li className="flex">
                      <span className="w-14">행사일</span>
                      <p>{announcement.eventDays}</p>
                      {/* <p>2024년 10월 4일</p> */}
                    </li>
                  </ul>
                </div>
              )
            })}
        </div>
      </section>

      <hr className="h-1 border-none bg-gray-medium" />

      <section className="flex flex-col items-start pt-6 px-8 text-text">
        <h4 className="font-medium text-gray-dark mb-6 ">회원 관리</h4>
        <div className="flex flex-col gap-8">
          <button type="button" className="font-normal">
            로그아웃
          </button>
          <button type="button" className="font-normal">
            회원 탈퇴
          </button>
        </div>
      </section>
    </div>
  )
}
