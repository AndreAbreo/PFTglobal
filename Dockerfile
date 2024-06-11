# Use the jboss/wildfly image as the base
FROM quay.io/wildfly/wildfly:latest-jdk17

# Copy the Oracle JDBC driver to the WildFly modules directory
COPY drivers/ojdbc11.jar /opt/jboss/wildfly/modules/system/layers/base/com/oracle/ojdbc/main/

# Expose the management port
EXPOSE 9990

# Run WildFly in standalone mode and allow management from any host
RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#007 --silent

# Copy the startup script to the WildFly bin directory
COPY datasource.sh /opt/jboss/wildfly/bin/datasource.sh

# Switch to the root user
USER root

# Change ownership of the startup script to the jboss user and make it executable
RUN chown jboss:jboss /opt/jboss/wildfly/bin/datasource.sh && chmod +x /opt/jboss/wildfly/bin/datasource.sh

# Switch back to the jboss user
USER jboss

# Run the startup script as the jboss user
RUN /opt/jboss/wildfly/bin/datasource.sh

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]