import Skeleton from 'react-loading-skeleton'
import 'react-loading-skeleton/dist/skeleton.css'

export default function StoreSkeleton() {
  return (
    <div className="pb-8 flex flex-col">
      <Skeleton className="h-40 w-full" />
      <section className="flex flex-col px-6 pt-6">
        <div className="flex flex-col gap-1 mb-2">
          <div className="flex items-end justify-between ">
            <span className="text-xs font-normal pl-1">
              <Skeleton width={50} />
            </span>
            <div className="flex gap-1 items-center">
              <Skeleton width={80} />
            </div>
          </div>
          <h1 className="text-2xl font-bold">
            <Skeleton width={200} />
          </h1>
        </div>
        <div className="flex flex-col gap-3 mb-5">
          <div className="flex justify-between items-center">
            <div className="flex gap-4">
              <div className="flex gap-[2px] items-center">
                <Skeleton width={20} />
                <Skeleton width={20} />
              </div>
              <div className="flex gap items-center gap-1">
                <Skeleton width={100} />
              </div>
            </div>
            <div className="flex gap-1 items-center">
              <Skeleton width={50} />
              <Skeleton width={50} />
            </div>
          </div>
          <div className="flex justify-between items-start gap-2">
            <div className="flex flex-col gap-2">
              <div className="flex gap-[2px] items-start">
                <Skeleton width={150} />
                <Skeleton width={150} />
              </div>
              <div className="flex flex-col w-full gap-1 items-center">
                <Skeleton className="w-full" height={60} />
                <Skeleton className="w-full" height={60} />
              </div>
            </div>
          </div>
        </div>
        <div className="flex flex-col gap-2">
          <div className="py-3 px-4 rounded-lg">
            <Skeleton width={200} />
            <Skeleton width={200} />
          </div>
          <Skeleton width={150} />
        </div>
      </section>
      <div className="px-5">
        <div className="mt-5">
          <Skeleton width={200} height={30} className="mb-4" />
          <Skeleton className="w-full" height={60} />
        </div>
        <div className="mt-5">
          <Skeleton width={200} height={30} className="mb-4" />
          <Skeleton className="w-full" height={300} />
        </div>
      </div>
    </div>
  )
}
