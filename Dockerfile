FROM registry.cluster.local/openjdk:8-jdk-alpine
MAINTAINER flame
COPY ./yiyan-demo-server/target/yiyan-demo-server.jar demo-service.jar
CMD java -jar demo-service.jar
