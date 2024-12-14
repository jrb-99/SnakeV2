package joao.snake.elements

enum class Direction{
    UP, DOWN, LEFT, RIGHT;

    //Returns the opposite direction
    fun opposite(): Direction{
        return when (this){
            UP -> DOWN
            DOWN -> UP
            LEFT -> RIGHT
            RIGHT -> LEFT
        }
    }

}