## Group 7: Guillaume Bams, Rosamelia Carioni Porras, Sam Glassman, Xander Karnebeek, Roman Marjusins, Elena Perego, Mischa Rauch

# Multi-agent system for guarding and intruding an unknown area

This project implements A* and Genetic Neural Network algorithms to reproduce intelligent intruders and Artificial Fish Swarm, Brick and Mortar, and Ants Patrol algorithms to imitate the behavior of intelligent guards.

### How to run:

1. Unzip the source code project folder.
2. Navigate to the project root folder ``project-2-2``on the command line.
3. If you have gradle installed, run the project using ```gradle run``` ( or ```./gradle run```) if you don't have
   gradle install it.
4. If you don't have gradle installed, try running ``gradlew run`` ( or ```./gradlew run```).
5. If step 4. does not work open the project with your favorite IDE (IntelliJ, VSCode,...).
6. Make sure when you open the root folder (project-2-2) to trust this project.
7. Run the inbuild ```run``` command of gradles applications via your IDE.

### Setting up a game:

1. Choose a map - you can either browse through an offered selection or upload your own scenario.
2. Algorithm - choose an algorithm for the setup. 
   1. Choose an algorithm for the Guards
   2. Choose an algorithm for the Intruders
3. GameMode - choose one of the following Game Modes and set them in the scenario file:
   1. Set variable GameMode to 0 for ```Exploration```
   2. Set variable GameMode to 1 for ```Single Intruder Caught```
   3. Set variable GameMode to 2 for ```All Intruders Caught```
   4. Set variable GameMode to 3 for ```One Intuder at Target```
   5. Set variable GameMode to 4 for ```All Intruder at Target```
4. Enjoy - in the bottom you can find the elapsed time and the current total coverage. "Follow" different agents by clicking on their associated icon. Make sure to zoom in/out to see the whole map.
