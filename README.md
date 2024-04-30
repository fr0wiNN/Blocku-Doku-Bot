
# Blocku Doku Bot

## Introduction
Blocku Doku Bot is a sophisticated agent designed to play the BlockuDoku game by analyzing screenshots and determining the best set of moves using heuristics developed through genetic algorithm training. This bot utilizes a combination of board recognition algorithms and a Python server-client setup to process game states and compute optimal strategies.

## Features
- **Board Recognition Algorithm**: Detects occupied cells on the game board.
- **Block Detection**: Identifies blocks based on their indices from the game screenshot.
- **Optimal Move Calculation**: Uses a backtracking algorithm to compute the best possible set of moves.
- **Heuristic Training**: Currently leveraging features and trained constants from another project, with plans to develop proprietary heuristics.

## Installation
To use the Blocku Doku Bot, follow these steps:
1. Ensure you have Java installed on your machine.
2. Download the `BlockuDokuBot.jar` file from the repository.
3. Place your screenshot (1080x2400 PNG) in the `Server/Server_Img` directory.

## Usage
Run the bot with the following command:
```bash
java .\BlockuDokuBot.jar .\Server\Server_Img\{test image}.png
```
Replace `{img file path}` with the path to your game screenshot.

You can run this command with preset images
Here is an example of such a use:
```bash
java -jar .\Blocku-Doku-Bot.jar .\Server\Server_Img\20240424235322.png
```

## Dependencies
For the Python server-client:
- Flask
- os
- subprocess
- datetime

You can install the necessary Python libraries using:
```bash
pip install Flask
```

## Contributing
Contributions are welcome! Please feel free to fork the repository, make changes, and submit a pull request. If you plan to propose significant changes, please open an issue first to discuss your ideas.

## Acknowledgments
- **[GaBlockSudoku](https://github.com/hashempour/GaBlockSudoku)**: This project was instrumental in providing the initial set of features and training models for my genetic algorithm.

## License
This project is released under the [MIT License](https://opensource.org/licenses/MIT).
