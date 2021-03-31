Package to import: _nl.praegus.fitnesse.slim.fixtures.mockserver_

A fixture to create a mockserver or proxy (or a mix of both)

This fixture supports matching request body, content type, cookies, headers, methods, url paths and querystring.
Matching for body can be done with regular expression, xpath, jsonpath or exact match. 
It supports returning complex response object containing a body, content-type and headers, statuscode and cookies. Binary data is also supported.
As a proxy it supports forwarding based on only the incoming request path or based on the matching request filters.
After testing, it can show the traffic the mockserver has seen. 
The mockserver implementation supports http en https.
see https://www.mock-server.com/mock_server/HTTPS_TLS.html

## How to set up a mockserver
### How to create requests match
A request match (e.g. the criteria a request has te meet to be recognized as a request that should trigger a response) is created by using a map fixture.
Path matching supports regular expression. For example "/test/*" for all paths that start with "/test/".
Body matching supports "jsonpath=" , "xpath=" and "regex=". 
An example of a requestMatch:
```
|script        |map fixture                               |
|set value     |/test            |for|path                |
|set value     |GET              |for|method              |
|set value     |application/json |for|content-type        |
|set value     |!-jsonpath=$.*-!|for|body                |
|set value     |test             |for|headers.customheader|
|set value     |choco            |for|cookies.yummy_cookie|
|set value     |123456           |for|querystring.id      |
|$requestMatch=|copy map                                 |
```


### How to create response definition
Response objects are also created using a map fixture.
An example of a responseDefinition:
```
|script          |map fixture                                                  |
|set value       |200                                 |for|status              |
|set value       |application/json                    |for|content-type        |
|set value       |{"state": "OK!", "body": "response"}|for|body                |
|set value       |choco                               |for|cookies.yummy_cookie|
|set value       |test                                |for|headers.customheader|
|$responseDefinition=|copy map                                                |
```

### Binary Support
There is support for three different ways to include binary files as a body to a response definition:
1. Reference to the filesystem:
```
|set value|C:\testdata\Binary.json|for|body|
```

2. Relative path to FitNesse files directory:
```
|set value|files/Binary.json|for|body|
```

3. URL path to FitNesse files directory:
```
|set value|http://files/Binary.json|for|body|
```

### Starting the mockserver
After creating request matches and response objects the mockserver can be started (on a designated port) and configured with the created request matches an response objects. Once started the mockserver can handle response. If you want to use https the mockserver should be started on port 443.
For example:
```
|script             |mock server  |1080                                           |
|set response for   |$requestMatch|to|$responseDefinition                       |
|forward requests on|/forward     |to|!-http://localhost:1090-!|with path|/forward|
|$mockserver1080=   |get fixture                                                 |
```
Normally testing is would be done by connection de SUT to the mockserver. For testing purposes you could use a http request fixture (for example json http request) to send calls to your mock server.

### Analyzing and display traffic and stopping de mockserver
When testing is done the mockserver can show what traffic it has received. With the "check" keyword these can also be used as validations in your tests. The final step should be to stop the mockserver.
```
|script|$mockserver1080                                |
|check |number of requests for path|/.*       |2       |
|show  |recorded requests and responses                |
|show  |recorded Requests For Path |/path1             |
|check |errored requests           |[/errorrequest 404]|
|stop mock server                                      |
```

## Methods
#### set response body for (path) to (body)
Set the mock response body for any request on the given path
- path: The path to set this response for (supports regular expressions)
- body: The response body as a String

#### set response body for (path) to (body) with status (status)
Set a mock response body with a given status code for any request on the given path
- path: The path to set this response for (supports regular expressions)
- body: The response body as a String
- status: The status code to respond with

#### set binary response for (path) to (file reference)
Sets a binary file as the response for any request on the given path
- path: The path to set this response for (supports regular expressions)
- file: The file to respond with (Can be an absolute path, a path relative to /files or a wiki file path)

#### set response for (request match) to (response definition)
Sets a response defined in the response definition hashmap for any request that matches the rules defined in the request matching hashmap
- requestMatching: a map object containing request filter rules. Valid rules are: method, path, content-type, cookies, querystring, headers, body
- responseDefinition: a map object containing the definition of the response. Valid fields are: body, status, headers, content-type, cookies

#### forward requests matching (request match) to (target) with path (targetPath)
forwards a request to the target host/port and path for any request that matches the rules defined in the request matching hashmap
- requestMatching: a map object containing request filter rules. Valid rules are: method, path, content-type, cookies, querystring, headers, body
- target: The host/port to forward to (http(s)://host[:port])
- targetPath: The path to forward to

#### forward requests on (path) to (target)
Forward any request on the given path to the target host/port
- path: The path to forward requests for
- target: The host/port to forward to (http(s)://host[:port])

#### forward requests on (path) to (target) with path (forwardPath)
Forward any request on the given path to the target host/port/path
- path: The path to forward requests for
- target: The host/port to forward to (http(s)://host[:port])
- forwardPath: The path to forward to

#### stop mock server
Stops the running of the mockserver

#### recorded requests
Retrieve all recorded requests from the mockserver in a map.

#### recorded requests for path (path)
Retrieve recorded requests for a specific path from the mockserver in a map.

#### number of requests for path (path)
Retrieve the number of recorded requests for a specific path from the mockserver.

#### recorded requests and responses
Retrieve all recorded requests and responses from the mockserver in a map.

#### recorded requests and responses for path (path)
Retrieve recorded requests and responses for a specific path from the mockserver in a map.
- path: The path for which to retrieve the recorded requests and responses for

#### errored requests
Retrieve request endpoints that returned an error status code (equal to or greater than status code 400).