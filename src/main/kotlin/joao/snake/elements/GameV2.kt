package joao.snake.elements

//Game constants
const val WIDTH = 20
const val HEIGHT = 16
const val CELL_SIZE = 32
const val REFRESH_RATE = 250//3000
const val WALL_REFRESH_RATE = 5000
//SnakeV2(mutableListOf<Position>(Position(WIDTH/2, HEIGHT/2), Position((WIDTH/2)-1, HEIGHT/2), Position((WIDTH/2)-2, HEIGHT/2), Position((WIDTH/2)-3, HEIGHT/2)), Direction.RIGHT, false, 0)
data class GameV2(val snake: SnakeV2 = SnakeV2(), val wall: List<Position> = generateCornerPositions(), val apple: Position? = Position(4, 4), val score: Int = 0){

    //Returns the next game status, checking if the snake has hit a wall
    fun advance(): GameV2 {
        for(w in wall){
            if(snake.nextPos(snake.body[0]) == w){
                return GameV2(SnakeV2(snake.body, snake.dir, true, snake.toGrow), wall)
            }
        }

        for(p in snake.body.subList(1, snake.body.size)){
            //Avoid snake head to turn against its body
            if(snake.nextPos(snake.body[0]) == snake.body[1]){
                println("OPPOSITE")
                return GameV2(SnakeV2(snake.body, snake.dir.opposite(), false, snake.toGrow), wall)
            }
            if(snake.nextPos(snake.body[0]) == p){
                return GameV2(SnakeV2(snake.body, snake.dir, true, snake.toGrow), wall)
            }

        }
        if(snake.body[0] == apple){
            return GameV2(SnakeV2(snake.body, snake.dir, snake.stopped, snake.toGrow + 5), wall, genApple(), score + 1)
        }
        val snk = snake.move(snake.dir)
        return GameV2(snk, wall, apple, score)
    }


    //Generates a new wall
    fun genWall(list: List<Position>): List<Position> {

        var newList = list
        val new_w = Position((0..WIDTH).random(), (0..HEIGHT).random())
        newList += new_w

        return newList

    }

    //Generates a new apple
    fun genApple(): Position? {
        val p = Position((0..WIDTH).random(), (0..HEIGHT).random())
        if (wall.contains(p) || snake.body.contains(p)) {
            return genApple()
        }
        return Position((0..WIDTH).random(), (0..HEIGHT).random())
    }




}//end of class GameV2


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