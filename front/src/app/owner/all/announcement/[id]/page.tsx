'use client'

import { useSearchParams } from 'next/navigation'
import Image from 'next/image'
import { useEffect, useState } from 'react'
import AppBar from '@/components/AppBar'

interface Announcement {
  entryConditions: string // 조건
  eventDays: string // 행사날
  eventName: string // 행사명
  eventPlace: string // 행사장소
  eventTimes: string // 행사시간
  recruitPostTitle: number // 포스트이름
  recruitSize: string // 모집 대수
  specialNotes: string // 주요 내용
}
export default function AnnouncementDetail() {
  const searchParams = useSearchParams()
  const dataString = searchParams.get('value')
  const announcement: Announcement = dataString
    ? JSON.parse(decodeURIComponent(dataString))
    : null
  const [isVisible, setIsVisible] = useState(false)

  const handleClickDownloadBtn = async () => {
    const response = await fetch(`/services/announcements/create-doc`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        eventName: announcement.eventName,
      }),
    })

    const file = await response.blob()

    const url = window.URL.createObjectURL(file)
    const link = document.createElement('a')
    link.href = url

    link.setAttribute('download', '입점신청서.docx')
    document.body.appendChild(link)
    link.click()

    document.body.removeChild(link)
    window.URL.revokeObjectURL(url)

    if (!response.ok) {
      console.error('파일 다운로드에 실패했습니다')
    }
  }

  useEffect(() => {
    if (
      (announcement.recruitPostTitle as unknown as string).slice(0, 4) ===
      '-마감-'
    ) {
      setIsVisible(true)
    }

    const timer = setTimeout(() => {
      setIsVisible(false)
    }, 3000)

    return () => clearTimeout(timer)
  }, [announcement.recruitPostTitle])

  return (
    <div>
      <AppBar title="푸드트럭 공고" />
      <div className="bg-secondary-medium pb-32 px-3 pt-7 text-text">
        <div
          className={`flex border justify-center items-center gap-2 fixed top-0 inset-x-0 m-4 z-50 px-4 py-10 text-xl rounded-lg shadow transition-all duration-500 ease-in-out
        ${
          isVisible
            ? 'transform translate-y-10 opacity-100 bg-primary-500 text-secondary-light'
            : 'transform -translate-y-full opacity-0'
        }
        `}
        >
          이미 마감된 공고입니다.
          <Image
            src="/images/no_content_illustration.png"
            width={50}
            height={50}
            alt="일러스트 사진"
          />
        </div>
        <div className="flex flex-col bg-white px-10 py-7 rounded-xl overflow-hidden">
          <h1 className="font-bold px-3">{announcement.recruitPostTitle}</h1>
          <hr className="my-4 bg-text h-[1px] border-none" />
          <div className="flex flex-col gap-5 px-3">
            <div className="flex gap-3 items-center">
              <div className="flex gap-2 items-center">
                <Image
                  src="/images/truck.png"
                  height={30}
                  width={30}
                  alt="트럭 아이콘"
                  priority
                />
                <span className="font-semibold">모집 트럭 수</span>
              </div>
              <span className="text-xl font-semibold">
                {announcement.recruitSize}
              </span>
            </div>
            <div className="flex flex-col gap-3 mb-5">
              <div className="flex items-center">
                <span className="w-14 font-semibold text-lg">행사명</span>
                <p>{announcement.eventName}</p>
              </div>
              <div className="flex items-center">
                <span className="w-14 font-semibold text-lg">장소</span>
                <p>{announcement.eventPlace}</p>
              </div>
              <div className="flex items-center">
                <span className="w-14 font-semibold text-lg">날짜</span>
                <p>{announcement.eventDays}</p>
              </div>
              <div className="flex items-center">
                <span className="w-14 font-semibold text-lg">시간</span>
                <p>{announcement.eventTimes}</p>
              </div>
              <div className="flex items-center">
                <span className="w-14 font-semibold text-lg">조건</span>
                <p>{announcement.entryConditions}</p>
              </div>
            </div>
            <p className="text-sm">{announcement.specialNotes} </p>
          </div>
        </div>
        <div className="w-full pt-8 px-8">
          <h1 className="font-bold text-xl mb-6">
            이 공고에 지원하고 싶으신가요?
          </h1>
          <p className="mb-4">입점 신청서를 작성해서 이메일로 보내주세요</p>
          <div className="flex gap-4 mb-3">
            <span className="w-20 font-semibold">이메일</span>
            <p>kftafestival@gmail.com</p>
          </div>
          <div className="flex gap-4 mb-9">
            <span className=" w-20 font-semibold">이메일 제목</span>
            <p>신청행사명-푸드트럭명-대표자 성명</p>
          </div>
          <Image
            src="/images/foodtruck_announcement.jpg"
            alt="푸드트럭 입점 신청서 파일"
            layout="responsive"
            width={700}
            height={500}
            className="object-contain"
          />
          <div className="flex flex-col gap-2 mt-7 items-center text-text">
            <button
              onClick={handleClickDownloadBtn}
              className="bg-primary-500 rounded-md py-4 w-full text-secondary-light"
            >
              입점 신청서 다운로드
            </button>
            <p className="text-xs">
              당신의 기본 인적 정보가 포함된 입점 신청서를 다운받으실 수
              있습니다
            </p>
          </div>
        </div>
      </div>
    </div>
  )
}
