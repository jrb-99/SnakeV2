package joao.snake.elements

//Game constants (Can be an enum)
const val WIDTH = 20
const val HEIGHT = 16
const val SCOREBOARD_HEIGHT = 2
const val CELL_SIZE = 32
const val REFRESH_RATE = 250//3000
const val WALL_REFRESH_RATE = 5000
const val GROW_RATE = 5
const val PPA = 1 //PPA: Points per Apple

data class GameV2(val snake: SnakeV2 = SnakeV2(), val wall: List<Position> = generateCornerPositions(), val apple: Position? = null, val score: Int = 0){

    //Returns the next game status, checking if the snake has hit a wall
    fun advance(): GameV2 {

        //Check collision with wall
        if(wall.contains(snake.nextPos(snake.body[0], snake.dir))){
            return GameV2(SnakeV2(snake.body, snake.dir, true, snake.toGrow), wall, apple, score)
        }

        //Check collision with snake body
        if(snake.body.subList(1, snake.body.size).contains(snake.nextPos(snake.body[0], snake.dir))){
            return GameV2(SnakeV2(snake.body, snake.dir, true, snake.toGrow), wall, apple, score)
        }

        //Return growing snake or current snake
        val snk = snakeType().move(snake.dir)

        //Return game status if snake eats an apple
        if(snake.body[0] == apple){
            return GameV2(snk, wall, null, score + PPA)
        }

        //Return game status if snake doesn't eat any apple
        return GameV2(snk, wall, genApple(), score)
    }


    //Returns snake type (has eaten apple or not)
    fun snakeType(): SnakeV2{

        if(snake.body[0] == apple){
            return SnakeV2(snake.body, snake.dir, snake.stopped, snake.toGrow + GROW_RATE)
        }
        return snake

    }

    //Returns true if the snake can change direction
    fun isDirectionChangeAllowed(newDirection: Direction): Boolean {
        val nextPosition = snake.nextPos(snake.body[0], newDirection)
        return !snake.body.contains(nextPosition) && !wall.contains(nextPosition)
    }


    //Returns the end game string based on the score
    fun endGameString(): String{
        if(gameOver() && score >= 11) {
            return "You won"
        } else if(gameOver() && score < 11){
            return "You lost"
        }
        return ""
    }

    //Returns true if the game is over (snake stopped and can't move)
    fun gameOver(): Boolean {
        return snake.stopped && cantMove()
    }

    //Returns true if the snake can't move
    fun cantMove(): Boolean {
        val occupied = wall + snake.body
        for (p in snake.surroundings()) {
            if (!occupied.contains(p)) {
                return false
            }
        }
        return true
    }

    //Generates a new wall
    fun genWall(list: List<Position>): List<Position> {

        val newList = list.toMutableList()
        var new_w = Position((0..<WIDTH).random(), (0..<HEIGHT).random())

        while (wall.contains(new_w) || snake.body.contains(new_w) || apple == new_w){

            new_w = Position((0..<WIDTH).random(), (0..<HEIGHT).random())

        }

        newList += new_w

        return newList

    }

    //Generates a new apple
    fun genApple(): Position {

        if(apple == null){

            var p = Position((0..<WIDTH).random(), (0..<HEIGHT).random())

            while (wall.contains(p) || snake.body.contains(p)) {
                p = Position((0..<WIDTH).random(), (0..<HEIGHT).random())
            }

            return p

        }
        return apple
    }

}//end of class GameV2

//Utility function to generate corners instead adding them manually
fun generateCornerPositions(): List<Position> {
    return listOf(
        // Top-left corner
        Position(0, 0), Position(1, 0), Position(0, 1),
        // Top-right corner
        Position(19, 0), Position(18, 0), Position(19, 1),
        // Bottom-left corner
        Position(0, 15), Position(1, 15), Position(0, 14),
        // Bottom-right corner
        Position(19, 15), Position(18, 15), Position(19, 14)
    )
}