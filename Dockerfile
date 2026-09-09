# ---- Stage 1: build the .war with Maven ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ---- Stage 2: run it on Tomcat 11 (jakarta.servlet, matches your local setup) ----
FROM tomcat:11.0-jdk17-temurin

# deploy the app at the domain root ("/") instead of "/ch07cart"
RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY --from=build /app/target/ch07cart.war /usr/local/tomcat/webapps/ROOT.war

# Render injects PORT at runtime (default 10000) -- Tomcat's connector must
# listen on that port, so this script rewrites server.xml before starting.
COPY start.sh /start.sh
RUN chmod +x /start.sh

EXPOSE 10000
CMD ["/start.sh"]
