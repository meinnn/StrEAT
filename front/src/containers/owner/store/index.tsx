'use client'

import { useState } from 'react'
import OwnerTabList from '@/components/OwnerTabList'
import Store from '@/containers/owner/store/Store'
import StoreMenu from '@/containers/owner/store/menu/store-menu'

const TAB_LIST = [
  { id: 'store-info', name: '내 점포 정보', href: '/store' },
  { id: 'store-menu', name: '메뉴 정보', href: '/menu' },
]

const renderView = (activeTab: string): React.ReactNode => {
  switch (activeTab) {
    case 'store-info':
      return <Store />
    case 'store-menu':
      return <StoreMenu />
    default:
      return null
  }
}

export default function OwnerStore() {
  const [selectedTab, setSelectedTab] = useState(TAB_LIST[0].id)

  return (
    <div>
      <OwnerTabList
        tabList={TAB_LIST}
        selectedTab={selectedTab}
        setSelectedTab={setSelectedTab}
      />
      {renderView(selectedTab)}
    </div>
  )
}
