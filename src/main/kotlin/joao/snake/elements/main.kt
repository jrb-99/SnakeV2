/*package joao.snake.elements

import pt.isel.canvas.*

    fun main() {

        var iniGame = Game()
        val canvas = Canvas(WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE, BLACK)

        onStart {

            drawGame(canvas, iniGame)

            //Listen to key events and update the snake position if allowed
            canvas.onKeyPressed { k ->
                val newSnk = when {
                    k.code == UP_CODE && iniGame.snake.dir != DOWN && iniGame.movableLeft() -> Snake(iniGame.snake.pos, UP)
                    k.code == DOWN_CODE && iniGame.snake.dir != UP && iniGame.movableRight() -> Snake(iniGame.snake.pos, DOWN)
                    k.code == LEFT_CODE && iniGame.snake.dir != RIGHT && iniGame.movableLeft() -> Snake(iniGame.snake.pos, LEFT)
                    k.code == RIGHT_CODE && iniGame.snake.dir != LEFT && iniGame.movableRight() -> Snake(iniGame.snake.pos, RIGHT)
                    else -> iniGame.snake
                }
                iniGame = Game(newSnk, iniGame.wall)
            }//onKeyPressed

            canvas.onTimeProgress(REFRESH_RATE) {
                iniGame = iniGame.advance()
                drawGame(canvas, iniGame)

            }//onTimeProgress 250

            canvas.onTimeProgress(WALL_REFRESH_RATE) {
                iniGame = Game(iniGame.snake, iniGame.genWall(iniGame.wall))
            }//onTimeProgress 5000

        }//onStart

    }//fun main()

    //Draws the game status
    fun drawGame(canvas: Canvas, game: Game) {

        canvas.erase()
        //drawGrid(canvas, WIDTH, HEIGHT, CELL_SIZE) used for development purposes
        drawSnake(canvas, game.snake)
        drawWall(canvas, game.wall)

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

    //Draws the snake (tail+head)
    fun drawSnake(canvas: Canvas, snk: Snake) {

        drawSnakeH(canvas, snk, snk.dir)
        drawSnakeT(canvas, snk, snk.dir)
    }

    //Draws the snake head
    fun drawSnakeH(canvas: Canvas, snk: Snake, d: Int) {

        canvas.drawImage(snk.snakeImgH(d), snk.pos.x * CELL_SIZE, snk.pos.y * CELL_SIZE, CELL_SIZE, CELL_SIZE)

    }

    //Draws the snake tail
    fun drawSnakeT(canvas: Canvas, snk: Snake, d: Int) {

        when (d) {
            UP -> canvas.drawImage(snk.snakeImgT(d), snk.pos.x * CELL_SIZE, (snk.pos.y + 1) * CELL_SIZE, CELL_SIZE, CELL_SIZE)
            DOWN -> canvas.drawImage(snk.snakeImgT(d), snk.pos.x * CELL_SIZE, (snk.pos.y - 1) * CELL_SIZE, CELL_SIZE, CELL_SIZE)
            LEFT -> canvas.drawImage(snk.snakeImgT(d), (snk.pos.x + 1) * CELL_SIZE, snk.pos.y * CELL_SIZE, CELL_SIZE, CELL_SIZE)
            RIGHT -> canvas.drawImage(snk.snakeImgT(d), (snk.pos.x - 1) * CELL_SIZE, snk.pos.y * CELL_SIZE, CELL_SIZE, CELL_SIZE)
            else -> canvas.drawImage(snk.snakeImgT(d), (snk.pos.x - 1) * CELL_SIZE, snk.pos.y * CELL_SIZE, CELL_SIZE, CELL_SIZE)
        }
    }

    //Draws game walls
    fun drawWall(canvas: Canvas, wall: List<Position>) {

        for (w in wall) {
            canvas.drawImage(BRICKS, w.x * CELL_SIZE, w.y * CELL_SIZE, CELL_SIZE, CELL_SIZE)
        }

    }*/