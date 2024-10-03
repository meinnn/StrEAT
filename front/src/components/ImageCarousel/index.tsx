/* eslint-disable react/no-array-index-key */

'use client'

import Image from 'next/image'
import React, { useState } from 'react'
import { FaAngleLeft, FaAngleRight } from 'react-icons/fa6'

export default function ImageCrousel({ slides }: { slides: string[] }) {
  const [currentIndex, setCurrentIndex] = useState(0)

  const nextSlide = () => {
    setCurrentIndex((prevIndex) =>
      prevIndex === slides.length - 1 ? 0 : prevIndex + 1
    )
  }

  const prevSlide = () => {
    setCurrentIndex((prevIndex) =>
      prevIndex === 0 ? slides.length - 1 : prevIndex - 1
    )
  }

  return (
    <div className="relative w-full mb-3 rounded-lg overflow-hidden border border-gray-medium">
      <div
        className="flex transition-transform duration-500 ease-in-out"
        style={{ transform: `translateX(-${currentIndex * 100}%)` }}
      >
        {slides.map((slide, index) => (
          <div
            key={`${slide} ${index}`}
            className="w-full flex-shrink-0 h-full relative aspect-video"
          >
            <Image
              src={slide}
              alt="음식 리뷰 사진"
              className="object-cover"
              fill
              priority
            />
          </div>
        ))}
      </div>
      {slides.length > 1 ? (
        <div className="absolute inset-0 flex items-center justify-between px-2">
          <FaAngleLeft
            onClick={prevSlide}
            className="text-text cursor-pointer w-6 h-6"
          />
          <FaAngleRight
            onClick={nextSlide}
            className="text-text cursor-pointer w-6 h-6"
          />
        </div>
      ) : null}

      <div className="absolute bottom-2 left-0 right-0 flex justify-center space-x-2">
        {slides.length > 1 &&
          slides.map((slide, index) => (
            <div
              key={`${slide} ${index}`}
              onClick={() => setCurrentIndex(index)}
              className={`w-3 h-3 rounded-full ${
                currentIndex === index ? 'bg-primary-500' : 'bg-gray-medium'
              } cursor-pointer`}
            />
          ))}
      </div>
    </div>
  )
}
