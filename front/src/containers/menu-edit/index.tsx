'use client'

import MenuEditDetails from '@/containers/menu-edit/MenuEditDetails'
import MenuEditOptions from '@/containers/menu-edit/MenuEditOptions'
import { FaArrowLeft } from 'react-icons/fa'

export default function MenuEdit() {
  return (
    <div>
      <div className="max-w-xl mx-auto flex flex-col">
        {/* 메뉴 추가 타이틀 */}
        <div className="relative mb-6 mt-4 w-full flex items-center">
          {' '}
          {/* relative 위치 지정 */}
          <button className="text-xl text-primary-950 absolute left-4">
            {' '}
            {/* 버튼을 왼쪽에 고정 */}
            <FaArrowLeft />
          </button>
          <h1 className="text-2xl font-bold text-primary-950 mx-auto">
            메뉴 수정
          </h1>{' '}
          {/* 중앙 정렬 */}
        </div>

        {/* 메뉴 세부 정보 */}
        <div className="w-full">
          <MenuEditDetails />
        </div>

        {/* 옵션 관리 */}
        <div className="w-full">
          <MenuEditOptions />
        </div>
      </div>
    </div>
  )
}
