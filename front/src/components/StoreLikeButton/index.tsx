import { RiHeart3Fill, RiHeart3Line } from 'react-icons/ri'
import { useCallback, useEffect, useState } from 'react'

export default function StoreLikeButton({ storeId }: { storeId: number }) {
  const [isLiked, setIsLiked] = useState(false)

  // 찜 여부를 확인하는 함수
  const fetchLikeStatus = useCallback(async () => {
    try {
      const response = await fetch(`/services/stores/${storeId}/dibs`, {
        method: 'GET',
      })
      const result = await response.json()

      if (response.ok) {
        setIsLiked(result.data) // API의 data 값이 true이면 찜한 상태
      } else {
        console.error(result.message)
      }
    } catch (error) {
      console.error('찜 여부를 확인하는 중 오류 발생:', error)
    }
  }, [storeId])

  // 찜 추가/삭제 함수
  const toggleLike = async () => {
    try {
      const method = isLiked ? 'DELETE' : 'POST'
      const response = await fetch(`/services/stores/${storeId}/dibs`, {
        method,
      })

      if (response.ok) {
        setIsLiked((prev) => !prev) // 상태 토글
      } else {
        const result = await response.json()
        console.error(result.message)
      }
    } catch (error) {
      console.error('찜 상태를 변경하는 중 오류 발생:', error)
    }
  }

  // 컴포넌트가 마운트될 때 찜 여부를 확인
  useEffect(() => {
    fetchLikeStatus().then()
  }, [fetchLikeStatus])

  return (
    <button type="button" onClick={toggleLike}>
      {isLiked ? (
        <RiHeart3Fill className="text-primary-500" size={24} />
      ) : (
        <RiHeart3Line className="text-gray-dark" size={24} />
      )}
    </button>
  )
}
