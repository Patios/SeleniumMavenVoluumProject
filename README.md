# SeleniumMavenVoluumProject

Basing on selenium maven template project create own testsuite for testing voluum.com web application.

#To run project use:

mvn clean verify

Then after build in target directory is failsafe-reports generated. 

#To run just testing class (TestNG needed).

Execute testNG test from  path:

src\test\java\com\selenium\tests\VoluumHomePageTestWebDriver.java 

- By default FIREFOX browser is set.

#You can specify which browser to use by using one of the following switches:

- -Dbrowser=firefox
- -Dbrowser=chrome
- -Dbrowser=ie
- -Dbrowser=opera
- -Dbrowser=htmlunit
- -Dbrowser=phantomjs


### It's not working!!!

You have probably got outdated driver binaries, by default they are not overwritten if they already exist to speed things up.  You have two options:

- mvn clean verify -Doverwrite.binaries=true
- Delete the selenium_standalone_binaries folder in your resources directory