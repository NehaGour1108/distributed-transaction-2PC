# Distributed Transaction Manager

This project simulates a **distributed transaction** using the **Two-Phase Commit (2PC)** protocol, which ensures data consistency across multiple databases. The system connects to two databases and performs a transaction, either **committing** the changes if both databases are successful, or **rolling back** if any database fails to prepare.

## Key Features:
- **Distributed Transaction Management**: Manages transactions involving two different databases.
- **Two-Phase Commit Protocol**: Ensures both databases are either updated or no updates are made (commit/rollback).
- **Database Connection Handling**: Handles MySQL database connections for distributed transactions.
  
## Technologies:
- **Java** (JDK 8+)
- **MySQL** (Two separate databases for the distributed transaction)

## How It Works:
1. **Prepare Phase**: Both databases are queried to check if they can safely commit the transaction.
2. **Commit Phase**: If both databases pass the prepare phase, changes are committed to both databases.
3. **Rollback Phase**: If either database fails to prepare, both databases roll back any changes, ensuring no inconsistencies.

## Getting Started

### Prerequisites:
- Java 8 or higher
- MySQL Server running with two databases (`db1`, `db2`) set up
- JDBC MySQL Driver configured

### Setup:

1. Clone the repository to your local machine:

   ```bash
   git clone https://github.com/yourusername/distributed-transaction.git
   cd distributed-transaction
   ```

2. Ensure that your MySQL databases (`db1` and `db2`) are set up and accessible. Update the database connection details in the code:
   
   - Database 1 connection details:
     ```java
     db1Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db1?serverTimezone=UTC", "user1", "password1");
     ```
   
   - Database 2 connection details:
     ```java
     db2Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2", "user2", "password2");
     ```

3. Compile and run the program:
   
   ```bash
   javac DistributedTransaction.java
   java DistributedTransaction
   ```

### How to Test:
1. **Start the Program**: The main method will trigger the transaction process.
2. **Observe the Output**: The console will show whether the transaction was committed or rolled back based on the success of the prepare phase in both databases.

### Example Output:
```
Transaction started.
Prepare Phase: Checking db1... Success
Prepare Phase: Checking db2... Success
Transaction Committed Successfully.
```

If an error occurs during the prepare phase, the output will look like this:
```
Transaction Failed: [Error Message]
Transaction Aborted.
```

### File Structure:
```
/src
    /org/example
        DistributedTransaction.java
    /lib
        JDBC MySQL Driver (if needed)
```

## How It Works in Simple Terms:
Imagine you're making an online purchase:
- **Database 1** (User database) is where your balance gets updated.
- **Database 2** (Order database) is where your order gets recorded.

Both databases must successfully process the transaction for it to be committed. If either database encounters an error, the transaction will be rolled back, so no updates are made to the databases, ensuring data consistency.
