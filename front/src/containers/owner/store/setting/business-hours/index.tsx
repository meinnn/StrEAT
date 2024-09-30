import AppBar from '@/components/AppBar'
import StoreBusinessSchedulePicker from '@/components/StoreBusinessSchedulePicker'

export default function OwnerStoreSettingBusinessHours() {
  return (
    <div>
      <AppBar title="점포 설정" />
      <main className=" mt-7 flex flex-col gap-7">
        <div className="px-7">
          <StoreBusinessSchedulePicker />
          <section className="flex flex-col gap-2">
            <h3 className="text-xl font-medium">휴무일</h3>
            <textarea className="h-20 resize-none border rounded-lg border-gray-dark " />
          </section>
        </div>
        <div className="p-4 fixed bottom-0 w-full bg-white">
          <button className="py-4 w-full bg-primary-500 text-secondary-light rounded-lg">
            수정하기
          </button>
        </div>
      </main>
    </div>
  )
}
