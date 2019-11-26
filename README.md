# InvoiceDemo

Invoice REST API Demo

The API Project has been done in IntelliJ IDE.

The API is written as a Springboot service.

The database configured is H2 using JPA and does not persist to disk

The h2 console is accessbile via http://localhost:8080/h2
Username: sa
Password: password

Run the springboot app in IntelliJ

The project has swagger enabled on it.
http://localhost:8080/swagger-ui.html

The sytem has logging enabled on it as well. The log files are created on a daily basis and are under the logs directory of project root.

Integration Test are in the InvoiceApplicationTests class.

Sample Invoice Object for Post:

{
  "client": "Test Client",
  "invoiceDate": "2019-11-16T14:45:28.712Z",
  "lineItems": [
    {
      "description": "Item 1",
      "quantity": 2,
      "unitPrice": 20
    }
  ],
  "vatRate": 15
}


