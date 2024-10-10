'use client'

// 클라이언트 사이드 페이지에서 쓰일 Wrapper
import { QueryClientProvider } from '@tanstack/react-query'
import getQueryClient from '../getQueryClient'

export default function ClientWrapper({
  children,
}: {
  children: React.ReactNode
}) {
  const queryClient = getQueryClient()

  return (
    <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>
  )
}
