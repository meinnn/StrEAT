import AppBar from '@/components/AppBar'
import LocationSearch from '@/containers/customer/search/LocationSearch'

export default function CustomerSearch() {
  return (
    <div className="h-screen bg-secondary-light">
      <AppBar title="위치 검색" />
      <LocationSearch />
    </div>
  )
}
