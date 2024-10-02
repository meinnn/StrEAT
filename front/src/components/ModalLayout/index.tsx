export default function ModalLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <div className="fixed top-0 w-full h-full cursor-pointer bg-[rgba(0,0,0,0.3)] backdrop-blur-sm z-[100] flex justify-center items-center">
      {children}
    </div>
  )
}
