package joao.snake.elements

enum class Direction{
    UP, DOWN, LEFT, RIGHT;


    fun turnLeft(): Direction{
        return when (this){
            UP -> LEFT
            DOWN -> RIGHT
            LEFT -> DOWN
            RIGHT -> UP
        }
    }

    fun turnRight(): Direction{
        return when (this){
            UP -> RIGHT
            DOWN -> LEFT
            LEFT -> UP
            RIGHT -> DOWN
        }
    }

    fun opposite(): Direction{
        return when (this){
            UP -> DOWN
            DOWN -> UP
            LEFT -> RIGHT
            RIGHT -> LEFT
        }
    }

}