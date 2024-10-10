'use client'

import { useState } from 'react'
import OrderProcessing from '@/containers/owner/order-management/OrderProcessing'
import OrderInquiry from '@/containers/owner/order-management/OrderInquiry'
import OrderComplete from '@/containers/owner/order-management/OrderComplete'
import OwnerTabList from '@/components/OwnerTabList'
import { useOrderList } from '@/hooks/useOrderList'
import { useOwnerInfo } from '@/hooks/useOwnerInfo'
import { useMyStoreInfo } from '@/hooks/useMyStoreInfo'

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
  const {
    data: ownerInfo,
    error: ownerInfoError,
    isLoading: ownerInfoLoading,
  } = useOwnerInfo()
  const { data: processData } = useOrderList(ownerInfo?.storeId, 'PROCESSING')
  const { data: receivingData } = useOrderList(ownerInfo?.storeId, 'RECEIVING')

  const TAB_LIST = [
    {
      id: 'order-processing',
      name: `신규, 처리중 ${processData?.totalDataCount || 0}`,
      href: '',
    },
    {
      id: 'order-complete',
      name: `완료 ${receivingData?.totalDataCount || 0}`,
      href: '',
    },
    { id: 'order-inquiry', name: '주문 조회', href: '' },
  ]

  const [selectedTab, setSelectedTab] = useState(TAB_LIST[0].id)

  if (ownerInfoLoading) {
    return <p>로딩중</p>
  }

  if (ownerInfoError) {
    return <p>에러 발생</p>
  }
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
