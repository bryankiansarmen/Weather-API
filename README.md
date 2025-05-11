# Weather API with Spring Boot, Redis, and Rate Limiting Filter Edit

This project is a Spring Boot web application designed to fetch weather data for a given location from the **Free Weather API**. It incorporates **Redis** for caching and a **Rate Limiting Filter** to enhance performance and prevent abuse.

This project is a solution to the [roadmap.sh Weather API project](https://roadmap.sh/projects/weather-api-wrapper-service).

---

## **Features**

- **Weather Data Fetching**: Retrieves weather information for a specified location using the Free Weather API.  
- **Caching with Redis**: Stores weather data in Redis to reduce redundant API calls and enhance performance.  
- **Rate Limiting Filter**: Ensures fair usage by restricting the number of API requests per user.  

---

## **Requirements**

- **Java**: 17+  
- **Spring Boot**: 3.x  
- **Redis**: Cloud-based or local instance  
- **Free Weather API Key**  
- **SSL/TLS**: Required for secure Redis connections  

---

## **Setup**

### **1. Clone the Repository**
```
git clone https://github.com/yourusername/yourrepository.git
cd yourrepository
```
### **2. Install Dependencies**
```
mvn install
```
### **3. Set Environment Variables**
Update the following variables in your environment:
```
API_KEY=your_api_key
spring.redis.host=redis_host
spring.redis.port=redis_port
```
### **4. Run the Application**
Navigate to the application directory and start the server:
```
cd API
mvn spring-boot:run
```
The server will run on port 8000 by default.

## Usage
Example API Request
To fetch weather data for London with optional forecast day, use the following:
```
curl "http://127.0.0.1:8000/api/v1/weather?location=london&day=3"
```

## Request Parameters:
* location (required): The name of the location (e.g., "London").
* day (optional): The day of the forecast (e.g., "3 days").

## Notes
* Ensure Redis is running and accessible.
* Use a valid API key from the Free Weather API provider.
* Adjust environment variables as needed for production environments.
