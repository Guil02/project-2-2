# Multi-Agent Surveillance System

This repository contains a multi-agent system designed for guarding and intruding an unknown area. This research delves into the application of various algorithms to simulate the behaviour of intelligent intruders and guards. The primary algorithms implemented include A*, Genetic Neural Network, Artificial Fish Swarm, Brick and Mortar, and Ants Patrol.

## Essay:
For a detailed understanding of the research and methodologies employed in this project, you can refer to the [essay](https://drive.google.com/file/d/1oQJD-LeZYbpJkblvc1ZNCZFuouJtjkN9/view?usp=share_link).

## Team Members
Guillaume Bams, Rosamelia Carioni Porras, Sam Glassman, Xander Karnebeek, Roman Marjusins, Elena Perego, Mischa Rauch

## Prerequisites

- Java JDK 11 or higher
- Gradle 6.8 or higher

## Installation and how to run

1. Clone the repository to your local machine.
2. Navigate to the project root folder `project-2-2` using the command line.
3. If you have Gradle installed, run the project using `gradle run`.
4. If you don't have gradle installed, try running ``gradlew run`` ( or ```./gradlew run```), or you can [install Gradle here](https://gradle.org/install/).
5. If step 4. does not work open the project with your favorite IDE (IntelliJ, VSCode,...).
6. Make sure when you open the root folder (project-2-2) to trust this project.
7. Run the inbuild ```run``` command of gradles applications via your IDE.


### Setting Up a Game

1. **Choose a Map**: Browse through the available maps or upload your custom scenario.
2. **Select Algorithms**: 
   - Choose an algorithm for the Guards.
   - Choose an algorithm for the Intruders.
3. **Game Modes**: Set the desired game mode in the scenario file:
   - `0` for Exploration
   - `1` for Single Intruder Caught
   - `2` for All Intruders Caught
   - `3` for One Intruder at Target
   - `4` for All Intruders at Target
4. **Play**: Monitor the game's progress at the bottom of the interface. You can follow different agents by clicking on their icons. Use zoom controls to view the entire map.

