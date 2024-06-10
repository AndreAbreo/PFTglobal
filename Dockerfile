# Use the jboss/wildfly image as the base
FROM quay.io/wildfly/wildfly:latest-jdk17

# Copy the standalone.xml file to the WildFly configuration directory
# COPY standalone.xml /opt/jboss/wildfly/standalone/configuration/

# Expose the management port
EXPOSE 9990

# Run WildFly in standalone mode and allow management from any host
RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#007 --silent
  
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
