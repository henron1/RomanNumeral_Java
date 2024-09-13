# RomanNumeral_Java

The Purpose of this project is to create an endpoint that accepts a URI in the format of `http://localhost8080/romannumeral?query={integer}` and return the integer as a Roman Numeral. An example of the response would look like this:

```json
{
  "input": "1",
  "output": "I"
}
```

An error response will just be plain text format.

Instead of using Spring I'll utilize basic Java libraries like com.sun.net.httpserver.HttpServer for handling HTTP requests.

Here is documentation regarding the various types of roman numerals
https://www.britannica.com/topic/Roman-numeral

You'll notice that multiples of 1000 have a vinculum or bar placed over the numeral. To keep the project simple and not get lost in the minutia of mathematical notation we'll handle multiples of 1000 verbatim. For example 4000 would read MMMM instead of I̅V̅.

You can read more about the vinculum here https://en.wikipedia.org/wiki/Vinculum_(symbol)
The test suite uses Maven so if you haven't downloaded and configured that please see these docs.
https://maven.apache.org/install.html
(You may also need to add the bin directory of the created directory apache-maven-3.3.9 to the PATH environment variable)

## Getting Started

1. Clone project and `cd` into root level.
2. Start tests by running `mvn test` - you may have to run `mvn install clean` first to set up the environment.

```bash
mvn install clean
mvn test
```

3. Notice how wonderfully all the tests pass.
4. `cd` into the `src/main/java` directory and run `javac RomanNumeralConverter.java`

```bash
cd src/main/java
javac RomanNumeralConverter.java
```

5. Start the server with `java RomanNumeralConverter` and notice it's running on localhost.

```bash
java RomanNumeralConverter
```

6. profit.

To test the application you can download Postman, Insomnia or a similar program to test the endpoint. 
