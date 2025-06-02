export default function SkeletonList() {
  return (
    <ul className="animate-pulse space-y-4">
      {[1, 2, 3].map((i) => (
        <li
          key={i}
          className="flex justify-between items-center border-b border-gray-200 py-4"
        >
          <div className="w-full">
            <div className="h-4 bg-gray-300 rounded w-1/3 mb-2" />
            <div className="h-3 bg-gray-200 rounded w-2/3" />
          </div>
        </li>
      ))}
    </ul>
  )
}
