# blockachain

This is a project to try out developing my own **blockchain**.  I am following the guidance of the book *Blockchain* by Fertig & Schütz (Rheinwerk Computing, 2024) and filling in the implementation details by myself.

# to run

Run the automated tests with Maven:

    mvn clean test

Note that you will have to create the directory "e:\chains\42\" or modify the path in `Persistence.java` and the network ID in `Blockchain.java` in order for the persistence tests to run.

The test in `MinerTest.java` creates a blockchain and a hundred pending transactions, then proceeds to mine new blocks using a proof-of-work consensus model (like Bitcoin's).  Its "difficulty" setting is hard-coded in Blockchain.java and is tuned to take 2-3 seconds to mine a block *on my computer*.  You may need to adjust it for your own computer; it doesn't automatically adjust.  A larger number (less negative) will make it easier to mine new blocks; a smaller number (greater negative) will make it harder.

