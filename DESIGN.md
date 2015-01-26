####Introduction
Our team is trying to implement an abstract engine that implements various types of cellular automata. This engine consists of a finite size grid, representing the various states of the world, and a set of cells, representing life. These cells are subject to various behaviors that will be implemented in their own classes or specified by the user. The engine will then update the environment based on the rules of the grid and the rules of the cells. Overall, we are trying to make a simple abstract engine deriving from the Game of Life. We intend to implement the above in a scalable manner that conforms to standard design conventions separating the game into reasonable structures using various classes. We will be using Java8 and JavaFX to do so.

####Overview

The four games are similar in the sense that they all involve a 2-D grid of cells, each with several distinct states. These cells will evolve according to a set of rules on how to update their states based on their own and their neighboring cells’ current states. The states of the cell can be represented using integer values, so that the update rules can be implemented mathematically instead of using complex logic comparisons. 

However, each game has very different rules. In Game of Life and Schelling's Model of Segregation, each cell has eight neighbors; while the other two games only consider the four neighbors in the X and Y directions. The differences in rules are summarized in the table below:

| Simulation  |# of Possible Cell States   | # of Neighbors for Each Cell  | # of Initial Parameters  | Do cells move?  |
|---|---|---|---|---|
| Schelling  |  3 | 8  |  3 | Y  |
|  Fire | 3  |  4 |  2 | N  |
| Wa-Tor  |  3 | 4  | 4  | Y  |
| Game of Life  | 2  |  8 | 1  | N  |

The four games share some aspects, but the rules differ too much that it is simply more convenient to design a update method for each game instead of designing an abstract one for all of them. We then need a class to oversee and update the simulation, a class to initialize the simulation specified in the XML file, and a class that holds all the information of the simulation. To reflect this design motivation, we have come up with four classes to implement.

First of all, the XML file will have the following format:
```
<sim>
<type>an integer that represents which simulationto run(1 for schelling, 2 for spreading fire, 3 for wa-tor, 4 for game of life)</type>
<width>Width of the simulation (in px)</width>
<height>Height of the simulation (in px)</height>
<delay>Delay for each tick</delay>
<params>
<param>create as many param nodes as needed to specify each simulation</param>
</params>
</sim>
```

Parameters for each simulation will be specified in the XML file according to the following order.
Schelling - param 1: satisfaction threshold, param 2: red-blue ratio, param 3: % of empty cells.
Wa-Tor - param 1: % of fish, param 2: % of sharks, param 3: initial energy level of sharks, param 4: # of frames before a fish can reproduce 
Spreading Fire - param 1: probability of catching fire, param 2: initial size of fire
Game of Life - param: % of live cells

Then, we will have a Main class that extends JavaFX’s Application. This will allow us to launch the entire application. It will have a start method that will call the Master class. The Master class essentially controls everything in the application. Similar to the game, it will be updating every frame, using the method start(int frameRate), which returns a KeyFrame. A a lambda expression is used to connect this to an update() method, which runs every frame. This way we are able to check whether a condition within another class has been satisfied, and act based on that.  

As stated, the Master class controls a few classes. The first class that it controls is the Initializer class. We will pass in the XML file to the constructor of this class. The Initializer then reads the XML file and transcribes the information for the simulation into its own fields. The Master class then calls the init method of the Initializer, which will return a Game object, which will have the scene of populated grids and a method that implements the rules to update the cells based on the information in the XML file.

The Game class is a superclass that has the fields Map, Scene, and all information needed to specify a simulation (name, size, delay time, etc.). The map of the cells will be a 2D array of integer numbers that represent the states of the cells. For example, in the Game of Life, 1 would be life and 0 would be death. The scene will have the populated grids based on the map. However, because each simulation has different rules of updating, we will need to create four (or however many simulations) subclasses. Within each subclass, we will have methods that update the map (states of the cells) and the scene. An instance of the subclass is then returned to the Master class.

The Master class will have a method named update, that calls the update method in the Game subclass in each frame so that the user can visualize the changes being made every frame.

The following is the basic structure of our design. The drawing of the design is also attached.

* Specific Classes:
 * Main: 
    * start (method)
 * Master: (updates frames): 
    * create instances of the Initializer class and Game class (pass in XML file)
    * start simulation (method to start animation)
    * updateFrame (method) - calls the update methods of the specific Game subclass.
 * Initializer (takes in XML file, stores data in Game object)
 * Game (superclass)
	* updateRules (method)
	* WaTor (subclass)
	* Schelling (subclass)
	* Spreading Fire (subclass)
	* Game of Life (subclass)
        
####User Interface

The user interface will be kept very simple to start with, similar to the user interface used in implementing the first project game. There will be a tool bar along-side the game, or in a separate window, where properties of the game can be changed, this will then be reflected in the game automatically, or a new game will start if necessary.
 
#### Design Details
As described in the design overview, in addition to Main, we plan to divide the project into three major components: Master, Initializer, and Game. The rationale behind having a Master class is that we will have to implement multiple simulations and each simulation involves updating the cells in each frame. Therefore, it is natural to have a class that monitors the overall operation of the simulation and controls the logic flow of the program. Furthermore, we think it would be better practice to differentiate operations on the on-screen elements and those behind the scene (algorithms). The Master class is then also responsible for updating the display.
We think that the Master class will have fields that store some statistics of the simulation that are of interest. For example, in Game of Life, the fields will store the percentage of live cells, the exact number of live cells, and how many generations have passed. The Master will also have a method called init, which completes all the preparation before running the simulation, including adding basic control buttons to the scene, such as pause/resume, load new XML, and quit. Then two methods will control pause and resume of the simulation, with the resume method also functioning as the method that starts the simulation in the beginning.
In the init method of the Master class, an instance of the Initializer class will be created, with its constructor taking the handler to the XML file as an argument, and puting whatever information it reads from the XML file into its own fields. The Initializer will have two methods, readXML and init. readXML takes the handler to the XML file as an argument, and returns a Game object with all the specifications on the simulation. The init method also returns a Game object, but it is created using the existing information in the Initializer. Therefore, the actual code for creating the Game object can be put into a separate private method.
The Game superclass has four (or however many simulations) subclasses. Within each subclass, we will implement a different UpdateCells method that update the map (states of the cells) and the scene. The Game superclass also provides setters and getters to change basic specifications of the simulation such as delay between consecutive frames. 
####Design Considerations
Is our design versatile and modular? Does it conform to open-closed principle? Is it built in such a way to ensure easy collaboration between team mats? We do not know for sure and hope the TA will help us out in this process, however, below are the design considerations we discussed at length.

We talked about having a master class, that basically controls all the active components of the engine. We also talked about having a cell class, this gives the specifics for a general cell and can be passed on through inheritance. We then discussed using a user interface class, that can also be passed through inheritance to implement specific controls relating to the game selected to be played. Then there will be the specific implementations of each case. We chose to go about it this way as it offers a general approach to solving the project and separates important components. So errors will not propagate throughout the program and the code will be maintainable and scalable.
.
The cons to our design is that it takes a lot of effort to figure out these abstractions and since we aren’t coding a specific game in general, for the overall engine, we have to keep in mind all these possibilities. This makes the start of the project harder than directly diving into making the specific games, but it should become more beneficial as time goes on.

####Team Responsibilities

We will work together to create the general architecture of the program, making the appropriate classes and important methods figuring out what each one does and returns. This will allow us to understand all the components and what will be done where to ensure no redundant code is produced and make sure everything fits in the correct order later. We are not exactly sure how to go about this, how do companies set up big endeavours? Hopefully our TA can answer this question.

We will then each tackle specific methods and classes to implement in a structured way so we can test whether they work immediately after writing them. We want to be able to check for bugs in particular cases very quickly so using the TA’s advice we will each code the classes and methods that make most sense first, implementing the bare minimum to get to the next step. For example, initially we will only code one rule for a cell, like it can move forward, and then move on to the next step, where we will code an interaction of that step.

After this we can extend the minimum set of features to include more diverse features. These may take more effort, like coding cells left, right, down, jump, etc. movement, however, they will be implemented in the correct spot, in accordance with the open-closed principle. We will divide up these tasks and choose later who does what.
