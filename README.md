<h1>Project Building</h1>

1. Add an ENV variable called <b>"ORS_TOKEN"</b> with your OpenRoute token 

2. Use the command <b>mvn clean install</b> to compile the project, run unit tests and generate an executable jar

<h1>Project Running</h1>

Use the following commands to execute the program

* java -jar CO2-Calculation-1.0.jar -start Hamburg -end Berlin -transportation-method medium-diesel-car

* java -jar CO2-Calculation-1.0.jar -start "Los Angeles" -end "New York" -transportation-method=medium-diesel-car

* java -jar CO2-Calculation-1.0.jar -end "New York" -start "Los Angeles" -transportation-method=large-electric-car


<h1>Run Unit tests</h1>

1. Use the command <b>mvn test</b>