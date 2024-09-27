# 1. Node.js 이미지를 사용하여 Docker 환경 설정
FROM node:20.15.1 AS build

# ARG로 빌드 타임 변수 선언
ARG NEXT_PUBLIC_NAVER_CLIENT_ID
ARG NEXT_PUBLIC_NAVER_SEARCH_CLIENT_ID
ARG NEXT_PUBLIC_NAVER_SEARCH_CLIENT_SECRET

# ENV로 빌드 타임에 설정된 ARG 값을 런타임에 사용할 수 있게 설정
ENV NEXT_PUBLIC_NAVER_CLIENT_ID=${NEXT_PUBLIC_NAVER_CLIENT_ID}
ENV NEXT_PUBLIC_NAVER_SEARCH_CLIENT_ID=${NEXT_PUBLIC_NAVER_SEARCH_CLIENT_ID}
ENV NEXT_PUBLIC_NAVER_SEARCH_CLIENT_SECRET=${NEXT_PUBLIC_NAVER_SEARCH_CLIENT_SECRET}

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

# 6. 런타임 단계
FROM node:20.15.1 AS runner

WORKDIR /app

COPY --from=build /app/.next ./.next
COPY --from=build /app/node_modules ./node_modules
COPY --from=build /app/public ./public
COPY --from=build /app/package.json ./package.json

EXPOSE 3000

CMD Xvfb :99 -screen 0 1024x768x16 -ac & npm start
