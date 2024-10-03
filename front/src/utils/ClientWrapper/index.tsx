'use client'

// 클라이언트 사이드 페이지에서 쓰일 Wrapper
import { QueryClientProvider, QueryClient } from '@tanstack/react-query'
import { useState } from 'react'

export default function ClientWrapper({
  children,
}: {
  children: React.ReactNode
}) {
  const [queryClient] = useState(() => new QueryClient())
  return (
    <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>
  )
}
