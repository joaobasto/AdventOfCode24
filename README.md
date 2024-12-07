# AdventOfCode24
Solutions for Advent of Code 2024

# Profiling the application

## Set up IntelliJ to build an output Jar

Check instructions here: https://stackoverflow.com/questions/1082580/how-to-build-jars-from-intellij-idea-properly

## Running the application with JFR enabled for profiling

From the directory where the jar was created ( `Solutions/out/artifacts/Solutions_jar` ) run the following command:
`java -XX:StartFlightRecording=duration=1m,filename=recording.jfr,settings=profile -jar Solutions.jar`

## Using Java Mission Control

- Get OpenJDK JMC from here: https://adoptium.net/jmc/
- Open the recording.jfr file in JMC.
- Flame graph is not displayed in Ubuntu due to an existing incompatibility
- But you can use the stack trace on "Environment" -> "Processes" -> "(Legacy) Threads" to understand bottleneck methods.
