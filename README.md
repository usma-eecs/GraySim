# GraySim
This repository contains various simulators to support students enrolled in USMA's CS481 (Operating Systems) course. Each simulator was written by a USMA Cadet, under the guidance of Dr. Maria Ebling, as part of their Honors Thesis; the students' names are indicated below.

## Tracking Impact

These simulators are made freely available to other students and instructors of undergraduate Operating Systems courses. The only request we make is to add a comment to [Issue #1](https://github.com/usma-eecs/GraySim/issues/1), which Dr. Ebling is using to track the impact these simulators have beyond USMA.

## Simulations

### cpu-scheduling (Sierra Zoe Bennett-Manke '25)

This simulation allows students to explore their understanding of single-processor CPU Scheduling. The simulation supports the following schedulers:

* FIFO (First-in-First-out): When the currently running process ceases to execute, the process that has been in the READY state the longest is selected to execute next.

* SJF: When the currently running process ceases to execute, the process that has the SHORTEST expected processing time is selected next. SJF is a non-preemptive scheduling algorithm.

* STCF: When the currently running process ceases to execute, the process that has the SHORTEST remaining processing time is selected next. STCF is a preemptive scheduling algorithm.

* Round Robin: When the currently running process ceases to execute or the timer expires, the process is moved back to the ready queue and the process that has been in the READY state the longest is selected to execute next for a time quanta.

* MLFQ: When the currently running process ceases to execute, it is placed on the next lowest priority queue (if it hasn't completed its processing). The OS allocates the processor to the first process on the highest priority queue. The quantum is 2^i, where i is the priority of the queue on which the process had been waiting. MLFQ is a preemptive scheduling process.

This is the first simulator built. A paper about it was presented during the CCSC-NE conference held in April of 2024. The paper can be found here:

[Sierra Zoe Bennett-Manke and Maria R. Ebling. 2024. GraySim: An OS Scheduling Simulator. J. Comput. Sci. Coll. 39, 8 (April 2024), 55–69.](https://dl.acm.org/doi/10.5555/3665609.3665613)


### page-replacement (Melinda Zhang '24)

This simulation allows students to explore their understanding of page replacement policies. The simulation supports the following policies:

* Optimal: Replace the page that will be used furthest into the future.

* FIFO: Replace the page that was brought into memory first.

* LRU: Replace the page that has not been used for the longest time period.

* Clock: Replace the page identified by the clock algorithm.

## Instructions

To run the simulator,

1. cd into the appropriate directory (cpu-scheduling or page-replacement).

1. Run `sbt`

1. Enter `run`

This should bring the simulation up with a randomized configuration that will allow you to check your understanding of either cpu-scheduling or page-replacement.

For the cpu-scheduling simulation, the quantum can be changed by providing a parameter to run (i.e., 'run --quantum 2')

## Usage

### cpu-scheduling

When the simulation starts up, you are shown a set of processes (named 'A', 'B', and so forth) as well as their start time and service time (i.e., how long they will run). You can click on the tabs across the top to select the scheduling policy you want to practice (i.e., FIFO, SJF, STCF, RR, or MLFQ). On any of those tabs, you can click in the scheduling table (i.e., the light blue cells) to cause a process to run at a particular time; if you click again, it will deselect.

The buttons on the right do the following:

* 'Show Policy' gives an English description of the policy.
* 'Show Feedback' gives a summary of any mistakes the simulation has detected or tells you that your solution is correct.
* 'Show Solution' will put an 'X' in each box that is scheduled.

### page-replacement

When the simulation starts up, you can select which page replacement policy you want to practice by selecting the appropriate tab. Within the policy's page, you can see a page request stream across the headings of the table and the memory frames along the left-hand column. You place a page into a frame using the radio buttons at the bottom of the screen and then hitting 'Enter'. You can back the simulation up by clicking on the 'Undo' button.

The buttons on the right do the following:

* 'Show Policy' gives an English description of the policy.
* 'Show Feedback' gives a summary of any mistakes the simulation has detected or tells you that your solution is correct.
* 'Show Solution' will show a correct solution by placing each page in a frame of memory.

Note that solutions are not unique. This simulation selects the lowest frame number possible in the event of a "tie".




