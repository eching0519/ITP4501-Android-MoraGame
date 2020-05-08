# [ITP4501] Android-MoraGame Instruction
## 1	Aims and Objectives
* To gain experience in mobile application UI and program design.
* To gain practical skill of Android application development.
* To understand the constraints and limitation of mobile application and the ways to overcome them.
* To obtain knowledge on connecting the mobile device to the internet services and building a multi-tier distributed system.

## 2	Introduction
In this assignment, you are required to develop an Android Application to play a "15,20" game by selecting a player on a server. You can use the following website to get the detail of this game:<br />
https://zh.wikipedia.org/wiki/%E6%95%B8%E5%AD%97%E6%8B%B3<br />
https://www.youtube.com/watch?v=IrYzM-NCIEs<br />
<br />
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
<br />
Note: You are encouraged to design and implement extra features. 15% of the total mark will be allocated on such additional functions. Refer to section 7 Marking Guidelines for more details.<br />
<br />
## 4	Local Database
The database scheme described here is an extremely simple one. Many fields are intended not to be included in order to reduce the complexity of this assignment. You are free to add columns and tables to the database to fit for your own needs. <br />
GamesLog (gameDate, gameTime, opponentName, winOrLost)<br />
<br />
## 5	Opponents JSON Server
You can find your opponent from the server by using following url:<br />
https://4qm49vppc3.execute-api.us-east-1.amazonaws.com/Prod/itp4501_api/opponent/0<br />
 and the data returned is in JSON format.<br />
The sample JSON string returned is shown below:<br />
`{"id": 2, "name": "May", "country": "UK"}`<br />
Be careful the last digit 0 on the url is going to find an online opponent from the server. <br />
The attribute "id" is the userid of corresponding opponent.<br />
You can get the choice and hands from that user (here is May with id 2) by using following url:<br />
https://4qm49vppc3.execute-api.us-east-1.amazonaws.com/Prod/itp4501_api/opponent/2<br />
The last digit 2 is the id of May.<br />
The sample JSON string returned is shown below:<br />
`{"name": "May", "left": 5, "right": 5, "guess": 15}`<br />
<br />
For the turn which the number is not guessed by opponent, you can ignore the value in the attribute "guess".<br />
<br />
## 6	Additional Constraints
* The UI of the mobile application must be produced with Android widgets such as TextView, CheckBox, and Spinner etc.  Web-based UI is NOT allowed.
* The statistical charts must be produced using Android built-in graphics API such as drawRect() and drawText(). Using any other external drawing packages or libraries is NOT allowed. 
<br />
## 7	Marking Guidelines
You project will be assessed according to the items below. 
* Database initialisation
* Level of completion
* Correctness
* UI design
* Program design and implementation
* Program style and comments
<br />
15% of marks will be allocated to extra features not described in section 3. Each student can develop at most 3 additional functions such as animation effect or sound effect on the Android device or any other relevant and useful functions.<br />
40% of total marks will be deducted if demonstration is not done.<br />
