# 1. Node.js 이미지를 사용하여 Docker 환경 설정
FROM node:20.15.1

# 2. 작업 디렉토리를 'front' 폴더로 설정
WORKDIR /app

# 3. 필요한 파일들을 복사
COPY package.json .
COPY package-lock.json .

# 4. 의존성 설치
RUN npm ci

# 5. 나머지 프로젝트 파일 복사
COPY . /app

# 6. 빌드 명령 실행 (프로덕션 모드로 빌드)
RUN npm run build

# 프로덕션 스테이지
FROM nginx:1.21.4-alpine

# nginx 실행 전 default.conf 파일 수정
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 빌드 이미지에서 생성된 dist 폴더를 nginx 이미지로 복사
COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80
CMD [ "nginx", "-g", "daemon off;" ]