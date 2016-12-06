FROM jboss/wildfly

ENV LC_ALL en_US.UTF-8
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US.UTF-8

ADD standalone.xml /opt/jboss/wildfly/standalone/configuration/

RUN \
    echo                                          >> /opt/jboss/wildfly/standalone/configuration/mgmt-users.properties && \
    echo '# username: admin, password: admin'     >> /opt/jboss/wildfly/standalone/configuration/mgmt-users.properties && \
    echo 'admin=c22052286cd5d72239a90fe193737253' >> /opt/jboss/wildfly/standalone/configuration/mgmt-users.properties

EXPOSE 9990

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]