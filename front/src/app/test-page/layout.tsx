import { ReactNode } from 'react'

export default function TestPageLayout({ children }: { children: ReactNode }) {
  return (
    <div>
      <h1>Test Page Layout</h1>
      <main className="w-full bg-amber-200">{children}</main>
    </div>
  )
}
