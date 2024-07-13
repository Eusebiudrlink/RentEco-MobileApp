# RentEco is a client-server app build with modern features and a strong focus on security.
- Client side: written in Kolin Compose with Android Studio.
- Server side: Writen in Java Spring and Database in MySQL.
# Main features of the RentEco:
-   -  login, rent a car and view history.
    - Register with a driver license photo which identify personal data with Google Cloud Vision Api
    - Open/Close car based on sms message with an extern Arduino module
    - Vehicle location traking 
 
# Security :
  - Every request to server done with HTTPS 
  - Sms encripted with OTP(One-Time-Password protocol)
  - At login new JWT(Java-Web-Token) created for every user with an expiration time.
  - The app is working on a Google Virtual Machine which has restrictions on IPs and available ports
