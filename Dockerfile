FROM adoptopenjdk/openjdk11:alpine

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ARG VERSION
COPY target/demo-${VERSION}-SNAPSHOT/WEB-INF/lib /app/lib
COPY target/demo-${VERSION}-SNAPSHOT/META-INF /app/META-INF
COPY target/demo-${VERSION}-SNAPSHOT/WEB-INF/classes /app

ENTRYPOINT [ "java", "-cp", "app:app/lib/*", "com.container.demo.DemoApplication" ]
