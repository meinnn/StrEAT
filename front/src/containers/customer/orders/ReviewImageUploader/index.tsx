'use client'

import Image from 'next/image'
import { VscAdd } from 'react-icons/vsc'
import { TfiClose } from 'react-icons/tfi'

export interface ImageFile {
  preview: string
  file: File
}

export default function ReviewImageUploader({
  reviewImageList,
  setReviewImageList,
}: {
  reviewImageList: ImageFile[]
  setReviewImageList: React.Dispatch<React.SetStateAction<ImageFile[]>>
}) {
  const handleChangeFile = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0]

    if (file) {
      const preview = URL.createObjectURL(file)
      const nImageList = [...reviewImageList]
      nImageList.push({
        preview,
        file,
      })
      setReviewImageList(nImageList)
    }
  }

  const handleClickDeleteImageButton = (
    e: React.MouseEvent<HTMLButtonElement>,
    index: number
  ) => {
    e.preventDefault()
    const nImageList = [...reviewImageList]
    nImageList.splice(index, 1)
    setReviewImageList(nImageList)
  }

  return (
    <section className="flex gap-3">
      {reviewImageList.map((image, index) => {
        return (
          <div key={image.preview} className="relative inline-block">
            <button
              type="button"
              onClick={(e) => handleClickDeleteImageButton(e, index)}
              className="absolute z-10 border border-gray-medium -top-2 -right-2 shadow-lg bg-white rounded-full w-6 h-6 flex justify-center items-center"
            >
              <TfiClose className="W-3 h-3 text-text" />
            </button>
            <p className="relative w-16 aspect-square rounded-md overflow-hidden border border-gray-medium bg-gray-light">
              <Image
                src={image.preview}
                fill
                alt="food-review"
                draggable={false}
                className="object-cover"
                priority
              />
            </p>
          </div>
        )
      })}
      {reviewImageList.length < 5 ? (
        <div>
          <label
            htmlFor="file-upload"
            className="w-16 h-16 aspect-square border rounded-lg bg-gray-medium flex justify-center items-center cursor-pointer"
          >
            <VscAdd className="w-6 h-6 text-gray-dark" />
          </label>
          <input
            id="file-upload"
            type="file"
            className="hidden"
            onChange={handleChangeFile}
          />
        </div>
      ) : null}
    </section>
  )
}
