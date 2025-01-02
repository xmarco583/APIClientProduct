FROM eclipse-temurin:17.0.13_11-jdk

EXPOSE 5000

WORKDIR /root

COPY ./pom.xml /root
COPY ./.mvn /root/.mvn
COPY ./mvnw /root

RUN ./mvnw dependency:go-offline

COPY ./src /root/src

RUN ./mvnw clean install -DskipTests

ENTRYPOINT [ "java","-jar","/root/target/usproducto-0.0.1-SNAPSHOT.jar" ]