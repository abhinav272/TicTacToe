import java.util.*

/**
 * Created by Abhinav on 26/10/17.
 */
class TicTacToeAI {
    val X = "X"
    val O = "O"
    var board = Array(3) { Array(3) { "" } }
    var continueGame = true

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            var ticTacToe = TicTacToeAI()
            println("*************** Welcome to AI based Tic Tac Toe *******************")
            println("You will be the first user(X) and Computer will be the second user(O)")
            println("Enter location number on your chance as below")
            ticTacToe.printDemoBoard()
            while (ticTacToe.continueGame) {
                println("Enter your choice : ")
                var inputPosition: Int
                try {
                    inputPosition = readLine()!!.toInt()
                } catch (e: Exception) {
                    println("Incorrect Format of input")
                    continue
                }

                if (ticTacToe.validatePosition(inputPosition)) {
                    ticTacToe.fillUserInput(inputPosition)
                    if (!ticTacToe.validateWinner()) {
                        if (ticTacToe.isEmptySpaceAvailable()) {
                            ticTacToe.putAIInput()
                        } else {
                            ticTacToe.continueGame = false
                            println("Game Draw")
                        }
                    } else {
                        println("You Won!")
                        ticTacToe.printCurrentBoard()
                        ticTacToe.continueGame = false
                    }
                }
            }

        }
    }

    private fun putAIInput() {
        var flag = false
        outerLoop@ for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] == "") {
                    when {
                        isWinningInput(i, j) -> {
                            board[i][j] = O
                            flag = true
                            break@outerLoop
                        }
                        isBlockingInput(i, j) -> {
                            board[i][j] = O
                            flag = true
                            break@outerLoop
                        }
                    }
                }
            }
        }

        if (!flag)
            putRandomAIInput()
    }

    private fun putRandomAIInput() {
        val random = Random()
        outer@ while (true) {
            val randomPosition = random.nextInt(8) + 1
            when {
                validatePosition(randomPosition) -> {
                    fillInputAtPosition(randomPosition, O)
                    break@outer
                }
            }
        }
    }

    private fun isWinningInput(i: Int, j: Int): Boolean {
        return false
    }

    private fun isBlockingInput(i: Int, j: Int): Boolean {
        return false
    }

    private fun isEmptySpaceAvailable(): Boolean {
        return (0..2)
                .firstOrNull()
                ?.let { board[it].contains("") }
                ?: false
    }

    private fun printCurrentBoard() {
        println("Current board is : ")
        for (i in 0..2) {
            for (j in 0..2) {
                print(" ")
                when {
                    board[i][j] == "" -> print("_")
                    else -> print(board[i][j])
                }
            }
            println()
        }
    }

    private fun validateWinner(): Boolean {
        return false
    }

    private fun fillUserInput(inputPosition: Int) {
        fillInputAtPosition(inputPosition, X)
    }

    private fun fillInputAtPosition(inputPosition: Int, symbol: String) {
        when {
            inputPosition % 3 == 0 -> board[inputPosition / 3 - 1][2] = symbol
            else -> board[inputPosition / 3][inputPosition % 3 - 1] = symbol
        }
    }

    private fun printDemoBoard() {
        var ps = 1
        for (i in 1..3) {
            for (j in 1..3) {
                print("$ps ")
                ps++
            }
            println()
        }
    }

    private fun validatePosition(inputPosition: Int): Boolean {
        return if (inputPosition in 1..9) {
            when {
                inputPosition % 3 == 0 -> board[inputPosition / 3 - 1][2] == ""
                else -> board[inputPosition / 3][inputPosition % 3 - 1] == ""
            }
        } else false
    }
}