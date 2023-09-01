
[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-f4981d0f882b2a3f0472912d15f9806d57e124e0fc890972558857b51b24a6f9.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=10477700)
[![CI](https://github.com/2berk4/soft3102-project-template/actions/workflows/starter-action.yml/badge.svg?branch=main)](https://github.com/2berk4/soft3102-project-template/actions/workflows/starter-action.yml)

# CarDetail Rental System

This is a CarDetail Rental System project that utilizes Spring Boot 3.0 and React JS 18 as the main technologies. It is a web application that allows users to rent cars for a specified duration.

## Features

- User Authentication: Users can register and login to the system to access the car rental functionalities.
- CarDetail Listings: Users can view the available cars for rent along with their details such as make, model, and price per day.
- CarDetail Search: Users can search for cars based on criteria such as location, make, and model.
- CarDetail Reservation: Users can reserve a car for a specified duration by providing the pickup and return dates.
- Payment Integration: Users can make payments for the car rental using popular payment gateways such as PayPal or Stripe.
- Booking History: Users can view their booking history, including details of past and upcoming reservations.
- Admin Dashboard: An admin dashboard is available for the system administrator to manage cars, bookings, and user accounts.
- Manager Dashboard: A manager dashboard is available for users with the MANAGER role to manage cars and bookings.
- User Page: A user page is available for regular users to manage their bookings.

## Technologies Used

- [Spring Boot 3.0](https://spring.io/projects/spring-boot): A Java-based framework for building robust and scalable web applications.
- [React JS 18](https://reactjs.org/): A popular JavaScript library for building user interfaces.
- [MySQL](https://www.mysql.com/): A widely used relational database management system for data storage.
- [Spring Security](https://spring.io/projects/spring-security): A powerful security framework for securing the application and implementing authentication and authorization.
- [Redux](https://redux.js.org/): A state management library for managing the state of the application in a predictable and efficient manner.
- [Axios](https://axios-http.com/): A popular HTTP client for making API calls from the frontend to the backend.
- [Material-UI](https://mui.com/): A widely used UI component library for building responsive and visually appealing user interfaces.
- [Stripe/PayPal API](https://stripe.com/docs/api, https://developer.paypal.com/docs/api/overview/): APIs for integrating with popular payment gateways to facilitate online payments.
- [Java Mail Starter](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.email): A Spring Boot starter for sending emails using the Java Mail API.

## Prerequisites

Before running the CarDetail Rental System project, ensure that the following software is installed:

- Java JDK 8 or later
- Node.js 14 or later
- MySQL 8 or later

## Getting Started

1. Clone the repository to your local machine using `git clone`.
2. Import the backend Spring Boot project into your preferred IDE.
3. Update the database configuration in the `application.properties` file with your MySQL database credentials.
	 - The CarDetail Rental System uses the following configuration in the `application.properties` file for MySQL database connection and Spring Mail Server settings:
### MySQL Database Configuration
    spring.datasource.url=jdbc:mysql://localhost:3306/car_rental_system
    spring.datasource.username=your_mysql_username
    spring.datasource.password=your_password
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.jpa.hibernate.ddl-auto=update
    spring.mail.host=smtp.gmail.com
    spring.mail.port=587
    spring.mail.username=your_email@gmail.com
    spring.mail.password=your_email_password
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true

4. Run the backend application by executing the `main()` method in the `Application` class.
5. Change directory to the `frontend` folder and install the frontend dependencies using `npm install`.
6. Start the frontend application by running `npm start`.
7. Access the CarDetail Rental System application in your web browser at `http://localhost:3000`.

## API Documentation

The CarDetail Rental System has a Swagger OpenAPI configuration accessible at [http://localhost:8080/docs/swagger-ui/index.html](http://localhost:8080/docs/swagger-ui/index.html). This API documentation provides information on the endpoints available in the system, request/response payloads, and error messages.

Swagger OpenAPI provides a comprehensive and machine-readable documentation of the CarDetail Rental System's API. This documentation can be used by developers to understand how to interact with the system's endpoints, and by clients to integrate the system's API into their applications.

The API endpoints are organized by resource, and provide the following functionality:


- Cars: View, add, edit, and delete cars from the system.
- Bookings: View, add, edit, and cancel bookings made by users.
- Users: View, add, edit, and delete user accounts.
- Colors: View, add, edit, and delete colors.
- Brands: View, add, edit, and delete brands.

## Roles and Permissions

The CarDetail Rental System has three roles:

- ADMIN: The system administrator role with access to the admin dashboard for managing cars, bookings, and user accounts.
- MANAGER: The manager role with access to a manager dashboard for managing cars and bookings.
- USER: The regular user role with access to the user page for managing their bookings.

### Permissions

The permissions for each role are as follows:

- ADMIN:
  - View and manage cars: Add, edit, delete cars from the system.
  - View and manage bookings: View, edit, cancel bookings made by all users.
  - View and manage user accounts: View, edit, delete user accounts.
- MANAGER:
  - View and manage cars: Add, edit, delete cars from the system.
  - View and manage bookings: View, edit, cancel bookings made by all users.
- USER:
  - View cars: View the available cars for rent.
  - Reserve cars: Make bookings for cars with specified pickup and return dates.
  - View and manage own bookings: View, edit, cancel bookings made by the user.

## Dashboard Pages

The CarDetail Rental System includes dashboard pages for ADMIN and MANAGER roles:

### Admin Dashboard

The Admin Dashboard provides the following functionalities:

- Manage Cars: Add, edit, delete cars from the system.
- Manage Bookings: View, edit, cancel bookings made by all users.
- Manage User Accounts: View, edit, delete user accounts.

### Manager Dashboard

The Manager Dashboard provides the following functionalities:

- Manage Cars: Add, edit, delete cars from the system.
- Manage Bookings: View, edit, cancel bookings made by all users.

## Conclusion

The CarDetail Rental System is a comprehensive web application that allows users to rent cars, manage bookings, and make payments. With roles and permissions for ADMIN, MANAGER, and USER, as well as dashboard pages for ADMIN and MANAGER roles, the system provides secure access and efficient management of cars, bookings, and user accounts.

## Contributing

If you would like to contribute to the CarDetail Rental System project, please follow these steps:

1. Fork the repository and create a new branch.
2. Make your changes and ensure that the code is well-documented and follows best practices.
3. Test the changes thoroughly.
4. Create a pull request with a descriptive title and detailed description of the changes made.
5. Await feedback from the maintainers and address any comments or concerns raised.

## License

This project is licensed under the [MIT License](LICENSE). Feel free to use, modify, and distribute the code for personal or commercial purposes.
