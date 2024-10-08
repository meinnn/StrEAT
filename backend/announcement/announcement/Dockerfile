# OpenJDK 21 Alpine 버전 사용
FROM openjdk:21-jdk

RUN microdnf install -y python3 python3-pip

# 애플리케이션 JAR 파일을 Docker 이미지로 복사
COPY build/libs/*.jar /app.jar

COPY src/main/resources/submitWordFile.py submitWordFile.py
COPY src/main/resources/truck.docx truck.docx

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]
