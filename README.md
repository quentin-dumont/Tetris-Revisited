# Tetris-Revisited
A game developed in a 4 members team, which consists to assemble different shapes to minimize the total space taken up on a grid. We have used different Design Patterns, which are MVC, Strategy and State. This enabled us to produce a code more maintainable and reusable. Third year at university project.

# Game Manual

Launch with :
$ java -jar dist/jeuAssemblage-1.0.jar 

Shapes can be moved with drag and drop.
Use the arrow keys <- -> to rotate a shape after clicking on it (Make sure it's possible to rotate it, otherwise the shape will not move)

When you think you have found the best arrangement, click on 'Terminer' and look at the solution proposed by the robot.
Sometimes, you might beat it, it doesn't always find the best solution.

I encourage you to test the robot with a large grid and a huge number of shapes, really satisfying. 
For example, 300 shapes on a 80 per 80 grid. The resolution may take a few seconds.

We also implemented a backup system, if you want to save a game configuration.
