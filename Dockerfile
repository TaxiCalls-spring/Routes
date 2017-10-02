FROM java:8

MAINTAINER rmeloca@gmail.com

WORKDIR /app

ADD ./target/Routes.jar /app/

EXPOSE 8080

CMD ["java", "-Xmx200m", "-jar", "/app/Routes.jar"]