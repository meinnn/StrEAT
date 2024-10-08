from docx import Document
from docx.enum.text import WD_COLOR_INDEX

import os

# 현재 작업 디렉토리 출력
print("현재 작업 디렉토리:", os.getcwd())

# Word 문서 템플릿 로드
doc = Document('/truck.docx')

# 회원 정보를 사전(Dictionary) 형태로 준비
# user_info = {
#     '이름': '홍길동',
#     '회원번호': '123456'
# }

# 문서의 각 문단을 순회하며 자리표시자를 대체
# for paragraph in doc.paragraphs:
#     print(paragraph.text, 'hi')

# 문서 내 모든 표를 순회하며 각 셀의 자리표시자를 대체
for table in doc.tables:
    for row in table.rows:
        for i, cell in enumerate(row.cells):
            if '대표자' in cell.text:
                if i + 1 < len(row.cells):  # 오른쪽 셀이 존재하는지 확인
                    row.cells[i + 1].text = ''
                    cell_to_update = row.cells[i + 1].paragraphs[0].add_run('이주호')
                    cell_to_update.font.highlight_color = WD_COLOR_INDEX.YELLOW
            elif '성 별' in cell.text:
                if i + 1 < len(row.cells):  # 오른쪽 셀이 존재하는지 확인
                    row.cells[i + 1].text = ''
                    cell_to_update = row.cells[i + 1].paragraphs[0].add_run('남')
                    cell_to_update.font.highlight_color = WD_COLOR_INDEX.YELLOW
            elif '연 령' in cell.text:
                if i + 1 < len(row.cells):  # 오른쪽 셀이 존재하는지 확인
                    row.cells[i + 1].text = ''
                    cell_to_update = row.cells[i + 1].paragraphs[0].add_run('21세')
                    cell_to_update.font.highlight_color = WD_COLOR_INDEX.YELLOW
            elif '주민등록번호' in cell.text:
                if i + 1 < len(row.cells):  # 오른쪽 셀이 존재하는지 확인
                    row.cells[i + 1].text = ''
                    cell_to_update = row.cells[i + 1].paragraphs[0].add_run('990202')
                    cell_to_update.font.highlight_color = WD_COLOR_INDEX.YELLOW
            elif '주 소' in cell.text:
                if i + 1 < len(row.cells):  # 오른쪽 셀이 존재하는지 확인
                    row.cells[i + 1].text = ''
                    cell_to_update = row.cells[i + 1].paragraphs[0].add_run('경기도 용인시 기흥구 보정동 죽현마을 현대아이파크1차 206동 2304호')
                    cell_to_update.font.highlight_color = WD_COLOR_INDEX.YELLOW
            elif '연 락 처' in cell.text:
                if i + 1 < len(row.cells):  # 오른쪽 셀이 존재하는지 확인
                    if '일반전화' in row.cells[i+1].text:
                        row.cells[i + 1].text = ''
                        cell_to_update = row.cells[i + 1].paragraphs[0].add_run(
                            '031-274-3878')
                        cell_to_update.font.highlight_color = WD_COLOR_INDEX.YELLOW
                    if '휴대폰' in row.cells[i + 1].text:
                        row.cells[i + 1].text = ''
                        cell_to_update = row.cells[i + 1].paragraphs[0].add_run(
                            '010-2572-3878')
                        cell_to_update.font.highlight_color = WD_COLOR_INDEX.YELLOW
            elif 'ON-LINE' in cell.text:
                if i + 1 < len(row.cells):  # 오른쪽 셀이 존재하는지 확인
                    if 'E-Mail' in row.cells[i + 1].text:
                        row.cells[i + 1].text = ''
                        cell_to_update = row.cells[i + 1].paragraphs[0].add_run(
                            'jeuslee99@naver.com')
                        cell_to_update.font.highlight_color = WD_COLOR_INDEX.YELLOW
                    if 'SNS' in row.cells[i + 1].text:
                        row.cells[i + 1].text = ''
                        cell_to_update = row.cells[i + 1].paragraphs[0].add_run(
                            'Intagram@: ' + 'rat_leeho')
                        cell_to_update.font.highlight_color = WD_COLOR_INDEX.YELLOW
            elif '푸드트럭명' in cell.text:
                if i + 1 < len(row.cells):  # 오른쪽 셀이 존재하는지 확인
                    row.cells[i + 1].text = ''
                    cell_to_update = row.cells[i + 1].paragraphs[0].add_run('민지네 카페트럭')
                    cell_to_update.font.highlight_color = WD_COLOR_INDEX.YELLOW
            elif '사업자등록\n유무' in cell.text:
                if i + 1 < len(row.cells):
                    if '사업자등록' in row.cells[i + 1].text:
                        row.cells[i + 1].text = ''
                        cell_to_update = row.cells[i + 1].paragraphs[0].add_run('■ 사업자등록')
                        cell_to_update.font.highlight_color = WD_COLOR_INDEX.YELLOW
            elif '사업자등록번호' in cell.text:
                if i + 1 < len(row.cells):
                    row.cells[i + 1].text = ''
                    cell_to_update = row.cells[i + 1].paragraphs[0].add_run('165-128763721')
                    cell_to_update.font.highlight_color = WD_COLOR_INDEX.YELLOW
            elif '신청행사명' in cell.text:
                if i + 1 < len(row.cells):
                    row.cells[i + 1].text = ''
                    cell_to_update = row.cells[i + 1].paragraphs[0].add_run('삼성전자 어린이날 행사')
                    cell_to_update.font.highlight_color = WD_COLOR_INDEX.YELLOW

            print("1: " + cell.text)

# 결과 문서 저장
#submit_name = '20241008_입점 신청서_'+'민지네 푸드트럭'
#doc.save(submit_name+'.docx')
doc.save('announcement/announcement/src/main/java/io/ssafy/p/j11a307/announcement/newfile.docx')
