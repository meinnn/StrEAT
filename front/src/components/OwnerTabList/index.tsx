'use client'

interface OwnerTab {
  id: string
  name: string
  href: string
}

export default function OwnerTabList({
  tabList,
  selectedTab,
  setSelectedTab,
}: {
  tabList: OwnerTab[]
  selectedTab: string
  setSelectedTab: React.Dispatch<React.SetStateAction<string>>
}) {
  return (
    <ul className="flex sticky top-0 bg-white z-50 border-b border-gray-medium">
      {tabList.map((tab) => {
        return (
          <li
            key={tab.id}
            className={`${tab.id === selectedTab ? 'bg-primary-400 text-secondary-light' : 'bg-gray-medium text-[#6D6D6D]'} flex justify-center  px-6 pt-3 pb-2 min-w-28 rounded-t-[10px] cursor-pointer`}
            onClick={() => {
              setSelectedTab(tab.id)
            }}
          >
            <p className="font-bold">{tab.name}</p>
          </li>
        )
      })}
    </ul>
  )
}
