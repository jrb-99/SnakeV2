package joao.snake.elements

//Snake constants
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
const val S_B_H = "snake|64,0,64,64"
const val S_B_V = "snake|128,64,64,64"
const val S_B_C1 = "snake|128,0,64,64"
const val S_B_C2 = "snake|0,0,64,64"
const val S_B_C3 = "snake|0,64,64,64"
const val S_B_C4 = "snake|128,128,64,64"

data class SnakeV2(val body: List<Position> = mutableListOf<Position>(Position(WIDTH / 2, HEIGHT / 2)), val dir: Direction = Direction.RIGHT, val stopped: Boolean = false, val toGrow: Int = 4) {

    //Returns the new snake in a new position
    fun move(d: Direction): SnakeV2 {
        val newBody = mutableListOf<Position>()
        // for (i in 0 until body.size)
        if (toGrow > 0) {
            for (i in 0 until body.size + 1) {
                if (i == 0) {
                    newBody.add(nextPos(body[i]))
                } else {
                    newBody.add(body[i - 1])
                }
            }
            return SnakeV2(newBody, d, false, toGrow - 1)
        }

        for (i in 0 until body.size) {
            if (i == 0) {
                newBody.add(nextPos(body[i]))
            } else {
                newBody.add(body[i - 1])
            }
        }
        return SnakeV2(newBody, d, false, toGrow)
    }

    fun grownList(): List<Position> {
        val newBody = mutableListOf<Position>()
        if (body.size == 1) {
            newBody.add(body[0])
            newBody.add(prevPos(body[0]))
            return newBody

        }
        return newBody + prevPos(body[0]) + body.subList(2, body.lastIndexOf(body.last()))

    }

    //Returns the next position of the snake
    fun nextPos(p: Position): Position {
        return when (dir) {
            Direction.UP -> if (upBorder(p)) p.resetYD() else p.up()
            Direction.DOWN -> if (downBorder(p)) p.resetYU() else p.down()
            Direction.LEFT -> if (leftBorder(p)) p.resetXL() else p.left()
            Direction.RIGHT -> if (rightBorder(p)) p.resetXR() else p.right()
        }
    }

    //Returns the previous position of a part
    fun prevPos(p: Position): Position {
        return when (dir) {
            Direction.UP -> if (downBorder(p)) p.resetYU() else p.down()
            Direction.DOWN -> if (upBorder(p)) p.resetYD() else p.up()
            Direction.LEFT -> if (rightBorder(p)) p.resetXR() else p.right()
            Direction.RIGHT -> if (leftBorder(p)) p.resetXL() else p.left()
        }
    }

    //Returns true if the snake is in the left border
    fun leftBorder(pos: Position): Boolean {
        return pos.x <= 0
    }

    //Returns true if the snake is in the right border
    fun rightBorder(pos: Position): Boolean {
        return pos.x >= WIDTH - 1
    }

    //Returns true if the snake is in the up border
    fun upBorder(pos: Position): Boolean {
        return pos.y <= 0
    }

    //Returns true if the snake is in the down border
    fun downBorder(pos: Position): Boolean {
        return pos.y >= HEIGHT - 1
    }

    fun anyBorder(pos: Position): Boolean {
        return upBorder(pos) || downBorder(pos) || leftBorder(pos) || rightBorder(pos)
    }

    fun tail(): Position {
        return body[body.size - 1]
    }

    fun afterTail(): Position {
        return body[body.size - 2]
    }

    //Returns the image of the snake head based on the direction
    fun snakeImgH(d: Direction): String {
        when (d) {
            Direction.UP -> return S_H_UP
            Direction.DOWN -> return S_H_DOWN
            Direction.LEFT -> return S_H_LEFT
            Direction.RIGHT -> return S_H_RIGHT
        }
    }

    //Returns the image of the snake tail based on the direction
    fun snakeImgT(d: Direction): String {
        when (d) {
            Direction.UP -> return S_T_UP
            Direction.DOWN -> return S_T_DOWN
            Direction.LEFT -> return S_T_LEFT
            Direction.RIGHT -> return S_T_RIGHT
        }
    }

    //Returns the image of the snake body based on the direction
    fun snakeImgB(d: Direction): String {
        when (d) {
            Direction.UP -> return S_B_V
            Direction.DOWN -> return S_B_V
            Direction.LEFT -> return S_B_H
            Direction.RIGHT -> return S_B_H
        }
    }

    fun snakeImgB(list: List<Position>, index: Int): String {

        val p1 = list[index-1] //front position
        val p2 = list[index] //middle position
        val p3 = list[index+1] //back position

            if (p1.x == p2.x && p2.x == p3.x) {
                //Horizontal
                return S_B_V
            }
            if (p1.y == p2.y && p2.y == p3.y) {
                //Vertical
                return S_B_H
            }
            if (p1.x == p2.x && p2.x < p3.x && p1.y < p3.y){
                //Left to up
                println("Condition 1")
                return S_B_C3
            }
            if (p1.x == p2.x && p2.x > p3.x && p1.y < p3.y){
                //Right to up
                println("Condition 2")
                return S_B_C4
            }
            if (p1.x < p2.x && p2.x == p3.x && p2.y < p3.y){
                //Up to left
                println("Condition 3")
                return S_B_C1
            }
            if (p1.x > p2.x && p2.x == p3.x && p2.y < p3.y){
                //Up to right
                println("Condition 4")
                return S_B_C2
            }
            if(p1.x == p2.x && p2.y == p3.y && p2.x < p3.x){
                //Left to down
                println("Condition 5")
                return S_B_C2
            }
            if(p1.x == p2.x && p2.y == p3.y && p2.x > p3.x){
                //Right to down
                println("Condition 6")
                return S_B_C1
            }
            if(p1.x < p2.x && p1.y == p2.y && p2.x == p3.x){
                //Down to left
                println("Condition 7")
                return S_B_C4
            }
            if(p1.x > p2.x && p1.y == p2.y && p2.x == p3.x){
                //Down to right
                println("Condition 8")
                return S_B_C3
            }
            println("No condition")
            return S_B_C1


    }

}
