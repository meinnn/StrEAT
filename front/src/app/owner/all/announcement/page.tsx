'use client'

import { useQuery } from '@tanstack/react-query'
import { useRouter } from 'next/navigation'
import AppBar from '@/components/AppBar'

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
export default function Announcement() {
  const router = useRouter()
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

  if (isLoading) {
    return <p>로딩중</p>
  }

  if (error) {
    return <p>에러 발생</p>
  }

  return (
    <div>
      <AppBar title="푸드트럭 공고" />
      <div className="bg-secondary-light pb-32">
        <div className="flex flex-col px-7">
          {announcementData?.getAnnouncementListDTOList &&
            announcementData?.getAnnouncementListDTOList?.length > 0 &&
            announcementData?.getAnnouncementListDTOList.map(
              (announcement, index) => {
                const deadline = (
                  announcement.recruitPostTitle as unknown as string
                ).slice(0, 4)

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
                    className="cursor-pointer text-text flex flex-col gap-2 border-b py-5 px-2 last:border-b-0"
                    key={`${announcement.eventName} ${announcement.eventPlace}`}
                  >
                    <div className="flex justify-between items-center">
                      <div className="flex gap-2 items-start">
                        <p className="font-medium text-sm">
                          {deadline === '-마감-'
                            ? (
                                announcement.recruitPostTitle as unknown as string
                              ).slice(
                                4,
                                (
                                  announcement.recruitPostTitle as unknown as string
                                ).length
                              )
                            : announcement.recruitPostTitle}
                        </p>
                        <span
                          className={`${deadline === '-마감-' ? 'border-gray-dark text-gray-dark' : 'border-primary-500 text-primary-500'} rounded-full flex items-center py-[2px] text-xs whitespace-nowrap border  px-3 text-[10px] font-semibold`}
                        >
                          {deadline === '-마감-' ? '모집마감' : '모집중'}
                        </span>
                      </div>
                    </div>
                    <ul className="text-xs flex flex-col gap-2 font-normal">
                      <li className="flex">
                        <span className="w-14">운영장소</span>
                        <p>{announcement.eventPlace}</p>
                      </li>
                      <li className="flex">
                        <span className="w-14">행사일</span>
                        <p>{announcement.eventDays}</p>
                      </li>
                    </ul>
                  </div>
                )
              }
            )}
        </div>
      </div>
    </div>
  )
}
