FROM fabric8/java-centos-openjdk11-jre
ADD target/invoice.jar /home/
CMD ["java","-jar","/home/invoice-0.0.1-SNAPSHOT.jar"]