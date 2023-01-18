# StrategoMultiplayer
## Start instructions
To start the game a computer must run the lobby class as a java application. 
Then the players of the game can join the lobby by starting the maven application with following goals clean javafx:run. 
To connect to the lobby each player must then input the ip of the lobby which it has printed in it's console.
From there a player can join one of 10 rooms by writing the number of the room they want to join. When two players have joined the same room the game can begin.

From here each player is prompted for a string which represents the start location of their pieces and is 40 characters long this string can simply be skipped by pressing enter in console and a default configuration will be used instead. From here the game simply functions as Stratego and messages in the console will give information on the ongoing game.

## Potential issues
If maven and Jspace is not installed the program will not be able to run. \\
