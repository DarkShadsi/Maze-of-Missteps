# Maze of Missteps

In every turn lies another mistake.

## Description

Maze of Missteps is not your typical maze puzzle game. This game won’t just test your intuition
and navigation skills but also your survival instincts.
The game is composed of subsequent levels that get harder as you move on. The player is
tasked to find a certain number of key fragments scattered randomly across the map before
they can open the door that leads to the next level. But it is not as simple as it sounds. The map
has multiple confusing paths making it difficult for the player to navigate to their goals. Some of
these paths may lead them to some boosts that would help them in clearing the level faster or
traps that could potentially make them fail. Moreover, there are also enemies that are randomly
scattered across the map that will try to chase the player. Upon collision with the player, the
player will lose a percentage of their hp and then get teleported to a random location within the
map. Enemies also get harder as you reach higher levels (e.g. increased speed/damage). If the
player’s hp drops to zero, the player loses and has to restart the level.
If the player is having a hard time in clearing the level, they can activate the hint system in which
they will be guided to where each of the key fragments are located while at the same time giving
some debuffs to the enemies in order for the player to gain an upper hand.
The player successfully beats the game if they were able to clear all levels.

## Features

- Monsters
- Progressive difficulty levels
- Reward system
- Interactive UI with sound effects
- Real-time pathfinding hint systems

## Prerequisites

- Java 24 or higher
- Maven 3.6+

## How to Run

### Clone the Repository
```bash
git clone https://github.com/DarkShadsi/Maze-of-Missteps.git
cd Maze-of-Missteps
```

### Build and Run with Maven
```bash
mvn clean install
mvn exec:java -Dexec.mainClass="com.shadow.maze.MazeOfNoReturn"
```

### Run in Eclipse
1. File → Import → Maven → Existing Maven Projects
2. Select the cloned directory
3. Right-click on `MazeOfNoReturn.java` → Run As → Java Application

### Run in IntelliJ IDEA
1. File → Open → Select the project folder
2. IntelliJ will auto-detect the Maven project
3. Run `MazeOfNoReturn.java`

### Run in VS Code
1. Open the project folder
2. Install "Extension Pack for Java"
3. Run from the Run menu

## Project Structure
```
src/main/java/com/shadow/maze/
├── MazeOfNoReturn.java          # Main entry point
├── model/                       # Data models (Player, Button, etc.)
├── view/                        # UI components (Panels, Frames)
├── controller/                  # Game logic handlers
└── util/                        # Utility classes (Sound, Tools)

src/main/resources/
├── images/                      # Game images and backgrounds
├── sounds/                      # Sound effects and music
└── data/                        # Game data (questions, answers)
```

## Technologies Used

- Java 24
- Maven
- Swing (for GUI)

## Author

Shadow Game Studios
