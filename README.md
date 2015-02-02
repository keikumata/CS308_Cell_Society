# cellsociety
### Rules to create xml files
Each simulation has an assigned ID. Schelling is 1, Spreading Fire is 2, Wa-tor is 3, Game of Life is 4. The simulation world is a square, and the user must specify how many cells are on each side, and the frames per seconds (generations per second) of the simulation.

Each simulation requires different amount of parameters. Detailed examples for each simulation is listed below.
### XML Examples
#### Schelling:
```
<sim>
<type>1</type> (specify the ID of the simulation)
<length>30</length> (amount of cells on each side of the simulation world)
<fps>10</fps> (generations per second)
<params>
<param>40</param> (percentage of blue cells)
<param>40</param> (percentage of red cells)
<param>70</param> (threshold for satisfaction)
</params>
</sim>
```
#### Spreading Fire:
```
<sim>
<type>2</type> (specify the ID of the simulation)
<length>100</length> (amount of cells on each side of the simulation world)
<fps>10</fps> (generations per second)
<params>
<param>1</param> (percentage of initial burning cells)
<param>50</param> (probability of catching fire)
</params>
</sim>
```
#### Wa-tor:
```
<sim>
<type>3</type> (specify the ID of the simulation)
<length>30</length> (amount of cells on each side of the simulation world)
<fps>10</fps> (generations per second)
<params>
<param>70</param> (percentage of fish cells)
<param>5</param> (percentage of shark cells)
<param>5</param> (amount of energy a shark gains after eating a fish)
<param>20</param> (amount of initial energy sharks have)
<param>50</param> (generations before a fish or shark can reproduce)
</params>
</sim>
```
#### Game of Life:
```
<sim>
<type>4</type> (specify the ID of the simulation)
<length>100</length> (amount of cells on each side of the simulation world)
<fps>10</fps> (generations per second)
<params>
<param>40</param> (percentage of "life" cells)
</params>
</sim>
```
