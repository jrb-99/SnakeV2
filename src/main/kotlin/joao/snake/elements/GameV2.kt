package joao.snake.elements

import pt.isel.canvas.playSound
import java.io.File

//Game constants (Can be an enum)
const val WIDTH = 30
const val HEIGHT = 20
const val SCOREBOARD_HEIGHT = 2
const val CELL_SIZE = 32
const val REFRESH_RATE = 250//3000
const val WALL_REFRESH_RATE = 5000
const val GROW_RATE = 5
const val PPA = 1 //PPA: Points per Apple
const val SND_BUMP = "snd/smb_bump"
const val SND_EAT = "snd/smb_powerup"
const val SND_BRICK = "snd/smb_kick"
const val SND_WIN = "snd/smb_stage_clear"
const val SND_LOSE = "snd/smb_gameover"
const val SND_NEW_HIGH = "snd/smb_world_clear"
const val SCORE_FILE = "src/main/resources/highScore.txt"

data class GameV2(val snake: SnakeV2 = SnakeV2(), val wall: List<Position> = generateCornerPositions(), val apple: Position? = null, val score: Int = 0, val highestScore: Int = readHighestScore()) {

    //Returns the next game status, checking if the snake has hit a wall
    fun advance(): GameV2 {

        //Check collision with wall
        if(wall.contains(snake.nextPos(snake.body[0], snake.dir))){
            playSound(SND_BUMP)
            return GameV2(SnakeV2(snake.body, snake.dir, true, snake.toGrow), wall, apple, score, highestScore)
        }

        //Check collision with snake body
        if(snake.body.subList(1, snake.body.size).contains(snake.nextPos(snake.body[0], snake.dir))){
            playSound(SND_BUMP)
            return GameV2(SnakeV2(snake.body, snake.dir, true, snake.toGrow), wall, apple, score, highestScore)
        }

        //Return growing snake or current snake
        val snk = snakeType().move(snake.dir)

        //Return game status if snake eats an apple
        if(snake.body[0] == apple){
            return GameV2(snk, wall, null, score + PPA, highestScore)
        }

        //Return game status if snake doesn't eat any apple
        return GameV2(snk, wall, genApple(), score)
    }

    //Returns snake type (has eaten apple or not)
    fun snakeType(): SnakeV2{

        if(snake.body[0] == apple){
            playSound(SND_EAT)
            return SnakeV2(snake.body, snake.dir, snake.stopped, snake.toGrow + GROW_RATE)
        }
        return snake

    }

    //Returns true if the snake can change direction
    fun isDirectionChangeAllowed(newDirection: Direction): Boolean {
        val nextPosition = snake.nextPos(snake.body[0], newDirection)
        if(!snake.body.contains(nextPosition) && !wall.contains(nextPosition)){
            return true
        }
        playSound(SND_BUMP)
        return !snake.body.contains(nextPosition) && !wall.contains(nextPosition)
    }

    //Returns the end game string based on the score
    fun endGameString(): String{
        if(gameOver() && score >= 11) {
            return "You won"
        } else if(gameOver() && score < 11){
            return "You lost"
        }
        return ""
    }

    //Plays a sound based on the end game result
    fun endGameSound(){
        if(gameOver() && score >= highestScore){
            playSound(SND_NEW_HIGH)
        }else if(gameOver() && score >= 11) {
            playSound(SND_WIN)
        }else if(gameOver() && score < 11){
            playSound(SND_LOSE)
        }
    }

    //Returns true if the game is over (snake stopped and can't move)
    fun gameOver(): Boolean {
        writeHighScore(highestScore, score)
        return snake.stopped && cantMove()
    }

    //Writes the high score to a file
    fun writeHighScore(highScore: Int ,score: Int){
        val file = File(SCORE_FILE)
        if (!file.exists()) {
            file.createNewFile()
        }
        if(highScore < score){
            File(SCORE_FILE).writeText(score.toString())
        }
    }

    //Returns true if the snake can't move
    fun cantMove(): Boolean {
        val occupied = wall + snake.body
        for (p in snake.surroundings()) {
            if (!occupied.contains(p)) {
                return false
            }
        }
        return true
    }

    //Generates a new wall and plays a sound
    fun genWall(list: List<Position>): List<Position> {

        val newList = list.toMutableList()
        var new_w = Position((0..<WIDTH).random(), (0..<HEIGHT).random())

        while (wall.contains(new_w) || snake.body.contains(new_w) || apple == new_w){

            new_w = Position((0..<WIDTH).random(), (0..<HEIGHT).random())

        }

        newList += new_w
        playSound(SND_BRICK)

        return newList

    }

    //Generates a new apple
    fun genApple(): Position {

        if(apple == null){

            var p = Position((0..<WIDTH).random(), (0..<HEIGHT).random())

            while (wall.contains(p) || snake.body.contains(p)) {
                p = Position((0..<WIDTH).random(), (0..<HEIGHT).random())
            }

            return p

        }
        return apple
    }

}//end of class GameV2

//Utility function to generate corners instead adding them manually
fun generateCornerPositions(): List<Position> {
    return listOf(
        // Top-left corner
        Position(0, 0), Position(1, 0), Position(0, 1),
        // Top-right corner
        Position(WIDTH - 1, 0), Position(WIDTH - 2, 0), Position(WIDTH - 1, 1),
        // Bottom-left corner
        Position(0, HEIGHT - 1), Position(1, HEIGHT - 1), Position(0, HEIGHT - 2),
        // Bottom-right corner
        Position(WIDTH - 1, HEIGHT - 1), Position(WIDTH - 2, HEIGHT - 1), Position(WIDTH - 1, HEIGHT - 2)
    )
}

//Utility function to read the highest score from a file, if it doesn't exist, it creates it
fun readHighestScore(): Int {
    val file = File(SCORE_FILE)
    if (!file.exists()) {
        file.createNewFile()
        file.writeText("0")
        file.readText().toInt()
    }
    return file.readText().toInt()
}