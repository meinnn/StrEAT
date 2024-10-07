const getConvertedDate = (date: string) => {
  const dateObject = new Date(date)

  const weekdays = ['일', '월', '화', '수', '목', '금', '토']

  const year = dateObject.getFullYear()
  const month = String(dateObject.getMonth() + 1).padStart(2, '0')
  const day = String(dateObject.getDate()).padStart(2, '0')
  const weekday = weekdays[dateObject.getDay()]

  const formattedDate = `${year}.${month}.${day} (${weekday})`
  return formattedDate
}

export default getConvertedDate
