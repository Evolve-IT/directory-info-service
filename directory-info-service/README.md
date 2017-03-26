# Directory Info Service (directory-info-service)

This is a spring boot REST service which has been encapsulated in a docker container.
The REST service can be used to get a directory listing of a directory on the server where the docker container is running.

## Installation

Building and Installing on Centos/Linux
1. Enable wget if not enable/installed
   1. $ yum install wget -y
2. Install JDK 1.8 (This should only be done if Maven/mvn cannot run because JDK 1.8 is missing)
   1. $ mkdir /opt/java && cd /opt/java
   2. For 32-bit Systems
     * $ cd /opt/java
     * $ wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u45-b14/jdk-8u45-linux-i586.tar.gz"
   3. For 64-bit Systems
     * $ cd /opt/java
     * $ wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u45-b14/jdk-8u45-linux-x64.tar.gz
   4. Once file has been downloaded, you may extract the tarball using tar command as shown below:
      1. $ tar -zxvf jdk-8u45-linux-i586.tar.gz		[For 32-bit Systems]
      2. $ tar -zxvf jdk-8u45-linux-x64.tar.gz		[For 64-bit Systems]
   5. Next, move to the extracted directory and use command update-alternatives to tell system where java and its executables are installed.
      1. $ cd jdk1.8.0_45/
      2. $ update-alternatives --install /usr/bin/java java /opt/java/jdk1.8.0_45/bin/java 100  
      3. $ update-alternatives --config java
   6. Tell system to update javac alternatives as:
      1. $ update-alternatives --install /usr/bin/javac javac /opt/java/jdk1.8.0_45/bin/javac 100
      2. $ update-alternatives --config javac
   7. Similarly, update jar alternatives as:
      1. $ update-alternatives --install /usr/bin/jar jar /opt/java/jdk1.8.0_45/bin/jar 100
      2. $ update-alternatives --config jar
   8. Setting up Java Environment Variables.
      1. $ export JAVA_HOME=/opt/java/jdk1.8.0_45/	
      2. $ export JRE_HOME=/opt/java/jdk1.8.0_45/jre 	
      3. $ export PATH=$PATH:/opt/java/jdk1.8.0_45/bin:/opt/java/jdk1.8.0_45/jre/bin
   9. Now You may verify the Java version again, to confirm.
      1. $ java -version
3. Install Apache Maven:
   1. You can add maven to the yum libraries like this:
      1. $ wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
   2. Now you can install maven like this:
      1. $ yum install apache-maven
   3. Check version:
      1. $ mvn -version
4. Install Docker:
   1. Update the package database:
      1. $ sudo yum check-update
   2. Add the official Docker repository, download the latest version of Docker, and install it:
      1. $ curl -fsSL https://get.docker.com/ | sh
   3. After installation has completed, tart the docker service:
      1. $ sudo service docker start
5. Copy the source code to the server:
   1. Install Git:
      1. $ sudo yum install git
   2. Create a folder for the source code:
      1. $ cd ~
      2. $ mkdir /sourcecode
   3. Go the the sourcecode directory:
      1. $ cd /sourcecode
   4. Get the source code from git:
      1. The first time (Clone):
         * $ git clone https://github.com/Evolve-IT/directory-info-service.git
      2. If it has already been cloned (For updates from git), do a pull:
         * $ git pull https://github.com/Evolve-IT/directory-info-service.git  master
6. Build the project using Maven:
   1. go to the directory-info-service folder:
      1. $ cd /sourcecode/directory-info-service/directory-info-service
   2. Run: $ mvn clean
      1. This does a clean and downloads the required dependencies
   3. Run: $ mvn install
      1. This installs the dependencies for the project, builds the project and creates the directory-info-service.jar file which contains all of the source code and references.
7. Build and run the docker image:
   1. Run: docker build -f [Dockerfile name] -t [Docker image name (has to be lowercase)] . (notice the space dot at the end)
      1. Thus run: $ docker build -f Dockerfile -t directoryinfoservice .
      2. This builds the docker image
   2. Run: $ docker images
      1. Gets a list of docker images
      2. You should see the directoryinfoservice image in the list if it was successfully built
   3. Run: docker run -p [publish exposed port] -i -t [dockerimage]
      1. Thus Run: $ docker run -p 8080:8080 -i -t directoryinfoservice
      2. This will run the docker image and expose port 8080 on the docker image as port 8080 to the "outside world"
   4. Press Ctrl + P + Ctrl + Q (to allow you to type and see the input if it is still attached to the docker container)


## Usage

Follow these steps in order to call the REST service:

1. Get the ip address of the docker container:
   1. Run: $ docker ps
      1. This will show a list of docker containers
      2. Use the CONTAINER_ID which is displayed of the directoryinfoservice to get the ip address below.
   2. $ docker inspect --format '{{ .NetworkSettings.IPAddress }}' CONTAINER_ID
      1. This should display the docker container's IP Address
      2. If this doesn't work, use one of the commands on this page: http://networkstatic.net/10-examples-of-how-to-get-docker-container-ip-address/
2. If Curl is not enabled, enable it:
   1. $ yum install curl
3. Send a request to the directoryinfoservice:
   1. For Json Output:
      1. $ curl -X GET http://[Docker Image IP Address]:[Docker Image Port]/svc/v1/directoryinfo/getDirectoryInfoJson/?directory=[directory]
         * Example: $ curl -X GET http://172.17.0.1:8080/svc/v1/directoryinfo/getDirectoryInfoJson/?directory=/tmp
      2. You can add > [somedirectory]/[output filename] to the end of the curl command to write the output to a file
         * Example: $ curl -X GET http://172.17.0.1:8080/svc/v1/directoryinfo/getDirectoryInfoJson/?directory=/tmp > /tmp/directory_info_service_output_json.txt
      3. You should see the json returned by the rest service
      4. An object with the following structure will be returned
   2. For Xml Output:
      1. $ curl -X GET http://[Docker Image IP Address]:[Docker Image Port]/svc/v1/directoryinfo/getDirectoryInfoXml/?directory=[directory]
         * Example: $ curl -X GET http://172.17.0.1:8080/svc/v1/directoryinfo/getDirectoryInfoXml/?directory=/tmp 
      2. You can add > [somedirectory]/[output filename] to the end of the curl command to write the output to a file
         * Example: $ curl -X GET http://172.17.0.1:8080/svc/v1/directoryinfo/getDirectoryInfoXml/?directory=/tmp > /tmp/directory_info_service_output.xml
      3. You should see the xml returned by the rest service
   3. The data returned by both methods will be in the following format
      1. DirectoryListingResult
         1. This is the header object which contains the following fields
            1. directoryInfo
               1. This object contains all of the details related to the directory which was passed in as the directory input parameter.
               2. It contains the following fields
                  1. type
                     1. There are 5 types 
                        1. "Directory" - A folder or directory
                        2. "File" - A regular file
                        3. "Other" - It is not a regular file, symbolic link or directory
                        4. "SymbolicLink" - A Symbolic Link
                        5. "Unknown" - If an exception occurs during the gathering of attribute information due to permissions or other security exceptions
                  2. fullPath
                     * The full path of the directory
                  3. attributes
                     1. This is a collection of objects of type attribute which represents the attributes of the file or directory with the following fields
                        1. name
                           * The name of the attribute
                        2. value
                           * The value of the attribute
                     2. An attribute with name "ErrorMessage" will be added to a directoryInfo object's attributes if an exception occurred during the accessing of a file or directory's attributes.
                     3. Possible attributes:
                        1. FileSize
                        2. LastAccessTime
                        3. LastModifiedTime
                        4. CreationTime
                  4. children
                     * This is a collection of objects of type directoryInfo (same as described above) which represents all of the child files and directories. 
                     * This will only be populated if the current item is a directory.
            2. directorySize
               * This is the size of the directory listing. It is an interger which represents the number of files and folders contained in the input folder.
            3. success
               * This is a boolean field which will be:
                  * "true" if a directory listing could be retrieved without any errors.
                  * "false" if an exception occurred during the retrieval of a directory listing.
            4. errorMessage
               * This will be populated with a user friendly error message if an exception occurred during the retrieval of a directory listing.
   4. Example of Xml returned by the REST Service

```xml
		<DirectoryListingResult>
			<directoryInfo>n
				<type>Directory</type>
				<fullPath>C:\DirectoryInfo</fullPath>
				<attributes>
					<attribute>
						<name>LastAccessTime</name><value>2017-03-26T14:25:40.493221Z</value>
					</attribute>
					<attribute>
						<name>LastModifiedTime</name><value>2017-03-26T14:25:40.493221Z</value>
					</attribute>
					<attribute>
						<name>CreationTime</name><value>2017-03-26T14:24:04.736269Z</value>
					</attribute>
					</attributes>
				<children>
					<directoryInfo>
						<type>File</type>
						<fullPath>C:\DirectoryInfo\Test File 1.txt</fullPath>
						<attributes>
							<attribute>
								<name>LastAccessTime</name><value>2017-03-26T14:24:27.136322Z</value>
							</attribute>
							<attribute>
								<name>LastModifiedTime</name><value>2017-03-26T14:25:11.43494Z</value>
							</attribute>
							<attribute>
								<name>CreationTime</name><value>2017-03-26T14:24:27.136322Z</value>
							</attribute>
							<attribute>
								<name>FileSize</name><value>404640</value>
							</attribute>
							</attributes>
							<children/>
					</directoryInfo>
					<directoryInfo>
						<type>File</type>
						<fullPath>C:\DirectoryInfo\Test File 2.txt</fullPath>
						<attributes>
							<attribute>
								<name>LastAccessTime</name><value>2017-03-26T14:25:17.564302Z</value>
							</attribute>
							<attribute>
								<name>LastModifiedTime</name><value>2017-03-26T14:25:56.008242Z</value>
							</attribute>
							<attribute>
								<name>CreationTime</name><value>2017-03-26T14:25:17.564302Z</value>
							</attribute>
							<attribute>
								<name>FileSize</name><value>183774</value>
							</attribute>
						</attributes>
						<children/>
					</directoryInfo>
					<directoryInfo>
						<type>Directory</type>
						<fullPath>C:\DirectoryInfo\Test Folder 1</fullPath>
						<attributes>
							<attribute>
								<name>LastAccessTime</name><value>2017-03-26T14:25:33.999219Z</value>
							</attribute>
							<attribute>
								<name>LastModifiedTime</name><value>2017-03-26T14:25:33.999219Z</value>
							</attribute>
							<attribute>
								<name>CreationTime</name><value>2017-03-26T14:25:33.999219Z</value>
							</attribute>
						</attributes>
						<children/>
					</directoryInfo>
				</children>
			</directoryInfo>
			<directorySize>4</directorySize>
			<success>true</success>
			<errorMessage/>
		</DirectoryListingResult>
```


## Contributing

N/A

## History

This is version 1.0 of the application.

## Credits

N/A

## License

Anyone is free to make use of the source code.
