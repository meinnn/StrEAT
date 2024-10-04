import MenuDetails from '@/containers/customer/home/stores/menu/MenuDetails'

export default function MenuDetail({ params }: { params: { menuId: string } }) {
  return <MenuDetails menuId={params.menuId} />
}
