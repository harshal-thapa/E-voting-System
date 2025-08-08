# Voting System (Java Swing + PostgreSQL)

This is a simple E-Voting prototype:
- Java 11+
- Maven
- Swing UI
- PostgreSQL database

## Setup

1. Create the database and tables (example using psql):
```
CREATE DATABASE voting_system;
\c voting_system

CREATE TABLE voters (
    voter_id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    username VARCHAR(50) UNIQUE,
    password VARCHAR(255),
    has_voted BOOLEAN DEFAULT FALSE
);

CREATE TABLE candidates (
    candidate_id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    party VARCHAR(50),
    votes INT DEFAULT 0
);

CREATE TABLE admin (
    admin_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(255)
);
```

2. Update `src/main/resources/db.properties` with your DB password.

3. Build:
```
mvn clean package
```

4. Initialize admin and two candidates:
```
java -jar target/voting-system-1.0-SNAPSHOT.jar setup
```

5. Run:
```
java -jar target/voting-system-1.0-SNAPSHOT.jar
```

Admin default created by setup: username `admin`, password `admin123`. Change after first run.

## Notes
- Passwords are hashed with bcrypt.
- Voting uses a DB transaction and SELECT ... FOR UPDATE to avoid double-voting races.
- For production, use a connection pool, stronger auth, logging, and security review.
