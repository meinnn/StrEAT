# A307 김혜인

## 2024.09.02
* 아이디어 회의

| 구분 | 내용 |
| --- | --- |
| **해결하고 싶은 문제** | 1. 푸드트럭 앞에 줄이 길어 기다려야 하는 문제(푸드트럭 사이렌오더)<br>2. 계좌이체 표지판을 보고 직접 계좌를 입력하여 송금해야 하는 번거로움<br>3. 푸드트럭의 위치 정보가 실시간으로 업데이트되지 않아 알 수 없는 문제<br>4. 글로 적힌 메뉴판이 바래져 가독성이 떨어지는 문제 |
| **핵심 기능** | - **NFC를 이용한 간편 계좌 송금 (푸드트럭)**<br><br>- **매출 관리 (AI를 활용한 매출 리포트)**<br><br>- **주문 예약 시스템**<br>  1) 사이렌오더 같은 시스템 (= 테이블링)<br>  2) NFC를 이용한 주문 예약 (줄이 긴 푸드트럭/도깨비 야시장)<br>  3) 카드 결제/계좌 이체 선택 (선불 시스템)<br><br>- **개인별 메뉴 확인 및 리뷰** → **리뷰는 후순위**<br>  1) 알레르기 요소 있는 메뉴 정보 확인<br><br>- **GPS를 통한 위치 업데이트** |
| **부가 기능** | - **NFC를 이용한 간편 계좌 송금 (푸드트럭)**<br>  → 인증 필요<br><br>- **매출 관리 (AI를 활용한 매출 리포트)**<br>  → 사장님이 회원가입을 했을 경우 제공되는 기능<br>  → ~~대출 기능~~<br><br>- **영업 시작 버튼 클릭 시 해당 푸드트럭의 위치 표시 (GPS)**<br>  → 사업자 등록 번호가 있는 푸드트럭만 가입 가능<br><br>- **외국인을 위한 환율 시스템** (메뉴 가격이 해외 통화로 얼마인지 함께 표시)<br><br>- **대기 팀 수 표시** (주문 전/후 모두 표시)<br><br>- **NFC 태그를 이용한 음식 수령**<br>  → NFC 태그 인식(스마트폰) → 쿠키, 세션스토리지 등으로 주문 정보 전송 → 정보에 맞는 음식을 손님에게 전달 → 포장된 음식 태깅 처리 → 시스템에서 수령 완료 처리 |
| **핀테크 요소** | - 비밀번호 입력 시 키패드가 랜덤하게 바뀌는 보안 기능<br>- 매출 관리(AI 활용)<br>- NFC를 통한 간편 가상계좌 송금<br>- 간편결제 정보를 등록 후 스와이프 한 번으로 결제<br>- ~~마이데이터를 통해 혜택까지 얼마 남지 않은 카드를 우선 추천~~ |
| **최종 산출물** | PWA? |

---

위의 마크다운을 사용하면 문서를 더욱 깔끔하게 정리하고 공유할 수 있습니다.
