import AppBar from '@/components/AppBar'

export default function OwnerStoreSettingAnnouncement() {
  return (
    <div>
      <AppBar title="내 점포" />
      <form className="flex flex-col gap-4 px-4 py-8">
        <h3 className="text-xl font-medium pl-3">사장님 한마디</h3>
        <textarea className="p-3 resize-none w-full border border-gray-dark rounded h-44 outline-none" />
        <div className="self-center p-4 fixed bottom-0 w-full">
          <button className="w-full py-4 bg-primary-500 text-secondary-light rounded-lg font-normal">
            수정하기
          </button>
        </div>
      </form>
    </div>
  )
}
