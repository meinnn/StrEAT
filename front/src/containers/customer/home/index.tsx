'use client'

import { useState } from 'react'
import MapView from '@/containers/customer/home/MapView'
import ListView from '@/containers/customer/home/ListView'
import { Store } from '@/types/store'
import OngoingOrder from '@/containers/customer/home/OngoingOrder'

export default function CustomerHomeContainer() {
  const [view, setView] = useState<'map' | 'list'>('map')
  const [currentAddress, setCurrentAddress] =
    useState<string>('주소 불러오는 중...')
  const [storeList, setStoreList] = useState<Store[]>([])

  return (
    <div>
      {view === 'map' ? (
        <>
          <OngoingOrder />
          <MapView
            setView={setView}
            currentAddress={currentAddress}
            setCurrentAddress={setCurrentAddress}
            storeList={storeList} // storeList 전달
            setStoreList={setStoreList} // 재검색 버튼 클릭 시 실행될 함수 전달
          />
        </>
      ) : (
        <ListView
          setView={setView}
          currentAddress={currentAddress}
          storeList={storeList} // storeList 전달
        />
      )}
    </div>
  )
}
