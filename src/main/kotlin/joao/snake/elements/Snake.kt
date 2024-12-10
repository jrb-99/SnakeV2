/*package joao.snake.elements

//Snake constants
const val UP = 0
const val DOWN = 1
const val LEFT = 2
const val RIGHT = 3
/*
const val APPLE = "snake|0,192,64,64"
const val BRICKS = "bricks.png"
const val S_H_UP = "snake|192,0,64,64"
const val S_H_DOWN = "snake|256,64,64,64"
const val S_H_LEFT = "snake|192,64,64,64"
const val S_H_RIGHT = "snake|256,0,64,64"
const val S_T_UP = "snake|192,128,64,64"
const val S_T_DOWN = "snake|256,192,64,64"
const val S_T_LEFT = "snake|192,192,64,64"
const val S_T_RIGHT = "snake|256,128,64,64"
*/

//Represents the snake object
data class Snake(val pos: Position = Position(WIDTH / 2, HEIGHT / 2), val dir: Int = RIGHT) {

    //Move the snake up
    fun moveUp(): Snake {
        if (this.dir != DOWN) {
            if (upBorder()) {
                return Snake(pos.resetYD(), UP)
            }
            return Snake(pos.up(), UP)
        }
        return this
    }

    //Move the snake down
    fun moveDown(): Snake {
        if (this.dir != UP) {
            if (downBorder()) {
                return Snake(pos.resetYU(), DOWN)
            }
            return Snake(pos.down(), DOWN)
        }
        return this
    }

    //Move the snake left
    fun moveLeft(): Snake {
        if (dir != RIGHT) {
            if (leftBorder()) {
                return Snake(pos.resetXL(), LEFT)
            }
            return Snake(pos.left(), LEFT)
        }
        return this
    }

    //Move the snake right
    fun moveRight(): Snake {
        if (dir != LEFT) {
            if (rightBorder()) {
                return Snake(pos.resetXR(), RIGHT)
            }
            return Snake(pos.right(), RIGHT)
        }
        return this
    }

    //Returns the next position of the snake
    fun nextPos(): Position {
        return when (dir) {
            UP -> if (upBorder()) pos.resetYD() else pos.up()
            DOWN -> if (downBorder()) pos.resetYU() else pos.down()
            LEFT -> if (leftBorder()) pos.resetXL() else pos.left()
            RIGHT -> if (rightBorder()) pos.resetXR() else pos.right()
            else -> pos
        }
    }


    //Returns the position left to the snake
    fun leftPos(): Position {
        return when (dir) {
            UP -> if (leftBorder()) pos.resetXL() else pos.left()//OK
            DOWN -> if (rightBorder()) pos.resetXR() else pos.left()//NOK
            LEFT -> if (downBorder()) pos.resetYU() else pos.down()//NOK
            RIGHT -> if (upBorder()) pos.resetYD() else pos.up()//OK
            else -> pos
        }
    }

    fun rightPos(): Position {
        return when (dir) {
            UP -> if (rightBorder()) pos.resetXR() else pos.right()//OK
            DOWN -> if (leftBorder()) pos.resetXL() else pos.right()//NOK
            LEFT -> if (upBorder()) pos.resetYD() else pos.up()//NOK
            RIGHT -> if (downBorder()) pos.resetYU() else pos.down()//OK
            else -> pos
        }
    }

    //Returns true if the snake is in the left border
    fun leftBorder(): Boolean {
        return pos.x <= 0
    }

    //Returns true if the snake is in the right border
    fun rightBorder(): Boolean {
        return pos.x >= WIDTH - 1
    }

    //Returns true if the snake is in the up border
    fun upBorder(): Boolean {
        return pos.y <= 0
    }

    //Returns true if the snake is in the down border
    fun downBorder(): Boolean {
        return pos.y >= HEIGHT - 1
    }

    //Returns the image of the snake head based on the direction
    fun snakeImgH(d: Int): String {
        when (d) {
            UP -> return S_H_UP
            DOWN -> return S_H_DOWN
            LEFT -> return S_H_LEFT
            RIGHT -> return S_H_RIGHT
        }
        return S_H_RIGHT
    }

    //Returns the image of the snake tail based on the direction
    fun snakeImgT(d: Int): String {
        when (d) {
            UP -> return S_T_UP
            DOWN -> return S_T_DOWN
            LEFT -> return S_T_LEFT
            RIGHT -> return S_T_RIGHT
        }
        return S_T_RIGHT
    }


}
*/