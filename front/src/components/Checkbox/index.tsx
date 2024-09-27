import { MdCheckBox, MdCheckBoxOutlineBlank } from 'react-icons/md'
import React from 'react'

interface CheckboxProps {
  id: string
  checked: boolean
  onChange: (checked: boolean) => void
  label?: string
  size?: number // size는 선택적
}

export default function Checkbox({
  id,
  checked,
  onChange,
  label,
  size = 18,
}: CheckboxProps) {
  return (
    <label htmlFor={id} className="flex items-center cursor-pointer">
      <input
        type="checkbox"
        id={id}
        checked={checked}
        onChange={(e) => onChange(e.target.checked)}
        className="peer sr-only" // 실제 input은 화면에서 숨김
      />
      <div className="grid items-center">
        {/* 체크 상태에 따라 아이콘 변경 */}
        {checked ? (
          <MdCheckBox className="text-primary-500" size={size} />
        ) : (
          <MdCheckBoxOutlineBlank className="text-gray-dark" size={size} />
        )}
      </div>
      <span className="ms-2">{label}</span> {/* 라벨 텍스트 */}
    </label>
  )
}
