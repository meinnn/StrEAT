'use client'

import OrderProcessing from '@/containers/owner/order-management/OrderProcessing'
import OrderInquiry from '@/containers/owner/order-management/OrderInquiry'
import OrderComplete from '@/containers/owner/order-management/OrderComplete'
import OwnerTabList from '@/components/OwnerTabList'
import { useState } from 'react'

const TAB_LIST = [
  { id: 'order-processing', name: '신규, 처리중 0', href: '' },
  { id: 'order-complete', name: '완료 1', href: '' },
  { id: 'order-inquiry', name: '주문 조회', href: '' },
]

const renderView = (activeTab: string): React.ReactNode => {
  switch (activeTab) {
    case 'order-processing':
      return <OrderProcessing />
    case 'order-complete':
      return <OrderComplete />
    case 'order-inquiry':
      return <OrderInquiry />
    default:
      return null
  }
}

export default function OrderManagement() {
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
