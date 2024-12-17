@@ -68,15 +68,20 @@ After a successful purchase, users will receive a confirmation email. The projec
<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Code description
### Dependencies
  > Thymeleaf,
  > Spring Web,
  > Spring Boot Web Tools,
  > MySql Driver,
  > Spring Data JDBC
### Java
  - [Database Config](DatabaseConfig.java) - _configures the connection to the MySql database_
  - [Mail Config](MailConfig.java) - _configures the connection to gmail_
  - [Email Service](EmailService.java) - _provides some useful mail templates_
  - [Mobili](Mobili.java) - _is the personal computer in store class: it has model name, brand,description, price, image, quantity available in store and quantity sold. reflects the columns of the database table_
  - [MobiliA](MobiliA.java) - _is the personal computer in shopping cart class: it has model name, brand,description, price, image, quantity selected for purchase_
  - [JdbcTemplate](JdbcTemplate.java) - _is the class containing the database queries: select, insert, delete and update quantity (to update the available number and sold number of a given pc)_
  - [MyController](MyController.java) - __
  - [MyController](MyController.java) - _a class that handles HTTP requests and responses, facilitating the interaction between the front end and the back end of the application._
### Html
  - [Layout](templates/layout/layout.html) - _the layout of every html page. It contains the css libraries, JavaScript scripts, a navbar, a sidebar and a footer_
  - [Index](templates/index.html) - _the homepage: it contains a shop description, the card of the item sold in shop and a div with a random mario card obtained interrogating an API_
@@ -93,7 +98,7 @@ feel free to reach out and give me suggestions.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Contributors:
## Contributors:
