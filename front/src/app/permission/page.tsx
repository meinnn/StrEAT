import Permission from '@/containers/permission'

interface PermissionPageProps {
  searchParams: { userType: string }
}

export default function PermissionPage({ searchParams }: PermissionPageProps) {
  return <Permission userType={searchParams.userType} />
}
