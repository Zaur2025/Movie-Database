# Используй JDK вместо JRE!
FROM eclipse-temurin:17-jdk

# Или если хочешь slim версию:
# FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
RUN echo 'public class Test { public static void main(String[] args) { System.out.println("Docker works!"); } }' > Test.java
RUN javac Test.java
ENTRYPOINT ["java", "Test"]