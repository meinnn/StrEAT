import Image from 'next/image'

export default function EmptyCart() {
  return (
    <div className="flex justify-center items-center h-80 flex-col gap-1">
      <div className="relative w-40 aspect-square">
        <Image
          src="/images/no_content_illustration.png"
          className="object-cover"
          alt="내용이 없다는 일러스트"
          fill
          priority
        />
      </div>
      <p className="text-text font-bold">장바구니에 담은 메뉴가 없습니다</p>
    </div>
  )
}
