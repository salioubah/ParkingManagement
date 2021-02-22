# Spring Boot "REST API" List of parking
***
This is a sample list of parking around user

### About Javadoc

You can launch the index.html file inside javadoc folder.

## How to Run
***
* Clone this repository
* Make sure you are using JDK 1.8 and Maven 3.x
* You can run the project by the main Class :
```
      com.salioubah.proximity_parking.ProximityParkingApplication.java
```

Once the application runs you should see something like this :

```
2021-02-22 19:46:25.839  INFO 28472 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2021-02-22 19:46:25.916  INFO 28472 --- [  restartedMain] c.s.p.ProximityParkingApplication        : Started ProximityParkingApplication in 7.831 seconds (JVM running for 8.385)
```

### Retrieve a paginated list of users
***
```
@RequestParam(name="latUser")
@RequestParam(name="lonUser")
@RequestParam(name="distance", defaultValue = "3")
GET localhost:8080/parking

Response: HTTP 200
Content: list of parking aroun users
[
    {
        "name": "Parc-Relais Brandenburg",
        "lon": -0.5443771,
        "lat": 44.8755207
    },
    {
        "name": "Bord'Eau Village",
        "lon": -0.5618781,
        "lat": 44.8561424
    },
    {
        "name": "Bastide Niel",
        "lon": -0.5586437,
        "lat": 44.8509592
    }
]
```

## ABOUT MODELS
***

#### Parking
```
private String name;
private double lat;
private double lon;

```