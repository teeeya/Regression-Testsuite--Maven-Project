Regression-Testsuite--Maven-Project
===================================


1. Download the project
2. Import the project into eclipse as an "existing project"
The project should have a red "!" on the project (There are problems with the build path)
3. Open terminal/cmd
Navigate to project
run the following command to resolve/add all dependancies
mvn eclipse:eclipse
Refresh the project and this should disappear.
4. Run this project in eclipse
- Right click on the project and click on "Run as" -> "Run Configurations"
- Add the following arguments:

./tests/questions.xml http://api.trueknowledge.com:80/direct_answer/?  api_tktestrole  12bek2bldxxqp3iv

5. Run project
