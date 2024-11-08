'use client'

import { useEffect } from 'react'

export default function PickUpPage() {
  const pickupFood = async () => {
    const response = await fetch(`/services/pickup`, {
      method: 'POST',
    })
    if (!response.ok) {
      console.error('음식 수령에 실패했습니다.')
    }
    return response.json()
  }

  useEffect(() => {
    pickupFood()
  }, [])

  return <div>음식 수령하는 페이지</div>
}
