# Rest_API_RayIT

Using a REST API to show waypoints on a GeoMap commissioned by RayIT.

## Project Description

This project fetches data from a REST API, parses the JSON response, and displays waypoints on a map using the JXMapViewer library. The map can optionally show routes between the waypoints.

## Technologies Used

- Java
- Kotlin
- Gradle
- SLF4J for logging
- org.json for JSON parsing
- JXMapViewer for map display

## Setup Instructions

1. **Clone the repository:**
    ```sh
    git clone https://github.com/raeveira/Rest-API-RayIT
    cd Rest-API-RayIT
    ```

2. **Install Gradle:**
    - Follow the instructions on the [Gradle Installation Guide](https://gradle.org/install/).


3. **Build the project using Gradle:**
    ```sh
    ./gradlew build
    ```

4. **Run the application:**
    ```sh
    ./gradlew run
    ```

## Dependencies

The project uses the following dependencies:

- org.junit:junit-bom:5.10.0
- org.junit.jupiter:junit-jupiter
- org.slf4j:slf4j-api:1.7.32
- org.slf4j:slf4j-simple:1.7.32
- org.json:json:20231013
- org.jxmapviewer:jxmapviewer2:2.8

## Usage

The main class is `org.main.Main`. It fetches data from the OpenStreetMap Nominatim API, parses the JSON response, and displays the coordinates on a map.

## Example

To fetch and display waypoints for a specific location, modify the `uri` variable in the `Main` class:

```java
String uri = "https://nominatim.openstreetmap.org/search?format=json&q=Roermond";
```

Run the application using the instructions provided in the **Setup Instructions** section.

## Contriubers
* **Rae** - rae@raeveira.nl - [GitHub](https://github.com/raeveira)

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
```