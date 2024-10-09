import { ReactNode } from 'react'

export default function MenuLayout({
  children,
  alert,
}: {
  children: ReactNode
  alert: ReactNode
}) {
  return (
    <>
      {alert}
      <main>{children}</main>
    </>
  )
}
