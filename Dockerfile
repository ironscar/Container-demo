FROM adoptopenjdk/openjdk11:alpine

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ARG VERSION
COPY target/demo-${VERSION}/WEB-INF/lib /app/lib
COPY target/demo-${VERSION}/META-INF /app/META-INF
COPY target/demo-${VERSION}/WEB-INF/classes /app

ENTRYPOINT [ "java", "-cp", "app:app/lib/*", "com.container.demo.DemoApplication" ]
EXPOSE 8081