import Link from 'next/link'
import { useRouter } from 'next/navigation'
import { FaStar } from 'react-icons/fa'
import { GoChevronRight } from 'react-icons/go'
import { HiOutlineLocationMarker, HiOutlineSpeakerphone } from 'react-icons/hi'
import { IoSettingsOutline } from 'react-icons/io5'
import { TbPhone } from 'react-icons/tb'

export default function StoreInformation({
  name,
  status,
  address,
  phoneNum,
  ownerWord,
  type,
  review,
}: {
  name: string
  status: string
  address: string
  phoneNum: string
  ownerWord: string
  type: string
  review: {
    averageScore: number
    reviewTotalCount: number
  }
}) {
  const router = useRouter()

  return (
    <section className="flex flex-col px-6 pt-6">
      <div className="flex flex-col gap-1 mb-2">
        <div className="flex items-end justify-between ">
          <span className="text-xs font-normal pl-1">#치킨</span>
          <Link href="/owner/store/setting">
            <div className="flex gap-1 items-center">
              <IoSettingsOutline className="shrink-0" />
              <p>점포설정</p>
            </div>
          </Link>
        </div>
        <h1 className="text-2xl font-bold">{name}</h1>
      </div>
      <div className="flex flex-col gap-3 mb-5">
        <div className="flex justify-between items-center">
          <div className="flex gap-4">
            <div className="flex gap-[2px] items-center">
              <FaStar className="text-yellow-400" />
              <span>{review?.averageScore || 0}</span>
            </div>
            <Link href="/owner/store/review">
              <div className="flex gap items-center gap-1">
                <p>리뷰 {review?.reviewTotalCount || 0}개</p>
                <GoChevronRight />
              </div>
            </Link>
          </div>
          <div className="flex gap-1 items-center">
            <span
              className={`${status === '영업중' ? 'bg-green-500' : 'bg-red-500'} h-2 w-2 block rounded-full`}
            />
            <p className="font-normal pr-2">{status}</p>
          </div>
        </div>
        <div className="flex justify-between items-start gap-2">
          <div className="flex flex-col gap-2">
            <div className="flex gap-[2px] items-start">
              <HiOutlineLocationMarker className="shrink-0 text-primary-500 w-4 h-4" />
              <p className="text-xs font-normal flex">{address}</p>
            </div>
            <div className="flex gap-1 items-center">
              <TbPhone className="shrink-0 text-primary-500 w-4 h-4" />
              <p className="text-xs font-normal">{phoneNum}</p>
            </div>
          </div>
          <button
            type="button"
            onClick={() => {
              if (status === '준비중') {
                if (type === '고정형') {
                  router.push(`/owner/store/open?address=${address}`)
                } else {
                  router.push('/select-business-location')
                }
              } else {
                router.push('/owner/store/close')
                console.log('상태가 영업중입니다.')
              }
            }}
            className={`whitespace-nowrap rounded-full font-semibold py-1 px-5 border ${status === '영업중' ? 'border-green-500 text-green-500' : 'border-[#3e3e3e] text-[#3e3e3e] hover:text-primary-500 hover:border-primary-500'}`}
          >
            {status === '영업중' ? '영업 종료하기' : '영업 시작하기'}
          </button>
        </div>
      </div>
      <div className="flex flex-col gap-2">
        <div className="py-3 px-4 border-gray-dark border rounded-lg">
          <p
            className={`${ownerWord.length > 0 ? 'text-red-500 ' : 'text-gray-dark'} flex gap-2 items-center text-xs`}
          >
            <HiOutlineSpeakerphone className="text-red-500 w-4 h-4" />
            {ownerWord.length > 0
              ? ownerWord
              : '등록된 사장님의 한 마디가 없습니다'}
          </p>
        </div>
        <Link href="/owner/store/setting/announcement">
          <button
            type="button"
            className="rounded-md text-xs font-normal bg-primary-500 py-2 w-full text-secondary-light"
          >
            사장님 한 마디 수정
          </button>
        </Link>
      </div>
    </section>
  )
}
