FROM amazoncorretto:11

ENV PROJECT_NAME sdmt-web
ENV VERSION 0.1

RUN mkdir /app

WORKDIR /app

COPY target/universal/${PROJECT_NAME}-${VERSION}.zip ./

RUN yum install -y unzip
RUN unzip ${PROJECT_NAME}-${VERSION}.zip

WORKDIR /app/${PROJECT_NAME}-${VERSION}

RUN chmod a+x bin/${PROJECT_NAME}

CMD rm -rf RUNNING_PID && bin/${PROJECT_NAME}

EXPOSE 9000 9000