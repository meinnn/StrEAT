import Skeleton from 'react-loading-skeleton'
import 'react-loading-skeleton/dist/skeleton.css'

export default function ReviewSkeleton() {
  return (
    <>
      <Skeleton width={150} height={30} className="ml-5 mt-8 mb-1" />
      {new Array(2)
        .fill(0)
        .map((_, index) => index + 1)
        .map((value) => {
          return (
            <section key={value} className="px-5 pb-5">
              <div className="flex py-3 items-center gap-3 relative">
                <Skeleton className="relative shrink-0 w-10 h-10 aspect-square rounded-md overflow-hidden border border-gray-medium bg-gray-light" />
                <Skeleton width={150} className="h-10" />
              </div>
              <Skeleton className="w-full h-6" />
              <Skeleton className="w-full aspect-video" />

              <div className="mb-4 mt-2">
                <p className="font-normal text-text" />
              </div>
              <Skeleton className="flex flex-wrap gap-2 h-10 w-full" />
            </section>
          )
        })}
    </>
  )
}
