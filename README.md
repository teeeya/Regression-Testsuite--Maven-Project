Regression-Testsuite--Maven-Project
===================================

This is a regression testsuite task that was set out as an (pre-interview) automated test problem.
This can be downloaded and run with the following steps:

Pre-requisites
1. Need to be using Java 1.6
2. Maven installed


1. Download the project
2. Import the project into eclipse as an "existing project.."
The project will have a red "!" on the project (There are problems with the build path)
3. Open terminal/cmd
Navigate to project folder
run the following command to resolve/add all dependancies
mvn eclipse:eclipse
Refresh the project and the exclamaition mark should disappear.
4. Run this project within eclipse
- Right click on the project and click on "Run as" -> "Run Configurations"
- Add the following arguments:

./tests/questions.xml http://api.trueknowledge.com:80/direct_answer/?  api_tktestrole  12bek2bldxxqp3iv

5. Run project


This testsuite will do the following:

1. Load in a config.xml file and extract the pieces of information for each scenario (question, asnwer, timeout). This is needed to execute and verify the success of each test.
2. It will pass in the parameter (question) set in the config file to an API
3. The time for each requst and answer returned will be stored
4. For each scenario the answer will be verified 
5. For each scenario the time taken will be checked against expected time (timeout) set in the config file.
6. The results will then be logged and a report file will be created for each scenario.
