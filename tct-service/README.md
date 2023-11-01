mvn install:install-file -Dfile=external-libs/xmlsec-2.3.0.jar -DgroupId=org.apache.xml.security -DartifactId=xmlsec -Dversion=2.3.0 -Dpackaging=jar

mvn install:install-file -Dfile=external-libs/commons-net-3.8.0.jar -DgroupId=commons-net -DartifactId=commons-net -Dversion=3.8.0 -Dpackaging=jar
mvn install:install-file -Dfile=external-libs/wlfullclient.jar -DgroupId=com.oracle -DartifactId=wlfullclient -Dversion=10.3.6.0 -Dpackaging=jar
