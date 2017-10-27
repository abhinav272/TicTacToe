import java.util.*

/**
 * Created by Abhinav on 26/10/17.
 */
class TicTacToeAI {
    val X = "X"
    val O = "O"
    val userWinArray = arrayOf(X, X, X)
    val computerWinArray = arrayOf(O, O, O)
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
        winnerLoop@ for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] == "" && isWinningInput(i, j)) {
                    println("winning input $i -- $j")
                    board[i][j] = O
                    flag = true
                    break@winnerLoop
                }
            }
        }

        if (!flag) {
            blockingLoop@ for (i in 0..2) {
                for (j in 0..2) {
                    if (board[i][j] == "" && isBlockingInput(i, j)) {
                        println("blocking input $i -- $j")
                        board[i][j] = O
                        flag = true
                        break@blockingLoop
                    }
                }
            }
        }

        if (!flag)
            putRandomAIInput()
        if (validateWinner()) {
            println("Computer Won")
            printCurrentBoard()
            continueGame = false
        } else
            printCurrentBoard()
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
        when (i) {
            0 -> {
                when (j) {
                    0 -> return (1..2).all { board[it][it] == O }
                            || (1..2).all { board[0][it] == O }
                            || (1..2).all { board[it][0] == O }
                    1 -> return (0..2).step(2).all { board[0][it] == O }
                            || (1..2).all { board[it][1] == O }
                    2 -> return (0..1).step(2).all { board[0][it] == O }
                            || (1..2).all { board[it][2] == O }
                }
            }
            1 -> {
                when (j) {
                    0 -> return (1..2).all { board[1][it] == O }
                            || (0..2).step(2).all { board[it][0] == O }
                    1 -> return (0..2).step(2).all { board[it][it] == O }
                            || (0..2).step(2).all { board[1][it] == O }
                            || (0..2).step(2).all { board[it][1] == O }
                            || (board[0][2] == O && board[2][0] == O)
                    2 -> return (0..2).step(2).all { board[it][2] == O }
                            || (0..1).all { board[1][it] == O }
                }
            }
            2 -> {
                when (j) {
                    0 -> return (1..2).all { board[2][it] == O }
                            || (0..1).all { board[it][0] == O }
                    1 -> return (0..2).step(2).all { board[2][it] == O }
                            || (0..1).all { board[it][1] == O }
                    2 -> return (0..1).all { board[it][it] == O }
                            || (0..1).all { board[2][it] == O }
                            || (0..1).all { board[it][2] == O }
                }
            }
        }
        return false
    }

    private fun isBlockingInput(i: Int, j: Int): Boolean {
        when (i) {
            0 -> {
                when (j) {
                    0 -> return (1..2).all { board[it][it] == X }
                            || (1..2).all { board[0][it] == X }
                            || (1..2).all { board[it][0] == X }
                    1 -> return (0..2).step(2).all { board[0][it] == X }
                            || (1..2).all { board[it][1] == X }
                    2 -> return (0..1).all { board[0][it] == X }
                            || (1..2).all { board[it][2] == X }
                }
            }
            1 -> {
                when (j) {
                    0 -> return (1..2).all { board[1][it] == X }
                            || (0..2).step(2).all { board[it][0] == X }
                    1 -> return (0..2).step(2).all { board[it][it] == X }
                            || (0..2).step(2).all { board[it][1] == X }
                            || (0..2).step(2).all { board[1][it] == X }
                            || (board[0][2] == X && board[2][0] == X)
                    2 -> return (0..2).step(2).all { board[it][2] == X }
                            || (0..1).all { board[1][it] == X }
                }
            }
            2 -> {
                when (j) {
                    0 -> return (1..2).all { board[2][it] == X }
                            || (0..1).all { board[it][0] == X }
                            || (board[0][2] == X && board[1][1] == X)
                    1 -> return (0..2).step(2).all { board[2][it] == X }
                            || (0..1).all { board[it][1] == X }
                    2 -> return (0..1).all { board[it][it] == X }
                            || (0..1).all { board[it][2] == X }
                            || (0..1).all { board[2][it] == X }
                }
            }
        }
        return false
    }

    private fun isEmptySpaceAvailable(): Boolean {
        return (0..2).any { board[it].contains("") }
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
        return (0..2).any {
            board[it].contentEquals(userWinArray)
                    || board[it].contentEquals(computerWinArray)
        }
                .or(checkVerticalWinning())
                .or(checkDiagonalWinning())
    }

    private fun checkDiagonalWinning(): Boolean {
        return (0..2).all { board[it][it] == X }
                || (0..2).all { board[it][it] == O }
                || (0..2).all { board[it][2 - it] == X }
                || (0..2).all { board[it][2 - it] == O }
    }

    private fun checkVerticalWinning(): Boolean {
        val arr = Array(3) { "" }
        for (i in 0..2) {
            for (j in 0..2) {
                arr[j] = board[j][i]
            }
            if (arr.contentEquals(userWinArray) || arr.contentEquals(computerWinArray)) {
                return true
            }
        }
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