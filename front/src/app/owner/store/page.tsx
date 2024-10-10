import OwnerStore from '@/containers/owner/store'
import ClientWrapper from '@/utils/ClientWrapper'

export default function OwnerStorePage() {
  return (
    <div>
      <ClientWrapper>
        <OwnerStore />
      </ClientWrapper>
    </div>
  )
}
