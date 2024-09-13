# RomanNumeral_Java

The Purpose of this project is to create an endpoint that accepts a URI in the format of `http://localhost8080/romannumeral?query={integer}` and return the integer as a Roman Numeral. An example of the response would look like this:

```
{
    input: "1",
    output: "I"
}
```

An error response will just be plain text format.

Instead of using Spring I'll utilize basic Java libraries like com.sun.net.httpserver.HttpServer for handling HTTP requests.

## Getting Started

1. Clone project
2. Run `cd` into the `api` directory and run `javac RomanNumeralConverter.java`
3. Start the server with `java RomanNumeralConverter` and notice it's running on localhost.

4. profit.

Here is documentation regarding the various types of roman numerals
https://www.britannica.com/topic/Roman-numeral

You'll notice that multiples of 1000 have a vinculum or bar placed over the numeral. To keep the project simple and not get lost in the minutia of mathematical notation we'll handle multiples of 1000 verbatim. For example 4000 would read MMMM instead of I̅V̅.

You can read more about the vinculum here https://en.wikipedia.org/wiki/Vinculum_(symbol)
