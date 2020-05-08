# [ITP4501] Android-MoraGame Instruction
## 1	Aims and Objectives
* To gain experience in mobile application UI and program design.
* To gain practical skill of Android application development.
* To understand the constraints and limitation of mobile application and the ways to overcome them.
* To obtain knowledge on connecting the mobile device to the internet services and building a multi-tier distributed system.

## 2	Introduction
In this assignment, you are required to develop an Android Application to play a "15,20" game by selecting a player on a server. You can use the following website to get the detail of this game:
https://zh.wikipedia.org/wiki/%E6%95%B8%E5%AD%97%E6%8B%B3
https://www.youtube.com/watch?v=IrYzM-NCIEs

## 3	Functional Requirements
Listed below are the basic requirements of your application. You need to refer to the Local Database section for the database schema.
1.	An activity performs a player’s personal information registration screen which can store player’s name, date of birth, phone number and email by using shared preferences in your phone. You should let player to update his personal information in your app.
2.	An activity which contains a button “Start”. When a player touches this button, your app will find a player from a server and then show the information of this player on the screen. 
And then your game will show (0,0),(0,5),(5,0),(5,5) to represent your two hands to let you to choose. Next screen will show 0,5,10,15,20 on the screen for you to guess the number. You should try to make your choice by touching the image on the screen.
Once you make your guess and hands, your app will get the hands and guess (useless at this round) of your opponent from the server. On the screen, your app should show your name and the name of your opponent, the hands of yours and corresponding opponent. The game will show who win the game if the number on the screen is same as your guess. Otherwise, the game will go on next round but this time you only select your hands, i.e. (0,0),(0,5),(5,0),(5,5) and then your app will get the hands and guess of your opponent from the server again and go on. The game will go on until the number on the screen will be same as your guess or opponent's guess.
If the game gets the winner, you can touch a Continue button to play again or Quit button to leave it.
To simplify your work, you are always the first one to start the guess.
3.	An activity which uses a bar chart to show how many games you win and how many games you lose.
4.	A database GamesLog which contains a table to store the game history for yours which store the date and time, the name of your opponents and the result of the game (Win or Lost).
An activity for GamesLog properly show the data stored in the local database. Your list should show the result of a game.

## 4	Local Database
## 5	Opponents JSON Server
## 6	Additional Constraints
## 7	Marking Guidelines
