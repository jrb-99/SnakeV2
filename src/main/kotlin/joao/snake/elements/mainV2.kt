package joao.snake.elements

import pt.isel.canvas.*

fun main(){

    var iniGame = GameV2()
    val canvas = Canvas(WIDTH * CELL_SIZE, (HEIGHT + SCOREBOARD_HEIGHT) * CELL_SIZE, BLACK)

    drawGame(canvas, iniGame)

    //Listen to key events and update the snake position if allowed
    canvas.onKeyPressed { k ->
        val newDirection = when (k.code) {
            UP_CODE -> Direction.UP
            DOWN_CODE -> Direction.DOWN
            LEFT_CODE -> Direction.LEFT
            RIGHT_CODE -> Direction.RIGHT
            else -> iniGame.snake.dir
        }
        if (iniGame.isDirectionChangeAllowed(newDirection) && iniGame.snake.dir.opposite() != newDirection) {
            iniGame = GameV2(SnakeV2(iniGame.snake.body, newDirection, iniGame.snake.stopped, iniGame.snake.toGrow), iniGame.wall, iniGame.apple, iniGame.score)
        }
    }//onKeyPressed

    canvas.onTimeProgress(REFRESH_RATE) {

        iniGame = iniGame.advance()
        drawGame(canvas, iniGame)


    }//onTimeProgress 250

    canvas.onTimeProgress(WALL_REFRESH_RATE) {
        iniGame = GameV2(iniGame.snake, iniGame.genWall(iniGame.wall), iniGame.apple, iniGame.score)
    }//onTimeProgress 5000

}

//Draws the game status
fun drawGame(canvas: Canvas, game: GameV2) {

    canvas.erase()
    drawGrid(canvas, WIDTH, HEIGHT, CELL_SIZE) //used for development purposes
    drawSnake(canvas, game.snake)
    drawWall(canvas, game.wall)
    drawApple(canvas, game.apple)
    drawScoreBoard(canvas, game)

}

//Draws a grid (used for development purposes)
fun drawGrid(canvas: Canvas, w: Int, h: Int, cs: Int) {

    //Grid
    for (i in 0..w) {
        for (j in 0..h) {
            canvas.drawRect(i * cs, j * cs, cs, cs, WHITE, 1)
        }
    }

}

//Draws the scoreboard
fun drawScoreBoard(canvas: Canvas, game: GameV2) {
    canvas.drawRect(0, HEIGHT * CELL_SIZE, WIDTH * CELL_SIZE, SCOREBOARD_HEIGHT * CELL_SIZE, WHITE, 5)
    canvas.drawRect(0, HEIGHT * CELL_SIZE, WIDTH * CELL_SIZE, SCOREBOARD_HEIGHT * CELL_SIZE, BLACK)

    canvas.drawText(0, (HEIGHT + (SCOREBOARD_HEIGHT/2)) * CELL_SIZE, "Score: ${game.score}", RED, 20)
    canvas.drawText(3 * CELL_SIZE, (HEIGHT + (SCOREBOARD_HEIGHT/2)) * CELL_SIZE, "Snake lenght: ${game.snake.body.size}", WHITE, 20)
    canvas.drawText(9 * CELL_SIZE, (HEIGHT + (SCOREBOARD_HEIGHT/2)) * CELL_SIZE, game.endGameString(), YELLOW, 20)

    //timer(canvas)
}

//Draws the snake
fun drawSnake(canvas: Canvas, snk: SnakeV2) {

    drawSnakeH(canvas, snk, snk.dir)
    drawSnakeBody(canvas, snk)
    drawSnakeT(canvas, snk)

}

//Draws the snake head
fun drawSnakeH(canvas: Canvas, snk: SnakeV2, d: Direction) {

    canvas.drawImage(snk.snakeImgH(d), snk.body[0].x * CELL_SIZE, snk.body[0].y * CELL_SIZE, CELL_SIZE, CELL_SIZE)

}

//Draws the snake body
fun drawSnakeBody(canvas: Canvas, snk: SnakeV2) {

    if(snk.body.size < 2) return

    for (i in 1 until snk.body.size - 1) {
        canvas.drawImage(snk.snakeImgB(snk.body, i), snk.body[i].x * CELL_SIZE, snk.body[i].y * CELL_SIZE, CELL_SIZE, CELL_SIZE)
    }

}

//Draws the snake tail
//TODO -> move this logic to Snake
fun drawSnakeT(canvas: Canvas, snk: SnakeV2) {
    if (snk.body.size == 1) return

    if(snk.upBorder(snk.tail()) && snk.downBorder(snk.afterTail())){
        canvas.drawImage(snk.snakeImgT(Direction.UP), snk.tail().x * CELL_SIZE, snk.tail().y * CELL_SIZE, CELL_SIZE, CELL_SIZE)
        return
    }
    if(snk.upBorder(snk.afterTail()) && snk.downBorder(snk.tail())){
        canvas.drawImage(snk.snakeImgT(Direction.DOWN), snk.tail().x * CELL_SIZE, snk.tail().y * CELL_SIZE, CELL_SIZE, CELL_SIZE)
        return
    }
    if(snk.leftBorder(snk.tail()) && snk.rightBorder(snk.afterTail())){
        canvas.drawImage(snk.snakeImgT(Direction.LEFT), snk.tail().x * CELL_SIZE, snk.tail().y * CELL_SIZE, CELL_SIZE, CELL_SIZE)
        return
    }
    if(snk.leftBorder(snk.afterTail()) && snk.rightBorder(snk.tail())){
        canvas.drawImage(snk.snakeImgT(Direction.RIGHT), snk.tail().x * CELL_SIZE, snk.tail().y * CELL_SIZE, CELL_SIZE, CELL_SIZE)
        return
    }
    if(snk.tail().x < snk.afterTail().x){
        canvas.drawImage(snk.snakeImgT(Direction.RIGHT), snk.tail().x * CELL_SIZE, snk.tail().y * CELL_SIZE, CELL_SIZE, CELL_SIZE)
    }
    if(snk.tail().x > snk.afterTail().x){
        canvas.drawImage(snk.snakeImgT(Direction.LEFT), snk.tail().x * CELL_SIZE, snk.tail().y * CELL_SIZE, CELL_SIZE, CELL_SIZE)
    }
    if(snk.tail().y < snk.afterTail().y){
        canvas.drawImage(snk.snakeImgT(Direction.DOWN), snk.tail().x * CELL_SIZE, snk.tail().y * CELL_SIZE, CELL_SIZE, CELL_SIZE)
    }
    if(snk.tail().y > snk.afterTail().y){
        canvas.drawImage(snk.snakeImgT(Direction.UP), snk.tail().x * CELL_SIZE, snk.tail().y * CELL_SIZE, CELL_SIZE, CELL_SIZE)
    }

}

//Draws game walls
fun drawWall(canvas: Canvas, wall: List<Position>) {

    for (w in wall) {
        canvas.drawImage(BRICKS, w.x * CELL_SIZE, w.y * CELL_SIZE, CELL_SIZE, CELL_SIZE)
    }
}

//Draws the apple
fun drawApple(canvas: Canvas, apple: Position?) {

    if(apple != null){
        canvas.drawImage(APPLE, apple.x * CELL_SIZE, apple.y * CELL_SIZE, CELL_SIZE, CELL_SIZE)
    }

}
