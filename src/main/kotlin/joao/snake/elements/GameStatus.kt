package joao.snake.elements

enum class GameStatus{
    START, RUNNING, OVER, RESTART;

    //Returns the next game status
    fun nextStatus(): GameStatus{
        return when(this){
            START -> RUNNING
            RUNNING -> OVER
            OVER -> RESTART
            RESTART -> RUNNING
        }
    }

}