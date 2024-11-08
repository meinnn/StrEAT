'use client'

import {
  createContext,
  useContext,
  useState,
  ReactNode,
  useMemo,
  Dispatch,
  SetStateAction,
} from 'react'

interface StoreRegistInfo {
  businessRegistrationNumber: string | null
  Account: string | null
}

interface StoreRegistContextType {
  storeRegistInfo: StoreRegistInfo
  setStoreRegistInfo: Dispatch<SetStateAction<StoreRegistInfo>>
}

const StoreRegistContext = createContext<StoreRegistContextType | null>(null)

export function StoreRegistProvider({ children }: { children: ReactNode }) {
  const [storeRegistInfo, setStoreRegistInfo] = useState<StoreRegistInfo>({
    businessRegistrationNumber: null,
    Account: null,
  })

  const value = useMemo(
    () => ({ storeRegistInfo, setStoreRegistInfo }),
    [storeRegistInfo, setStoreRegistInfo]
  )

  return (
    <StoreRegistContext.Provider value={value}>
      {children}
    </StoreRegistContext.Provider>
  )
}

export function useStoreReigstInfo() {
  const context = useContext(StoreRegistContext)
  if (!context) {
    throw new Error(
      'useStoreRegistInfo must be used within a StoreRegistProvider'
    )
  }
  return context
}
