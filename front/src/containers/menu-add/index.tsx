"use client"

import MenuAddDetails from "./MenuAddDetails"
import MenuAddOption from "./MenuAddOption"

const MenuAdd = () => {
  return (
    <div>
      <div className="max-w-xl mx-auto flex flex-col">
        {/* 메뉴 추가 타이틀 */}
        <div className="mb-6 mt-4 w-full">
          <h1 className="text-2xl font-bold text-primary-950 text-center">메뉴 추가</h1>
        </div>

        {/* 메뉴 세부 정보 */}
        <div className="w-full">
          <MenuAddDetails />
        </div>

        {/* 옵션 관리 */}
        <div className="w-full">
          <MenuAddOption />
        </div>
      </div>
    </div>
  )
}

export default MenuAdd
