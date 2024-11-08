import Skeleton from 'react-loading-skeleton'
import 'react-loading-skeleton/dist/skeleton.css'

export default function CartSkeletonPage() {
  return (
    <div
      className="pb-20 p-4 flex flex-col justify-between"
      style={{ minHeight: 'calc(100vh - 4rem)' }}
    >
      {/* Store Information Skeleton */}
      <div>
        <div className="flex items-center space-x-4 mb-4">
          <Skeleton circle height={50} width={50} />
          <div>
            <Skeleton height={20} width={200} />
          </div>
        </div>

        {/* Cart Items Skeleton */}
        {Array.from({ length: 2 }).map((_, index) => (
          <div
            key={Math.random()}
            className="border border-gray-medium rounded-lg p-4 mb-2 flex flex-col"
          >
            <div className="flex justify-between">
              <Skeleton circle height={24} width={24} />
              <Skeleton height={24} width={24} />
            </div>

            <div className="ms-10 mt-2">
              <div className="flex items-start space-x-4">
                <Skeleton width={80} height={80} className="rounded-lg" />
                <div>
                  <Skeleton width={120} height={20} />
                  <Skeleton width={40} height={15} />
                  <Skeleton width={100} height={15} count={2} />
                </div>
              </div>
            </div>

            <div className="ms-10 mt-4 flex justify-between items-center">
              <Skeleton width={80} height={30} />
              <Skeleton width={50} height={20} />
            </div>
          </div>
        ))}

        {/* Add Menu Button Skeleton */}
        <div className="w-full py-2">
          <Skeleton height={40} />
        </div>
      </div>

      {/* Estimated Price Section Skeleton */}
      <div>
        <Skeleton height={30} width={200} className="mb-2" />
        <div className="border border-gray-medium rounded-lg p-4 mt-2">
          <div className="flex justify-between mb-2">
            <Skeleton height={20} width={120} />
            <Skeleton height={20} width={50} />
          </div>
          <div className="flex justify-between">
            <Skeleton height={20} width={120} />
            <Skeleton height={20} width={70} />
          </div>
        </div>
      </div>

      {/* Checkout Button Skeleton */}
      <div className="fixed bottom-0 inset-x-0 p-3 bg-white">
        <Skeleton height={50} width="100%" />
      </div>
    </div>
  )
}
