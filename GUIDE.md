### How to interact with the application?
The application by default runs on port 8443 and exposes following endpoints

1. `/authenticate` As the name suggests, this endpoint is used to authenticate a user based on
    username and password. This is a HTTP.POST requests and expects two parameters in the request body, 
    a. `username`
    b. `password`
    Based on the provided credentials, the application authenticates the user, and upon success returns a
    Jwt token in response body. This token is essential to access all other resources within the application, without it
    no requests would be served.
    
 2. `/tickets` following the REST guidelines, returns a list of Tickets. These ticket records are sorted in descending
    order by priority. It is essential that the Jwt token returned by the authentication request is put in an `Authorization`
    header in the following format `Bearer {jwt token}`. If the token is invalid/expired, the request will not be served.