'use client'

import Image from 'next/image'
import { useState } from 'react'
import { GoChevronRight } from 'react-icons/go'
import { IoCloseOutline } from 'react-icons/io5'
import Checkbox from '@/components/Checkbox'
import Link from 'next/link'
import Drawer from '@/components/Drawer'
import MenuOptions from '@/containers/customer/home/stores/menu/MenuOptions'
import StoreLink from '@/components/StoreLink'
import OrderMenuDetail from '@/components/OrderMenuDetail'

export default function CartPage() {
  // 장바구니 아이템 예시 데이터
  const [cartItems, setCartItems] = useState([
    {
      id: 1,
      name: '후라이드 치킨',
      quantity: 1,
      options: ['한마리', '양념치킨 소스'],
      price: 1000000000,
      checked: false,
      image: '/images/보쌈사진.jpg', // 실제 이미지 경로로 변경
      option_categories: [
        {
          id: 1,
          name: '부분육 선택',
          min_select: 1,
          max_select: 1, // 1개만 선택 가능 -> RadioButton
          options: [
            { id: 1, desc: '한마리' },
            { id: 2, desc: '순살 변경' },
            { id: 3, desc: '윙&봉 변경' },
          ],
        },
        {
          id: 2,
          name: '소스 추가 선택',
          is_essential: false,
          min_select: 2,
          max_select: 2, // 여러 개 선택 가능 -> Checkbox
          options: [
            { id: 4, desc: '양념치킨 소스' },
            { id: 5, desc: '스모크 소스' },
          ],
        },
        {
          id: 3,
          name: '소스 추가 선택',
          is_essential: false,
          min_select: 0,
          max_select: 2, // 여러 개 선택 가능 -> Checkbox
          options: [
            { id: 6, desc: '양념치킨 소스' },
            { id: 7, desc: '스모크 소스' },
            { id: 8, desc: '다른 소스' },
          ],
        },
      ],
    },
    {
      id: 2,
      name: '양념 치킨',
      quantity: 1,
      options: ['한마리', '양념치킨 소스'],
      price: 1000000000,
      checked: true,
      image: '/images/보쌈사진.jpg', // 실제 이미지 경로로 변경
      option_categories: [
        {
          id: 1,
          name: '부분육 선택',
          min_select: 1,
          max_select: 1, // 1개만 선택 가능 -> RadioButton
          options: [
            { id: 1, desc: '한마리' },
            { id: 2, desc: '순살 변경' },
            { id: 3, desc: '윙&봉 변경' },
          ],
        },
        {
          id: 2,
          name: '소스 추가 선택',
          is_essential: false,
          min_select: 2,
          max_select: 2, // 여러 개 선택 가능 -> Checkbox
          options: [
            { id: 4, desc: '양념치킨 소스' },
            { id: 5, desc: '스모크 소스' },
          ],
        },
        {
          id: 3,
          name: '소스 추가 선택',
          is_essential: false,
          min_select: 0,
          max_select: 2, // 여러 개 선택 가능 -> Checkbox
          options: [
            { id: 6, desc: '양념치킨 소스' },
            { id: 7, desc: '스모크 소스' },
            { id: 8, desc: '다른 소스' },
          ],
        },
      ],
    },
  ])

  const [showDrawer, setShowDrawer] = useState(false) // Drawer 상태 관리
  const [selectedItem, setSelectedItem] = useState(cartItems[0]) // 선택한 아이템 상태

  const handleItemCheck = (id: number) => {
    setCartItems((prevItems) =>
      prevItems.map((item) =>
        item.id === id ? { ...item, checked: !item.checked } : item
      )
    )
  }

  const handleRemoveItem = (id: number) => {
    setCartItems((prevItems) => prevItems.filter((item) => item.id !== id))
  }

  const handleOpenDrawer = (item: any) => {
    setSelectedItem(item) // 선택한 아이템 저장
    setShowDrawer(true) // Drawer 열기
  }

  const handleCloseDrawer = () => {
    setShowDrawer(false) // Drawer 닫기
  }

  const totalPrice = cartItems
    .filter((item) => item.checked)
    .reduce((acc, item) => acc + item.price * item.quantity, 0)

  const totalQuantity = cartItems.filter((item) => item.checked).length

  return (
    <div
      className="p-4 flex flex-col justify-between"
      style={{ minHeight: 'calc(100vh - 8rem)' }}
    >
      <div>
        {/* 가게 정보 */}
        <StoreLink storeId={1} />

        {/* 장바구니 아이템 리스트 */}
        {cartItems.map((item) => (
          <div
            key={item.id}
            className="border border-gray-medium rounded-lg p-4 mb-2 flex flex-col"
          >
            <div className="flex justify-between">
              <Checkbox
                onChange={() => handleItemCheck(item.id)}
                checked={item.checked}
                id={`cart-${item.id}`}
                size={24}
              />
              <button
                onClick={() => handleRemoveItem(item.id)}
                className="hover:text-primary-500"
              >
                <IoCloseOutline size={20} />
              </button>
            </div>

            {/* 메뉴 정보 */}
            <div className="ms-10 mt-2">
              <OrderMenuDetail item={item} />
            </div>

            <div className="ms-10 mt-4 flex justify-between items-center">
              <button
                type="button"
                className="p-3 py-1 text-primary-500 border border-primary-300 rounded-full text-xs w-20"
                onClick={() => handleOpenDrawer(item)} // 옵션 변경 시 Drawer 열기
              >
                옵션 변경
              </button>
              <p className="font-bold">{item.price.toLocaleString()}원</p>
            </div>
          </div>
        ))}

        {/* 메뉴 추가 버튼 */}
        <div className="w-full py-2 border border-primary-300 text-primary-500 rounded-lg text-center">
          <Link href="/customer/stores/1">+ 메뉴 추가</Link>
        </div>
      </div>

      {/* 결제 금액 정보 */}
      <div>
        <h3 className="text-xl font-semibold ms-1 mt-8">예상 결제 금액</h3>
        <div className="border border-gray-medium rounded-lg p-4 mt-2">
          <div className="flex justify-between mb-2">
            <span className="text-gray-600">선택한 메뉴 수</span>
            <span>총 {totalQuantity}개</span>
          </div>
          <div className="flex justify-between">
            <span className="text-gray-600">예상 주문 금액</span>
            <span className="font-bold">{totalPrice.toLocaleString()}원</span>
          </div>
        </div>
      </div>

      {/* 결제 버튼 */}
      <div className="fixed bottom-0 inset-x-0 p-3 bg-white">
        <button
          type="button"
          className={`w-full py-4 font-bold rounded-lg ${
            totalQuantity > 0
              ? 'bg-primary-500 text-white'
              : 'bg-gray-medium text-gray-dark'
          }`}
          disabled={totalQuantity === 0}
        >
          {`${totalPrice.toLocaleString()}원 결제하기`}
        </button>
      </div>

      {/* 옵션 변경 Drawer */}
      {showDrawer && (
        <Drawer title={selectedItem.name} onClose={handleCloseDrawer}>
          <div className="mb-20">
            <MenuOptions type="change" menuInfo={selectedItem} />
          </div>
        </Drawer>
      )}
    </div>
  )
}
