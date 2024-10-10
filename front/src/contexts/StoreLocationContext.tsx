'use client'

import { createContext, useContext, useState, ReactNode, useMemo } from 'react'

const StoreLocationContext = createContext<any>(null)

export function StoreLocationProvider({ children }: { children: ReactNode }) {
  const [storeLocation, setStoreLocation] = useState({
    latitude: null,
    longitude: null,
    address: null,
  })

  const value = useMemo(
    () => ({ storeLocation, setStoreLocation }),
    [storeLocation, setStoreLocation]
  )

  return (
    <StoreLocationContext.Provider value={value}>
      {children}
    </StoreLocationContext.Provider>
  )
}

export function useStoreLocation() {
  const context = useContext(StoreLocationContext)
  if (!context) {
    throw new Error(
      'useStoreLocation must be used within a StoreLocationProvider'
    )
  }
  return context
}
