/* eslint-disable import/prefer-default-export */
import { NextResponse, NextRequest } from 'next/server'
import { updateProductStockStatus } from '@/libs/product'

/* 메뉴 품절 여부 수정 API  */
export async function PUT(
  req: NextRequest,
  { params }: { params: { id: string } }
) {
  try {
    const productId = params.id

    const menuStockStatusResponse = await updateProductStockStatus(productId)

    if (!menuStockStatusResponse.ok) {
      const errorMessage = await menuStockStatusResponse.text()
      return NextResponse.json(
        { error: errorMessage },
        { status: menuStockStatusResponse.status }
      )
    }

    const menuStockStatusData = await menuStockStatusResponse.json()
    console.log('menuStockStatusData:', menuStockStatusData)

    return NextResponse.json(menuStockStatusData)
  } catch (error: unknown) {
    let errorMessage = '에러가 발생했습니다'

    if (error instanceof Error) {
      errorMessage = error.message
    } else if (typeof error === 'string') {
      errorMessage = error
    } else if (typeof error === 'object' && error !== null) {
      errorMessage = JSON.stringify(error)
    }

    console.error('에러:', errorMessage)
    return NextResponse.json({ error: errorMessage }, { status: 500 })
  }
}
