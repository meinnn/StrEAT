// 주문표 인쇄

export default function PrintOrderBtn() {
  const handlePrint = () => {
    // console.log('주문표 인쇄 클릭됨')
  }

  return (
    <button
      type="button"
      onClick={handlePrint}
      className="whitespace-nowrap flex justify-center items-center bg-white h-20 w-14 text-sm font-bold text-black px-3 py-2 rounded-md shadow-md"
    >
      주문표 <br />
      인쇄
    </button>
  )
}
