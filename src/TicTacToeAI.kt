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
                        ticTacToe.printCurrentBoard()
                    } else {
                        println("You Won!")
                        ticTacToe.printCurrentBoard()
                        ticTacToe.continueGame = false
                    }
                }
            }

        }
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
        when {
            inputPosition % 3 == 0 -> board[inputPosition / 3 - 1][2] = X
            else -> board[inputPosition / 3][inputPosition % 3 - 1] = X
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