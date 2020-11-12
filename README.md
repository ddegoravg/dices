# Requirements
1. Java 11+
2. maven (tested with 3.5.4 - but wrapper also provided)

# Building

mvn clean install

# Executing

like any spring boot application: i.e: 

    java -jar target/dices-1.0.0.jar

# Configure CheckStyle-IDEA plugin
        
- File -> Settings -> Other Settings -> Checkstyle:
        
                Checkstyle version: 8.0
                Scan Scope: All sources (including tests)
                Add checkstyle.xml as Configuration File and check as Active
            
## Configure IDEA checkstyle
        
- File -> Settings -> Editor -> Code Style:
        
                Scheme: Project
                Add checkstyle.xml - gearwheel -> Import Scheme -> CheckStyle Configuration
                Right margin (columns): 150

- File -> Settings -> Editor -> Code Style -> Java:
                Spaces -> Before Left Brace: uncheck Annotation array initializer left brace   
            
- File -> Settings -> Editor -> Code Style -> Java -> Imports:
            
                Use single class import checked on
                Class count to use import with '*': 999
                Names count to use static import with '*': 999
            
- File -> Settings -> Editor -> Code Style -> Java -> Imports -> Import Layout:
            
                Layout static imports separately checked on
                Layout:
                    import static all other imports
                    <blank line>
                    import all other imports
                    <blank line>
                    
# Assumptions and simplifications
    1. Tests are currently not divided into unit and integration tests - there are not so many at the moment and the execution time is not long
    2. In-memory H2 is used in both "production" execution and in tests.
    3. Database model was prepared only for purpose of those two assignments.
    4. roller package contains common logic, rollerv1 what was required by Assignement 1, rollerv3 by Assignment 3
    5. there are two controllers - one for Assignment 1 (when no change is done on the server hence GET only), 
            other for Assignment 3 - one creating simulation in DB - this time stores data on a server, so simulation creation is now POST request
           There is a postman collection in misc folder to make the testing easier
    6. Only required validation were applied - normally some additional questions about max parameters values would be asked and the validations implemented accordingly.
             
                   