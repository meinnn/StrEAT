import {
  MdOutlineRadioButtonChecked,
  MdOutlineRadioButtonUnchecked,
} from 'react-icons/md'
import React from 'react'

interface RadioButtonProps {
  id: string
  checked: boolean
  onChange: (checked: boolean) => void
  label?: string
  name: string // 라디오 버튼은 그룹화가 필요하므로 name 속성 추가
  size?: number
}

export default function RadioButton({
  id,
  checked,
  onChange,
  label,
  name,
  size = 24, // 기본값 설정
}: RadioButtonProps) {
  return (
    <label htmlFor={id} className="flex items-center cursor-pointer">
      <input
        type="radio"
        id={id}
        name={name} // 그룹화를 위한 name 속성
        checked={checked}
        onChange={() => onChange(true)} // 라디오 버튼은 true만 전달
        className="peer sr-only" // 실제 input은 숨김
      />
      <div className="grid items-center">
        {/* 체크 상태에 따라 아이콘 변경 */}
        {checked ? (
          <MdOutlineRadioButtonChecked
            className="text-primary-500"
            size={size}
          />
        ) : (
          <MdOutlineRadioButtonUnchecked
            className="text-gray-dark"
            size={size}
          />
        )}
      </div>
      <span className="ms-2">{label}</span> {/* 라벨 텍스트 */}
    </label>
  )
}
