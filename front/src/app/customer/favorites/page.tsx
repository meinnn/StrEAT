import Favorites from '@/containers/customer/favorites'
import ClientWrapper from '@/utils/ClientWrapper'

export default function CustomerFavorites() {
  return (
    <ClientWrapper>
      <div>
        <Favorites />
      </div>
    </ClientWrapper>
  )
}
