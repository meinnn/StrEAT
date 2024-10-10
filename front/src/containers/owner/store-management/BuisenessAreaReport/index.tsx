'use client'

import { useState } from 'react'
import TopThree from '@/containers/owner/store-management/BuisenessAreaReport/TopThree'
import Analysis from '@/containers/owner/store-management/BuisenessAreaReport/Analysis'
import NewSpot from '@/containers/owner/store-management/BuisenessAreaReport/NewSpot'

export default function BusinessAreaReport() {
  const [activeTab, setActiveTab] = useState('TopThree')

  return (
    <div className="w-full h-full">
      {/* 상단 탭을 지도 위에 고정 */}
      <div className="flex mr-4 p-4 absolute top-10 left-0 right-0 z-10">
        <div
          onClick={() => setActiveTab('TopThree')}
          className={`${
            activeTab === 'TopThree'
              ? 'bg-[#FF4365] text-white'
              : 'bg-[#f5f2ea] text-[#FF4365]'
          } px-6 py-2 mr-2 rounded-full text-center shadow-lg text-sm font-semibold cursor-pointer`}
        >
          스팟 별 top 3
        </div>
        <div
          onClick={() => setActiveTab('Analysis')}
          className={`${
            activeTab === 'Analysis'
              ? 'bg-[#FF4365] text-white'
              : 'bg-[#f5f2ea] text-[#FF4365]'
          } px-4 py-2 mr-2 rounded-full text-center shadow-lg text-sm font-semibold cursor-pointer`}
        >
          주변 상권 분석
        </div>
        {/* <div
          onClick={() => setActiveTab('NewSpot')}
          className={`${
            activeTab === 'NewSpot'
              ? 'bg-[#FF4365] text-white'
              : 'bg-[#f5f2ea] text-[#FF4365]'
          } px-4 py-2 rounded-full text-center shadow-lg text-sm font-semibold cursor-pointer`}
        >
          새로운 스팟 추천
        </div> */}
      </div>

      {/* 지도 아래에 배치 */}
      <div className="absolute top-10 left-0 right-0 bottom-0">
        {activeTab === 'TopThree' && <TopThree />}
        {activeTab === 'Analysis' && <Analysis />}
        {activeTab === 'NewSpot' && <NewSpot />}
      </div>
    </div>
  )
}
