# chatGPT-chatbot-spring-boot-react
Wrapper project for ChatGPT Chatbot application

## Prerequisite

1. Java 17+
2. Maven
3. NPM
4. Before running the app on local, please visit the following URL and generate a new OpenAI API Key "https://platform.openai.com/account/api-keys" and add the key to the file "\back-end\src\main\resources\application-local.yml"

![img.png](img.png)

### Run the app
There are two scripts in the repository, you'll have to run based on the OS you have:

For Windows OS: windows.bat
For Linux-based OS: linux.sh

The final JAR file will be created inside back-end/target folder with the name demo-0.0.1-SNAPSHOT.jar

Switch to the back-end/target folder and run the JAR file with the command:

java -jar demo-0.0.1-SNAPSHOT.jar

### How to access the backend app via Postman?
Use Postman, you can launch the app by navigating to these two endpoints:
* localhost:8080/api/v1/chat