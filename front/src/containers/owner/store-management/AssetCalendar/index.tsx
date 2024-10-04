import SingleDatePicker from '@/containers/owner/store-management/AssetCalendar/SingleDatePicker'
import TransactionList from '@/containers/owner/store-management/AssetCalendar/TransactionList'

export default function AssetCalendar() {
  return (
    <div>
      <SingleDatePicker />
      <TransactionList />
    </div>
  )
}
