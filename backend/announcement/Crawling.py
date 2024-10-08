from http.client import responses
from importlib.util import source_hash
import requests
from bs4 import BeautifulSoup

import mysql.connector

base_url = "https://www.koreafoodtruck.org"

def crawl_notice_list():
    notice_list_url = f"{base_url}/blank-6"

    response = requests.get(notice_list_url)
    response.encoding = 'utf-8'

    if response.status_code == 200:
        soup= BeautifulSoup(response.text, 'html.parser')

        notice_links = soup.select('tbody tr td .uWgoCM a .post-title .x2rEMQ .NWUMmd span')
        links = soup.select('tbody tr td .uWgoCM a')

        # MySQL 연결
        connection = mysql.connector.connect(
            host='j11a307.p.ssafy.io',  # 데이터베이스 호스트
            user='root',  # 사용자 이름
            password='1234',  # 비밀번호
            database='streat'  # 데이터베이스 이름
        )
        cursor = connection.cursor()

        # 테이블 전체 데이터 삭제
        truncate_query = "TRUNCATE TABLE recruit_announcement"
        cursor.execute(truncate_query)
        connection.commit()
        print("기존 데이터 삭제 완료!!")

        for index, link in enumerate(links):
            if index >=1:
                title = link.select_one('.post-title .x2rEMQ .NWUMmd span').text.strip()  # 제목 추출
                link = link['href']

                crawl_notice_detail(link)


def crawl_notice_detail(notice_url):
    response = requests.get(notice_url)

    if response.status_code == 200:
        soup = BeautifulSoup(response.text, 'html.parser')

        recruit_post_title = ""
        event_name = ""
        special_notes = ""
        event_days = ""
        event_times = ""
        event_place = ""
        recruit_size = ""
        entry_conditions = ""

        titles = soup.select('.NWUMmd span')  # 공고 내용의 CSS 선택자
        for title in titles:
            recruit_post_title = title.text.strip()

        contents = soup.select('.rLPbp .wAvtl ._3QmQ6 .JDT-d .SX-l9')  # 공고 내용의 CSS 선택자

        is_special_notes = False
        is_event_days = False

        for content in contents:
            if content.text.strip() == "":
                continue
            if content.text.strip() == "모집 개요":
                continue
            elif "행사명 :" in content.text:
                if ":" in content.text:
                    event_name = content.text.split(":")[1].strip()
            elif "일 자 :" in content.text:
                if ":" in content.text:
                    event_days += content.text.split(":")[1].strip() + " "
                    is_event_days = True
            elif "운영시간 :" in content.text.strip():
                is_event_days = False
                if ":" in content.text:
                    event_times = content.text.split(":", 1)[1].strip()
            elif is_event_days:
                event_days += content.text.strip() + " "
            elif "장 소 :" in content.text.strip():
                if ":" in content.text:
                    event_place = content.text.split(":", 1)[1].strip()
            elif "모집규모 :" in content.text.strip():
                if ":" in content.text:
                    recruit_size = content.text.split("푸드트럭")[1].strip()
            elif "입점조건 :" in content.text.strip():
                if ":" in content.text:
                    entry_conditions = content.text.split(":")[1].strip()
            elif "특이사항" in content.text.strip():
                is_special_notes = True
                continue  # 특이사항 시작 줄은 건너뜀
            elif is_special_notes:
                if content.text.strip().startswith("*"):
                    break

                # 특이사항 내용을 하나의 문자열로 이어붙임
                special_notes += content.text.strip() + " "

            elif content.name != 'strong':
                print(content.text)

        # MySQL 연결
        connection = mysql.connector.connect(
            host='j11a307.p.ssafy.io',  # 데이터베이스 호스트
            user='root',  # 사용자 이름
            password='1234',  # 비밀번호
            database='streat'  # 데이터베이스 이름
        )

        cursor = connection.cursor()

        insert_query = """
                INSERT INTO recruit_announcement(recruit_post_title, event_name, event_days, event_times, event_place, recruit_size, entry_conditions, special_notes)
                VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
                """
        data = (recruit_post_title, event_name, event_days, event_times, event_place, recruit_size, entry_conditions, special_notes)

        cursor.execute(insert_query, data)
        connection.commit()

        print("데이터 삽입 완료!!")

#크롤링 실행
crawl_notice_list()