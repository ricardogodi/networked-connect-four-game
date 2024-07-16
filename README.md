# Networked Connect Four Game

## Description

The Networked Connect Four Game is an event-driven application designed using a server-client architecture. This project features separate GUIs for the server and the client, enhancing user interaction and gameplay dynamics. The game logic is meticulously developed and thoroughly tested to simulate a realistic two-player matchup. It is implemented using JavaFX and managed as a Maven project.

## Prerequisites

Before you run this application, you will need:

- Java Development Kit (JDK) 17 or higher
- Maven 3.6 or higher
- JavaFX SDK 22.0.1

## Setup Instructions

### 1. Install JDK

Ensure that Java Development Kit (JDK) 17 or higher is installed on your machine. You can download the latest version from [Oracle’s official website](https://www.oracle.com/java/technologies/javase-downloads.html).

### 2. Install Maven

Maven is required to manage dependencies and build configurations. Download and install it from [Apache Maven’s official site](https://maven.apache.org/download.cgi).

### 3. Download JavaFX

Download the JavaFX SDK from [OpenJFX](https://openjfx.io). Ensure you download version 22.0.1 or compatible with your JDK version.

### 4. Configure JavaFX

Extract the downloaded JavaFX SDK to a known location on your machine. You will need to reference this path when setting up your project to run.

### 5. Clone the Repository

Clone this repository to your local machine using Git:
```bash
  git clone https://github.com/ricardogodi/networked-connect-four-game.git
  cd networked-connect-four-game
```
### 6. Run the Server

First, start the server. Navigate to the server directory and use the following command, ensuring to replace /path/to/javafx-sdk-22.0.1/lib with the actual path to your JavaFX lib directory:
```bash
 java --module-path /path/to/javafx-sdk-22.0.1/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics -jar target/serverProgramProjectThree-0.0.1-SNAPSHOT.jar
```
The server GUI allows you to listen on a default port 5555.

### 7. Run the Client

Next, start the client application. Navigate to the client directory and use the following command:
```bash
 java --module-path /path/to/javafx-sdk-22.0.1/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics -jar target/clientProgramProjectThree-0.0.1-SNAPSHOT.jar
```
On the client GUI, specify the server’s IP address and port number to connect. Default buttons are provided for quick access.

## Included Files

The repository includes several key files and documents that support and document the development and functionality of the Networked Connect Four Game:

- **UML Diagram**: Visual representation of the project's structure and relationships.
- **Wireframe**: Visual guide that represents the skeletal framework of the user interfaces.
- **Networking Algorithm Flow Diagram**: Outlines the processes involved in the server-client communication.
- **Checking for a Winner Algorithm**: Details the logic used to determine the game's winner.
- **Gameplay Example (1, 2, 3)**: Sample gameplay scenarios to illustrate typical game interactions and outcomes.

## Features

- Detailed game logic for a competitive and engaging two-player experience.
- Dynamic imagery and user interfaces to enhance visual engagement.
- Comprehensive UML planning and robust architecture for reliable network communication.
- Rigorous testing to ensure consistent performance and gameplay quality.





























