import { useState } from 'react'

const DAY = ['월', '화', '수', '목', '금', '토', '일']

interface Day {
  day: string
  start?: string
  end?: string
}

export default function StoreBusinessSchedulePicker() {
  const [selectedList, setSelectedList] = useState<Day[]>(
    DAY.map((day) => {
      return {
        day,
        start: '',
        end: '',
      }
    })
  )

  return (
    <div>
      <div className="flex flex-col w-full">
        <h3 className="font-medium text-xl text-text mb-3">
          영업일 및 영업시간
        </h3>
        <div className="flex justify-between mb-7">
          {DAY.map((day, index) => {
            return (
              <button
                // onClick={() => {
                //   let nSelectedList = [...selectedList]
                //   nSelectedList[index] = !nSelectedList[index]
                //   // nSelectedList.splice(index, 1)
                //   setSelectedList(nSelectedList)
                // }}
                className={`${selectedList[index] ? 'text-primary-400 border-primary-400' : 'text-primary-950 border-primary-950'} rounded-2xl border py-3 px-4`}
              >
                {day}
              </button>
            )
          })}
        </div>
        <table className="table-auto border-collapse">
          <thead>
            <th className="w-1/5"></th>
            <th className="w-2/5 text-xs">영업 시작 시간</th>
            <th className="w-2/5 text-xs">영업 끝 시간</th>
          </thead>
          {/* <tbody>
            <tr>
              <td className="text-xl font-normal text-center">월</td>
              <td>
                <input
                  type="time"
                  className="w-full outline-none py-2 rounded-lg border border-gray-dark"
                />
              </td>
              <td>
                <input
                  type="time"
                  className="w-full outline-none py-2 rounded-lg border border-gray-dark"
                />
              </td>
            </tr>
          </tbody> */}
        </table>
      </div>
    </div>
  )
}
