import OwnerTabList from '@/components/OwnerTabList'

export default function OwnerLayout({ tabList, children }: any) {
  return (
    <div>
      <OwnerTabList tabList={tabList} />
      <div>{children}</div>
    </div>
  )
}
