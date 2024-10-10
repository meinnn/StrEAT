'use client'

import { useQueryClient } from '@tanstack/react-query'
import { useState, useEffect } from 'react'
import { useRouter } from 'next/navigation'
import AppBar from '@/components/AppBar'
import { useOwnerInfo } from '@/hooks/useOwnerInfo'
import { useMyStoreInfo } from '@/hooks/useMyStoreInfo'

export default function OwnerStoreSettingAnnouncement() {
  const router = useRouter()
  const queryClient = useQueryClient()
  const {
    data: ownerInfo,
    error: ownerInfoError,
    isLoading: ownerInfoLoading,
  } = useOwnerInfo()
  const {
    data: storeInfo,
    error,
    isLoading,
  } = useMyStoreInfo(ownerInfo?.storeId)
  const [ownerWord, setOwnerWord] = useState('')

  const handleChangeOwnerWord = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setOwnerWord(e.target.value)
  }

  const updateStoreStatus = async () => {
    const response = await fetch(
      // 사장님 한마디 변경하는 API
      `/services/store/${ownerInfo?.storeId}/ownerWord`,
      {
        method: 'PATCH',
        body: JSON.stringify(ownerWord),
      }
    )
    if (!response.ok) {
      console.error('사장님 한마디 변경에 실패했습니다.')
    }
    queryClient.invalidateQueries({ queryKey: ['/store', ownerInfo?.storeId] })
    router.back()
  }

  useEffect(() => {
    if (!storeInfo?.storeInfo.ownerWord) return
    setOwnerWord(storeInfo?.storeInfo.ownerWord)
  }, [storeInfo?.storeInfo.ownerWord])

  if (ownerInfoLoading || isLoading) return <p>로딩중</p>
  if (ownerInfoError || error) return <p>에러 발생</p>

  return (
    <div>
      <AppBar title="점포 설정" />
      <form
        className="flex flex-col gap-4 px-4 py-8"
        onSubmit={(e) => {
          e.preventDefault()
          updateStoreStatus()
        }}
      >
        <h3 className="text-xl font-medium pl-3">사장님 한마디</h3>
        <textarea
          value={ownerWord}
          onChange={handleChangeOwnerWord}
          className="p-3 resize-none w-full border border-gray-dark rounded h-44 outline-none"
        />
        <div className="self-center p-4 fixed bottom-0 w-full">
          <button className="w-full py-4 bg-primary-500 text-secondary-light rounded-lg font-normal">
            수정하기
          </button>
        </div>
      </form>
    </div>
  )
}
