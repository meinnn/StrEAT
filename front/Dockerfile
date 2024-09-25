# 1. Node.js 이미지를 사용하여 Docker 환경 설정
FROM node:20.15.1 AS build

# 2. 작업 디렉토리를 'front' 폴더로 설정
WORKDIR /app

# 3. 필요한 파일들을 복사
COPY package.json .
COPY package-lock.json .

# 4. 의존성 설치
RUN npm install

COPY . .

# 6. 빌드 명령 실행 (프로덕션 모드로 빌드)
RUN npm run build

WORKDIR /app

COPY --from=builder /app/.next ./.next
COPY --from=builder /app/node_modules ./node_modules
COPY --from=builder /app/public ./public
COPY --from=builder /app/package.json ./package.json

EXPOSE 3000

CMD Xvfb :99 -screen 0 1024x768x16 -ac & npm start
