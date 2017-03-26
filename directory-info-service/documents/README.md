# directory-info-service

This is a spring boot REST service which has been encapsulated in a docker container.
The REST service can be used to get a directory listing of a directory on the server where the docker container is running.

## Installation

Building and Installing on Centos/Linux:
1.	Enable wget if not enable/installed:
	a.	# yum install wget -y
2.	Install JDK 1.8 (This should only be done if Maven/mvn cannot run because JDK 1.8 is missing):
	a.	# mkdir /opt/java && cd /opt/java
	b.	For 32-bit Systems
		i.	# cd /opt/java
		ii.	# wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u45-b14/jdk-8u45-linux-i586.tar.gz"
	c.	For 64-bit Systems
		i.	# cd /opt/java
		ii.	# wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u45-b14/jdk-8u45-linux-x64.tar.gz
		iii.	Once file has been downloaded, you may extract the tarball using tar command as shown below:
			1.	# tar -zxvf jdk-8u45-linux-i586.tar.gz		[For 32-bit Systems]
			2.	# tar -zxvf jdk-8u45-linux-x64.tar.gz		[For 64-bit Systems]
	d.	Next, move to the extracted directory and use command update-alternatives to tell system where java and its executables are installed.
		i.	# cd jdk1.8.0_45/
		ii.	# update-alternatives --install /usr/bin/java java /opt/java/jdk1.8.0_45/bin/java 100  
		iii.	# update-alternatives --config java
	e.	Tell system to update javac alternatives as:
		i.	# update-alternatives --install /usr/bin/javac javac /opt/java/jdk1.8.0_45/bin/javac 100
		ii.	# update-alternatives --config javac
	f.	Similarly, update jar alternatives as:
		i.	# update-alternatives --install /usr/bin/jar jar /opt/java/jdk1.8.0_45/bin/jar 100
		ii.	# update-alternatives --config jar
	g.	Setting up Java Environment Variables.
		i.	# export JAVA_HOME=/opt/java/jdk1.8.0_45/	
		ii.	# export JRE_HOME=/opt/java/jdk1.8.0_45/jre 	
		iii.	# export PATH=$PATH:/opt/java/jdk1.8.0_45/bin:/opt/java/jdk1.8.0_45/jre/bin
	h.	Now You may verify the Java version again, to confirm.
	i.	# java –version
3.	Install Apache Maven:
	a.	You can add maven to the yum libraries like this:
		i.	# wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
	b.	Now you can install maven like this:
		i.	# yum install apache-maven
	c.	Check version:
		i.	# mvn –version
4.	Install Docker:
	a.	Update the package database:
		i.	# sudo yum check-update
	b.	Add the official Docker repository, download the latest version of Docker, and install it:
		i.	# curl -fsSL https://get.docker.com/ | sh
	c.	After installation has completed, tart the docker service:
		i.	# sudo service docker start
5.	Copy the source code to the server:
	a.	Install Git:
		i.	# sudo yum install git
	b.	Create a folder for the source code:
		i.	# cd ~
		ii.	# mkdir /sourcecode
	c.	Go the the sourcecode directory:
		i.	# cd /sourcecode
	d.	Get the source code from git:
		i.	The first time (Clone):
			1.	# git clone https://github.com/Evolve-IT/directory-info-service.git
		ii.	If it has already been cloned (For updates from git), do a pull:
			1.	# git pull https://github.com/Evolve-IT/directory-info-service.git  master
6.	Build the project using Maven:
	a.	go to the directory-info-service folder:
		i.	# cd /sourcecode/directory-info-service/directory-info-service
	b.	Run: # mvn clean
		i.	This does a clean and downloads the required dependencies
	c.	Run: # mvn install
		i.	This installs the dependencies for the project, builds the project and creates the directory-info-service.jar file which contains all of the source code and references.
7.	Build and run the docker image:
	a.	Run: docker build -f <Dockerfile name> -t <Docker image name (has to be lowercase)> . (notice the space dot at the end)
		i.	Thus run: # docker build -f Dockerfile -t directoryinfoservice .
		ii.	This builds the docker image
	b.	Run: # docker images
		i.	Gets a list of docker images
		ii.	You should see the directoryinfoservice image in the list if it was successfully built
	c.	Run: docker run -p <publish exposed port> -i -t <dockerimage>
		i.	Thus Run: # docker run -p 8080:8080 -i -t directoryinfoservice
		ii.	This will run the docker image and expose port 8080 on the docker image as port 8080 to the “outside world”
	d.	Press Ctrl + P + Ctrl + Q (to allow you to type and see the input if it is still attached to the docker container)


## Usage

TODO: Write usage instructions

## Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

## History

This is version 1.0 of the application.

## Credits

N/A

## License

Anyone is free to make use of the source code.
