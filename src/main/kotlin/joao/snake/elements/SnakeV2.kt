package joao.snake.elements

//Snake constants (Can be an enum)
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

data class SnakeV2(
    val body: List<Position> = mutableListOf<Position>(Position(WIDTH / 2, HEIGHT / 2)),
    val dir: Direction = Direction.RIGHT,
    val stopped: Boolean = false,
    val toGrow: Int = 4
) {

    //Returns the new snake in a new position
    fun move(d: Direction): SnakeV2 {
        val newBody = mutableListOf<Position>()
        // for (i in 0 until body.size)
        if (toGrow > 0) {
            for (i in 0 until body.size + 1) {
                if (i == 0) {
                    newBody.add(nextPos(body[i], dir))
                } else {
                    newBody.add(body[i - 1])
                }
            }
            return SnakeV2(newBody, d, false, toGrow - 1)
        }

        for (i in 0 until body.size) {
            if (i == 0) {
                newBody.add(nextPos(body[i], dir))
            } else {
                newBody.add(body[i - 1])
            }
        }
        return SnakeV2(newBody, d, false, toGrow)
    }

    //Returns the left position of the snake head based on the direction
    fun leftPos(d: Direction): Position{
        return when(d){
            Direction.UP -> if(leftBorder(body[0])) body[0].resetXL() else body[0].left()
            Direction.DOWN -> if(leftBorder(body[0])) body[0].resetXL() else body[0].left()
            Direction.LEFT -> if(downBorder(body[0])) body[0].resetYU() else body[0].down()
            Direction.RIGHT -> if(upBorder(body[0])) body[0].resetYD() else body[0].up()
        }
    }

    //Returns the right position of the snake head based on the direction
    fun rightPos(d: Direction): Position{
        return when(d){
            Direction.UP -> if(rightBorder(body[0])) body[0].resetXR() else body[0].right()
            Direction.DOWN -> if(rightBorder(body[0])) body[0].resetXR() else body[0].right()
            Direction.LEFT -> if(upBorder(body[0])) body[0].resetYD() else body[0].up()
            Direction.RIGHT -> if(downBorder(body[0])) body[0].resetYU() else body[0].down()
        }
    }

    //Returns the next position of the snake head based on the direction
    fun nextPos(p: Position, direction: Direction): Position {
        return when (direction) {
            Direction.UP -> if (upBorder(p)) p.resetYD() else p.up()
            Direction.DOWN -> if (downBorder(p)) p.resetYU() else p.down()
            Direction.LEFT -> if (leftBorder(p)) p.resetXL() else p.left()
            Direction.RIGHT -> if (rightBorder(p)) p.resetXR() else p.right()
        }
    }

    //Returns a list with the positions surrounding the snake head
    fun surroundings(): List<Position>{

        return mutableListOf<Position>(leftPos(dir),rightPos(dir), nextPos(body[0], dir))

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

    //Returns the image of the snake body based on the direction (way too verbose, should be refactored)
    fun snakeImgB(list: List<Position>, index: Int): String {

        val p1 = list[index - 1] //front position
        val p2 = list[index] //middle position
        val p3 = list[index + 1] //back position

        if(downBorder(p1) && upBorder(p2) && upBorder(p3) && p1.x < p3.x){
            return S_B_C3
        }
        if(downBorder(p1) && upBorder(p2) && upBorder(p3) && p1.x > p3.x){
            return S_B_C4
        }
        if(leftBorder(p1) && rightBorder(p2) && rightBorder(p3) && p1.y > p3.y){
            return S_B_C3
        }
        if(leftBorder(p1) && rightBorder(p2) && rightBorder(p3) && p1.y < p3.y){
            return S_B_C2
        }
        if(upBorder(p1) && downBorder(p2) && downBorder(p3) && p1.x < p3.x){
            return S_B_C2
        }
        if(upBorder(p1) && downBorder(p2) && downBorder(p3) && p1.x > p3.x){
            return S_B_C1
        }
        if(rightBorder(p1) && leftBorder(p2) && leftBorder(p3) && p1.y < p3.y){
            return S_B_C1
        }
        if(rightBorder(p1) && leftBorder(p2) && leftBorder(p3) && p1.y > p3.y){
            return S_B_C4
        }
        if(downBorder(p1) && downBorder(p2) && upBorder(p3) && p1.x > p3.x){
            return S_B_C2
        }
        if(downBorder(p1) && downBorder(p2) && upBorder(p3) && p1.x < p3.x){
            return S_B_C1
        }
        if(upBorder(p1) && upBorder(p2) && downBorder(p3) && p1.x > p3.x){
            return S_B_C3
        }
        if(upBorder(p1) && upBorder(p2) && downBorder(p3) && p1.x < p3.x){
            return S_B_C4
        }
        if(rightBorder(p1) && rightBorder(p2) && leftBorder(p3) && p1.y > p3.y){
            return S_B_C2
        }
        if(rightBorder(p1) && rightBorder(p2) && leftBorder(p3) && p1.y < p3.y){
            return S_B_C3
        }
        if(leftBorder(p1) && leftBorder(p2) && rightBorder(p3) && p1.y > p3.y){
            return S_B_C1
        }
        if(leftBorder(p1) && leftBorder(p2) && rightBorder(p3) && p1.y < p3.y){
            return S_B_C4
        }

        if (p1.x == p2.x && p2.x == p3.x) {
            //Horizontal
            return S_B_V
        }
        if (p1.y == p2.y && p2.y == p3.y) {
            //Vertical
            return S_B_H
        }
        if (p1.x == p2.x && p2.x < p3.x && p1.y < p3.y) {
            return S_B_C3
        }
        if (p1.x == p2.x && p2.x > p3.x && p1.y < p3.y) {
            return S_B_C4
        }
        if (p1.x < p2.x && p2.x == p3.x && p2.y < p3.y) {
            return S_B_C1
        }
        if (p1.x > p2.x && p2.x == p3.x && p2.y < p3.y) {
            return S_B_C2
        }
        if (p1.x == p2.x && p2.y == p3.y && p2.x < p3.x) {
            return S_B_C2
        }
        if (p1.x == p2.x && p2.y == p3.y && p2.x > p3.x) {
            return S_B_C1
        }
        if (p1.x < p2.x && p1.y == p2.y && p2.x == p3.x) {
            return S_B_C4
        }
        if (p1.x > p2.x && p1.y == p2.y && p2.x == p3.x) {
            return S_B_C3
        }
        return S_B_C1

    }

}
