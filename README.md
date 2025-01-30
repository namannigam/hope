Hope Microservice

Welcome to Hope – a Spring Boot-based microservice that provides a REST API for processing user text input and transforming it into SQL queries using GPT4All. 🚀

🛠 Prerequisites
Before running the application, ensure you have the following installed:

Java 17+
Maven 3+
Docker (Optional, for running GPT4All via a container)
GPT4All (Installed locally)

🚀 Getting Started
1️⃣ Clone the Repository
git clone https://github.com/namannigam/hope.git
cd hope

2️⃣ Set Up GPT4All
If you haven't installed GPT4All, follow these steps:
Download GPT4All for macOS from gpt4all.io.
Install and run it:
/Applications/GPT4All.app/Contents/MacOS/gpt4all --server
By default, it runs on http://localhost:4891/v1/completions.

3️⃣ Configure the Application
Ensure you set the API URL for GPT4All in application.properties:
openai.api.url=http://localhost:4891/v1/completions

4️⃣ Build & Run the Spring Boot Application
Use Maven to build and start the application:
mvn clean install
mvn spring-boot:run
The service will be available at: http://localhost:8080/

5️⃣ Test the API
📌 Public API: Process User Text
curl -X POST http://localhost:8080/api/process-text \
-H "Content-Type: application/json" \
-d '{"input": "Find all users who have a subscription to the premium plan"}'

Response Example:
{
"sqlQuery": "SELECT * FROM users WHERE plan = 'premium';"
}