'use client'

import { useState } from 'react'
import MapView from '@/containers/customer/home/MapView'
import ListView from '@/containers/customer/home/ListView'

export default function CustomerHomeContainer() {
  const [view, setView] = useState<'map' | 'list'>('map') // 'map' or 'list' 상태로 관리
  const [currentAddress, setCurrentAddress] = useState<string>('')

  return (
    <div>
      {view === 'map' ? (
        <MapView
          setView={setView}
          currentAddress={currentAddress}
          setCurrentAddress={setCurrentAddress}
        />
      ) : (
        <ListView setView={setView} currentAddress={currentAddress} />
      )}
    </div>
  )
}
