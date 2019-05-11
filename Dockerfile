FROM jboss/wildfly
ADD ./SpringLineMain/target/springLine.war /opt/jboss/wildfly/standalone/deployments/
ADD ./SpringLineMarketing/target/SpringLineMarketing.war /opt/jboss/wildfly/standalone/deployments/
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent
CMD ["/opt/jboss/wildfly/bin/standalone.sh", \
  "-Djboss.server.default.config=standalone-full.xml", \
  "-b", "0.0.0.0", \
  "-bmanagement", "0.0.0.0" \
]
