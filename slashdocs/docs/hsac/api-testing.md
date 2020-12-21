API-testing is an important part of a well-balanced testing pyramid.

HSAC contains fixtures for testing http(s)-based API's with e.g. JSON payloads like the following:

```JSON
{
  "property": "myValue",
  "accessToken": "00000000-0000-0000-0000-000000000000",
  "customerId": "MyCustomerName@domain.com",
  "attributes": [
    {
      "key": "YourKey",
      "value": "YourValue"
    },
    {
      "value": "YourValue"
    }
  ]
}
``` 
