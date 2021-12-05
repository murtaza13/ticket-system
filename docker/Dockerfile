FROM centos
RUN yum install -y java-1.8.0-openjdk-devel.x86_64 dos2unix
ENV JAVA_HOME /etc/alternatives/jre
RUN $JAVA_HOME/bin/java -version
RUN echo "Installed Java"

RUN yum install -y git
RUN echo git --version

WORKDIR /opt
RUN git clone https://murtaza13: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@github.com/murtaza13/ticket-system.git
WORKDIR /opt/ticket-system

RUN ls -l
RUN chmod +x gradlew
RUN ./gradlew -v build
RUN ./gradlew bootrun