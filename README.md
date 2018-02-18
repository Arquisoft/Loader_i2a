# Loader i2a

[![Build Status](https://travis-ci.org/Arquisoft/Loader_i2a.svg?branch=master)](https://travis-ci.org/Arquisoft/Loader_i2a)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e61d81ba48f947d3a5423c69b6e605b4)](https://www.codacy.com/app/jelabra/Loader_i2a?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Arquisoft/Loader_i2a&amp;utm_campaign=Badge_Grade)
[![codecov](https://codecov.io/gh/Arquisoft/Loader_i2a/branch/master/graph/badge.svg)](https://codecov.io/gh/Arquisoft/Loader_i2a)

## Setup
As our code uses MongoDB although we have created an online instance at [mlab] (https://mlab.com/) the user will not have to install nothing related to it. We can say that the program is close to be ready to use, the only thin that will be needed is to download/install [Maven](https://maven.apache.org/).

## How to download and install Maven
### Windows
Download Maven (binary zip archive format) from the following [link] (https://maven.apache.org/).

Once you have downloaded it, create a folder named "maven" in "C:\" this is a safe place of your computer and ensures you will not remove the files containing maven accidentally, after you will have to extract the downloaded zip into the "maven" folder created. 

The next process will be to add Maven your Path variable, in order to do this go to Control Panel > System and Security > System > Advanced system configuration. Click on Environment Variables, and edit select Path on User variables and click edit, once you are in the next screen click new and copy the root to the bin folder inside apache-maven-x.x.x (been x.x.x the version installed) for example this was the root in my case "C:\maven\apache-maven-3.5.2\bin", finally save before exiting.

### Linux
Installing Maven in Linux is easy and straightforward, you will just have to type the following command:

```
sudo apt-get install maven
```

## How to run the application
### Prepare files for running process
In order to run the application in batch format we should first generate the .jar file needed in the running process. This will all be done by typing the following command while been located in the folder where we have the pom.xml file.

```
cd (location of pom.xml
mvn package
```
The next process is to move the generated .jar file one level up from the target folder with the following command.

#### In Windows
```
cd target
move .jar ../
cd ..
```

#### In Linux
```
cd target
mv .jar ../
cd ..
```

### Running the applicaiton
In order to run the application move the .xls and .csv files that are going to be used to the location where the .jar file is. TO run the application you will have to specify in this concrete order .xls mongoHOST .csv. In our case as we have a running instance of MongoDB in mlab.com we use "mongodb://admin:EIIASW2018$@ds229448.mlab.com:29448/db_loader_i2a" as our host
```
java -jar .jar .xls mongoHost .csv
```


## Team members 2018
* Jesús Atorrasagasti García [@jesusatgar] (https://github.com/jesusatgar)
* Juan Aza Gutiérrez [@juanaza] (https://github.com/juanaza)
* Lorena Castillero Corriols [@lorenacasti] (https://github.com/lorenacasti)

## Team members
* Nicolás Pascual González (@nicolaspascual)
* Jorge López Fueyo (@nokutu)
* Miguel García García (@miguelgrc)
* Pablo García Marcos (@pineirin)

