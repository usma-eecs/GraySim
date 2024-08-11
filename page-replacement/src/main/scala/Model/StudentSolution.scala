package model

import scala.collection.mutable.Stack

class StudentSolution(numFrames: Int, numRequests: Int):
    var solution: Array[Array[String]] = Array.ofDim[String](numFrames, numRequests)
    private val historyStack = Stack[Array[Array[String]]]()
    var currentCol = 0
    private val idleEntry = "-"

    init

    def init: Unit =
        for row <- solution.indices do
            for column <- solution(row).indices do
                solution(row)(column) = idleEntry
        historyStack.popAll()

    def push: Unit =
        historyStack.push(solution.map(_.clone))

    def pop: String =
        if historyStack.nonEmpty then
            solution = historyStack.pop
            currentCol -= 1
            "undone"
        else
            "Nothing to be undone"

    def getNextPageRequest(stream: PageRequestStream): String =
        var returnString = "N/A"
        if currentCol < numRequests then
            returnString = stream.index(currentCol).toString()
        returnString

    def pageIn(frame: Int, position: Int, page: String): Unit =
        // If we are in range, then pageIn; otherwise, do nothing
        if position < numRequests then
            // Push the current state onto our historyStack
            push

            // Update contents of frames to be what they were at previous point
            if (position > 0)
                for row <- solution.indices do
                    solution(row)(position) = solution(row)(position-1)

            // Place the specified page into the specified position
            solution(frame)(position) = page

    def pageIn(frame: Int, page: String): Unit =
        pageIn(frame, currentCol, page)
        if currentCol < numRequests then
            currentCol += 1

    def hasDuplicate: Boolean =
        var duplicate = false
        for column <- solution(0).indices do
            var pagesInMemory = Set.empty[String]
            for row <- solution.indices do
                if solution(row)(column) != "-" then
                    if pagesInMemory contains solution(row)(column) then
                        duplicate = true
                    pagesInMemory += solution(row)(column)
        duplicate

    def complete: Boolean =
        var complete = true
        for column <- solution(0).indices do
            var numEmptyFrames = 0
            for row <- solution.indices do
                if solution(row)(column) == "-" then
                    numEmptyFrames += 1
            if column >= numFrames - 1 then
                if numEmptyFrames > 0 then
                    complete = false
            else
                if numEmptyFrames > numFrames - column - 1 then
                    complete = false

        complete

    def show: String =
        val sb = new StringBuilder("")
        for row <- solution.indices do
            sb ++= "Frame " + row.toString() + ": "
            for column <- solution(row).indices do
                sb ++= solution(row)(column) + " "
            sb ++= "\n"
        sb.toString

    def getData: Array[Array[String]] =
        solution
