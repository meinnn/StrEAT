'use client'

import { useState } from 'react'
import AssetCalendar from '@/containers/owner/store-management/AssetCalendar'
import BuisenessAreaReport from '@/containers/owner/store-management/BuisenessAreaReport'
import SalesManagement from '@/containers/owner/store-management/SalesManagement'
import OwnerTabList from '@/components/OwnerTabList'

const TAB_LIST = [
  { id: 'asset-calendar', name: '자산 캘린더', href: '' },
  { id: 'sales-management', name: '매출 관리', href: '' },
  { id: 'buiseness-area-report', name: '상권 리포트', href: '' },
]

const renderView = (activeTab: string): React.ReactNode => {
  switch (activeTab) {
    case 'asset-calendar':
      return <AssetCalendar />
    case 'buiseness-area-report':
      return <BuisenessAreaReport />
    case 'sales-management':
      return <SalesManagement />
    default:
      return null
  }
}

export default function StoreManagement() {
  const [selectedTab, setSelectedTab] = useState(TAB_LIST[0].id)
  return (
    <div className="bg-white flex justify-center max-w-full">
      <div className="bg-white w-full max-w-full mx-auto flex flex-col">
        <OwnerTabList
          tabList={TAB_LIST}
          selectedTab={selectedTab}
          setSelectedTab={setSelectedTab}
        />
        {renderView(selectedTab)}
      </div>
    </div>
  )
}
