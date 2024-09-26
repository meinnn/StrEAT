'use client'

import { FaArrowLeft } from 'react-icons/fa'
import MenuAddDetails from '@/containers/owner/store/menu/menu-add/MenuAddDetails'
import MenuAddOptions from '@/containers/owner/store/menu/menu-add/MenuAddOptions'

export default function MenuAdd() {
  return (
    <div>
      <div className="max-w-xl mx-auto flex flex-col">
        {/* 메뉴 추가 타이틀 */}
        <div className="relative mb-6 mt-4 w-full flex items-center">
          {/* relative 위치 지정 */}
          <button className="text-xl text-primary-950 absolute left-4">
            {/* 버튼을 왼쪽에 고정 */}
            <FaArrowLeft />
          </button>
          <h1 className="text-2xl font-bold text-primary-950 mx-auto">
            메뉴 추가
          </h1>
          {/* 중앙 정렬 */}
        </div>

        {/* 메뉴 세부 정보 */}
        <div className="w-full">
          <MenuAddDetails />
        </div>

        {/* 옵션 관리 */}
        <div className="w-full">
          <MenuAddOptions />
        </div>
      </div>
    </div>
  )
}
