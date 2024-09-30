import Image from 'next/image'
import { IoSettingsOutline } from 'react-icons/io5'
import Link from 'next/link'
import AppBar from '@/components/AppBar'

export default function OwnerStoreBusinessLocationPage() {
  return (
    <div>
      <AppBar title="점포설정" />
      <main className="flex flex-col gap-3 px-6 py-5">
        <div className="flex gap-1 items-center">
          <h3 className="pl-2 text-xl font-medium ">영업 위치</h3>
          <Link href="/owner/store/setting/business-location">
            <IoSettingsOutline className="h-5 w-5" />
          </Link>
        </div>
        <div className="flex justify-center items-start w-full">
          <div className="grid grid-cols-3 grid-rows-2 gap-4 place-items-center w-full pb-36">
            {new Array(8).fill(0).map((_, index) => {
              return (
                <div
                  key={_}
                  className="relative w-full aspect-square max-w-[114px] overflow-hidden bg-gray-medium rounded-lg"
                >
                  <Image
                    src="/images/보쌈사진.jpg"
                    alt="store-location"
                    fill
                    className="absolute object-cover"
                    priority
                  />
                </div>
              )
            })}
          </div>
        </div>
      </main>
    </div>
  )
}
