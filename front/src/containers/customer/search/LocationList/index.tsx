export default function LocationList({
  items,
}: {
  items: { title: string; address: string; mapx: string; mapy: string }[]
}) {
  return (
    <div className="mt-6 px-4">
      <ul>
        {items.map((item) => (
          <li
            key={`${item.mapx}-${item.mapy}`}
            className="flex justify-between items-center border-b border-gray-200 py-4"
          >
            <div>
              <p className="font-semibold mb-1">{item.title}</p>
              <p className="text-xs text-primary-950">{item.address}</p>
            </div>
          </li>
        ))}
      </ul>
    </div>
  )
}
