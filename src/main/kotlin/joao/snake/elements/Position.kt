package joao.snake.elements

//Represents XY pos
data class Position(val x: Int, val y: Int){

    //Returns the position above
    fun up(): Position{
        return Position(x, y-1)
    }

    //Returns the position below
    fun down(): Position{
        return Position(x, y+1)
    }

    //Returns the position to the left
    fun left(): Position{
        return Position(x-1, y)
    }

    //Returns the position to the right
    fun right(): Position{
        return Position(x+1, y)
    }

    //Returns the position to the left of the left border
    fun resetXL(): Position{
        return Position(WIDTH - 1, y)
    }

    //Returns the position to the right of the right border
    fun resetXR(): Position{
        return Position(0, y)
    }

    //Returns the position above the upper border
    fun resetYU(): Position{
        return Position(x, 0)
    }

    //Returns the position below the lower border
    fun resetYD(): Position{
        return  Position(x, HEIGHT -1)
    }

}

