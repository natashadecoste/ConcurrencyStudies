# Java Studies of Concurrency

This is the README.md for the Java Concurrency Studies

**************************************

A collection of java files writted to understand multithreading, interleaving, concurrency and synchronization. Uses monitors and semaphore. 

SeatBooking.java - A system where there are client threads that pick a seat at random (at a venue with N amounts of seats), then are served by clerk threads. Clerks use the synchronized "monitor" in order to book the seat for the client and issue them a ticket. This monitor is for not allowing double booking by the clerks.

DiningSavages.java - Modelling the Dining Savages problem. There are N savages all sharing food from a pot that holds M servings. The cook refills the pot as soon as it is empty (servings = 0). At any time, the savages are trying to get a serving. Only one can access the pot at once (Mutual Exclusion.) Here it is simulated with 4 Savages and a pot containing 6 portions (or servings)
. It can be scaled to N savages and M portions if necessary.

Barrier.java - Class barrier has a sync function that is mutually exclusive however all threads must perform the sync before any of them can continue to execute their following instructions. Currently modelled with 3 threads.  


***************************************

Natasha DeCoste
McMaster University

****************************************

Note: These are meant to display the concurrency in the console outputs.

****************************************

COMPSCI 3SD3