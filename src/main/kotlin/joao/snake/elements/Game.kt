/*package joao.snake.elements

/*Game constants
const val WIDTH = 20
const val HEIGHT = 16
const val CELL_SIZE = 32
const val REFRESH_RATE = 250
const val WALL_REFRESH_RATE = 5000
*/

//Represents the game status
data class Game(val snake: Snake = Snake(Position(WIDTH / 2, HEIGHT / 2), RIGHT), val wall: List<Position> = mutableListOf<Position>()) {

    //Returns the next game status, checking if the snake has hit a wall
    fun advance(): Game {
        for(w in wall){
            if(snake.nextPos() == w){
                return Game(snake, wall)
            }
        }
        val snk = moveSnake()
        return Game(snk, wall)
    }

    //Returns true if the snake can move left
    fun movableLeft(): Boolean{
        for(w in wall){
            if(snake.leftPos() == w){
                return false
            }
        }
        return true
    }

    //Returns true if the snake can move right
    fun movableRight(): Boolean{
        for(w in wall){
            if(snake.rightPos() == w){
                return false
            }
        }
        return true
    }

    //Returns the new snake in a new position
    fun moveSnake(): Snake{

        val snk = when (snake.dir) {
            UP -> snake.moveUp()
            DOWN -> snake.moveDown()
            LEFT -> snake.moveLeft()
            RIGHT -> snake.moveRight()
            else -> snake.moveRight()
        }
        return snk

    }

    //Generates a new wall
    fun genWall(list: List<Position>): List<Position> {

        var newList = list
        val new_w = Position((0..WIDTH).random(), (0..HEIGHT).random())
        newList += new_w

        return newList

    }

}
*/