# 빌드 스테이지
FROM gradle:8-jdk17 AS build
WORKDIR /app

# Gradle 캐시 활용을 위해 의존성 먼저 복사
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
RUN gradle dependencies --no-daemon || true

# 소스 코드 복사 및 빌드
COPY src ./src
RUN gradle clean build -x test --no-daemon

# 실행 스테이지
FROM amazoncorretto:17-alpine-jdk
WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=build /app/build/libs/*.jar app.jar

# JVM 옵션 설정 (t3.micro용)
ENV JAVA_OPTS="-Xms512m -Xmx512m -XX:+UseG1GC -XX:MaxGCPauseMillis=200"

# 포트 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]