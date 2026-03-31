# 🎮 VideoGame Manager (VGM) - Backend API

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green)
![MongoDB](https://img.shields.io/badge/MongoDB-Latest-47A248)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED)
![Jenkins](https://img.shields.io/badge/Jenkins-CI%2FCD-D24939)

## 🚀 Overview

**VideoGame Manager** is a RESTful API designed to manage a catalog of 150+ video games. This project serves as my personal lab for mastering **Spring Boot**, **NoSQL persistence**, and **DevOps automation** within the Java ecosystem.

> [\!NOTE] > **Work in Progress:** This project is currently under active development as part of my DAM (Multi-platform Application Development) studies. New features and architectural improvements are being added regularly.  

## 🛠️ Tech Stack

  * **Backend:** Java 17 + Spring Boot 3.x (Spring Data MongoDB & Spring Web).
  * **Database:** MongoDB (NoSQL) for flexible data modeling.
  * **Containerization:** Docker & Docker Compose for seamless App + DB orchestration.
  * **CI/CD:** Jenkins (Implementing Build & Test pipelines).
  * **API Testing:** Postman.

## 🏗️ Project Highlights

  * **Automated Data Seeding:** Includes a `catalog.json` to pre-load the database with 148+ verified game records.
  * **Clean Architecture:** Built following the **Controller-Service-Repository** pattern.
  * **DevOps Workflow:** Fully dockerized environment for "one-command" local deployment.
  * **Custom Queries:** Implementation of MongoDB filtering and basic aggregations.

## 📦 Installation & Setup

The project is designed to run entirely within **Docker**:

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/javtl/videogamemanager.git
    ```

2.  **Spin up the environment:**

    ```bash
    docker-compose up --build
    ```

    *This command starts both the Spring Boot API and the MongoDB instance.*

3.  **Access the API:**
    The service will be available at `http://localhost:8080/api/games`

-----

*Note: Focused on professional backend development standards and hands-on DevOps practices.*

-----

**Un último detalle:** Asegúrate de que el archivo se llame exactamente `README.md` (en mayúsculas) en la raíz de tu proyecto para que GitHub lo renderice automáticamente.

¡Ya lo tienes listo para subir\! ¿Te convence este enfoque?
