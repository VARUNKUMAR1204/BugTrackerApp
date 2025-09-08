# BugTrackerApp

A full-stack bug tracking application built with Spring Boot and React.

## **Live URLs** 

- **Frontend**: https://bug-tracker-app-vert.vercel.app/
- **Backend API**: [https://bugtrackerapp-production-d87d.up.railway.app/api](https://bugtrackerapp-production-d87d.up.railway.app/api)

## **Tech Stack** 

**Backend:**
- Java 17
- Spring Boot 3.1.0
- Spring Data JPA
- Spring Security
- MySQL (Local) / PostgreSQL (Production)
- Maven

**Frontend:**
- React 18
- React Router DOM
- Axios
- CSS3

**Deployment:**
- Backend: Railway
- Frontend: Vercel
- Database: Railway PostgreSQL

## **Features** 

**User Authentication** - Registration and login   
**Bug Management** - Create, read, update, delete bugs   
**Status Workflow** - NEW → IN PROGRESS → FIXED → VERIFIED   
**Comment System** - Threaded comments on bugs   
**Filtering** - By severity (HIGH/MEDIUM/LOW) and status   
**Quick filter** -  Filter Button for HIGH + NEW bugs   
**User Assignment** - Assign bugs to team members   
**Responsive Design** - Works on desktop and mobile   


##  **API Endpoints** 

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - User login

### Bug Management
- `GET /api/bugs` - Get all bugs (supports filtering: `severity=HIGH&status=NEW`)
- `POST /api/bugs` - Create new bug
- `GET /api/bugs/{id}` - Get bug by ID
- `PUT /api/bugs/{id}/status` - Update bug status
- `GET /api/bugs/high-priority-new` - Get high priority new bugs

### Comments
- `GET /api/comments/bug/{bugId}` - Get comments for bug
- `POST /api/comments/bug/{bugId}` - Add comment to bug

### Users
- `GET /api/users` - Get all users



## **Database Schema** 
Database Schema

`CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  email VARCHAR(100) UNIQUE NOT NULL,
  name VARCHAR(100) NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);`

`CREATE TABLE bugs (
  id BIGSERIAL PRIMARY KEY,
  title VARCHAR(200) NOT NULL,
  description TEXT,
  severity VARCHAR(20) NOT NULL,
  status VARCHAR(20) NOT NULL DEFAULT 'NEW',
  assignee_id BIGINT REFERENCES users(id),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);`

`CREATE TABLE comments (
  id BIGSERIAL PRIMARY KEY,
  bug_id BIGINT NOT NULL REFERENCES bugs(id),
  author_id BIGINT NOT NULL REFERENCES users(id),
  body TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);`


## **Running Locally**

Installation

`git clone https://github.com/VARUNKUMAR1204/BugTrackerApp.git`

Backend Setup:

`cd bug-tracker/backend`
`mvn clean install`
`mvn spring-boot:run`

Frontend Setup:

`cd bug-tracker/frontend`
`npm install`
`npm start`

Database Setup:

`CREATE DATABASE bugtracker;`

Update application.properties with your MySQL credentials.

Visit http://localhost:3000 to explore the UI. The backend runs on http://localhost:8080

## **Screenshots**
[Dash Board]
<img width="1918" height="924" alt="Screenshot 2025-08-18 223429" src="https://github.com/user-attachments/assets/6f8d199b-6bcf-411a-9f84-119190ed0fe6" />


[Create Bug]
<img width="1918" height="910" alt="Screenshot 2025-08-18 223542" src="https://github.com/user-attachments/assets/8413bf00-a9ef-46f8-aa5d-673c19fe8367" />



[Bug Details]
<img width="1919" height="911" alt="Screenshot 2025-08-18 223631" src="https://github.com/user-attachments/assets/b97dff77-0dd1-4b2b-8b72-20951f2dfb18" />



[Filters]
<img width="1919" height="903" alt="Screenshot 2025-08-18 223658" src="https://github.com/user-attachments/assets/24f14884-08de-4bb1-bdc0-4a0e9bdf71ef" />



##**Thunder Client API Tests**
