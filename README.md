# RentEco
RentEco is a client-server app built with modern features and a strong focus on security.

# Client Side
Written in Kotlin Compose with Android Studio.
#Server Side
Written in Java Spring with a MySQL database.
# Main Features of RentEco
- Login, rent a car, and view history.
- Register with a driver's license photo, which identifies personal data using Google Cloud Vision API.
- Open/close car based on SMS messages with an external Arduino module.
- Vehicle location tracking.
# Security
- Every request to the server is done with HTTPS.
- SMS encrypted with OTP (One-Time Password protocol).
- At login, a new JWT (Java Web Token) is created for every user with an expiration time.
- The app runs on a Google Virtual Machine with restrictions on IPs and available ports.
