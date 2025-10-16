# 🗓️ Weekly Planner

**Author:** Gia Bao Bui  
**Purpose:** Technical assignment for IntegraDev – Demonstration of object-oriented design, clean architecture, and system evolvability in Java.

---

## Overview

This desktop application is a modular weekly planner designed with a focus on clean architecture, evolvability, and testability rather than full UI completeness.

Users can create tasks, set their properties, and generate a planned weekly schedule based on task attributes and user preferences (e.g., working hours).

The system's design highlights:

- A domain planner that encapsulates the core planning logic
- A layered architecture (JavaFX UI → Domain → Repository → DAO → Database)

---

## Architecture

The application follows a **Layered Architecture** with **Ports & Adapters** pattern, ensuring clean separation of concerns and testability.

```mermaid
graph TB
    subgraph "UI Layer"
        UI[JavaFX Controllers]
    end
    
    subgraph "Application Layer"
        APP[Use Cases<br/>PlanWeek, AddTask]
    end
    
    subgraph "Domain Layer"
        subgraph "Model"
            DOMAIN_MODEL[Task<br/>Value Objects]
        end
        subgraph "Ports"
            TASK_REPO[TaskRepository<br/>Interface]
        end
    end
    
    subgraph "Infrastructure Layer"
        subgraph "Persistence"
            TASK_ENTITY[TaskEntity]
            TASK_MAPPER[TaskMapper<br/>Task ↔ TaskEntity]
            REPO_IMPL[TaskRepositoryImpl<br/>implements TaskRepository]
            DAO[DAO (ORMLite)]
            DB[(SQLite Database)]
        end
    end
    
    UI --> APP
    APP --> TASK_REPO
    TASK_REPO <|.. REPO_IMPL
    REPO_IMPL --> DAO
    DAO --> DB
    REPO_IMPL --> TASK_MAPPER
    TASK_MAPPER --> DOMAIN_MODEL
    TASK_MAPPER --> TASK_ENTITY
    
    classDef domainClass fill:#e1f5fe
    classDef infraClass fill:#f3e5f5
    classDef uiClass fill:#e8f5e8
    
    class DOMAIN_MODEL,TASK_REPO domainClass
    class TASK_ENTITY,TASK_MAPPER,REPO_IMPL,DAO,DB infraClass
    class UI,APP uiClass
```

**Key Architectural Principles:**

- **Domain Layer** (innermost): Contains business logic with no framework dependencies
- **Application Layer**: Orchestrates use cases and coordinates between UI and domain
- **Infrastructure Layer**: Implements ports and handles external concerns (database, UI)
- **Dependency Inversion**: Outer layers depend on inner layers through interfaces

---

## Key Features

- ✅ Task creation and management
- ✅ Task prioritization and status tracking
- ✅ Weekly planning algorithm
- ✅ Clean architecture implementation
- ✅ Repository pattern with ORM integration

---

## Current Progress

The application has demonstrated a working flow from the UI → Application → Repository → DAO → SQLite Database.

**Completed Components:**

- Domain model and repositories
- Application services (use cases)
- Persistence layer with ORMLite
- Basic JavaFX UI for task creation
- Database configuration and initialization

---

## 🧰 Tech Stack

- **Language:** Java 11
- **UI:** JavaFX
- **ORM:** ORMLite
- **Database:** SQLite (Docker optional)
- **Build Tool:** Maven
- **Architecture:** Layered architecture with domain-driven design principles

---

## 📚 How to Run

```bash
# Clone repository
git clone https://github.com/giabaobui/weekly-planner.git
cd weekly-planner

# Build
mvn clean install

# Run
mvn javafx:run
```

---

## Notes

This submission prioritizes architecture quality, extensibility, and domain design clarity over visual polish. The codebase demonstrates my approach to incremental feature development, clean abstraction, and maintainable OOP design under time constraints.