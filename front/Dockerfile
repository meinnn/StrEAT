# 1. Node.js 이미지를 사용하여 Docker 환경 설정
FROM node:20.15.1 AS build

# 2. 작업 디렉토리를 'front' 폴더로 설정
WORKDIR /app

# 3. 필요한 파일들을 복사
COPY package.*.json ./

# 4. 의존성 설치
RUN npm install

COPY . .

# 6. 빌드 명령 실행 (프로덕션 모드로 빌드)
RUN npm run build

FROM nginx

EXPOSE 3000

COPY ./default.conf /etc/nginx/conf.d/default.conf

COPY --from=build app/out  /usr/share/nginx/html
