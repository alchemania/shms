
# Student Health Management System README

## Overview
This project is a Student Health Management System developed during the pandemic, utilizing a mix of Springboot and Vue for a comprehensive management solution. The project structure includes:
- **Frontend**: Developed with Vue, located in `/shms-frontend`.
- **Backend**: Developed with Springboot, leveraging MySQL for database management (hosted on Tencent Cloud Services), and located in `/src/main/java/com/shms`. It includes:
  - `/admin`: Functions specific to the admin module.
  - `/common`: Functions for regular user features.

## Getting Started

### Prerequisites
- Node.js (version 16 or higher)
- An IDE with Maven support for Springboot projects

### Project Setup
1. **Backend Initialization**:
   - Refresh Maven dependencies.
   - Start Springboot application through your IDE.

2. **Frontend Initialization**:
   - Navigate to `shms-frontend` directory in the command line.
   - Execute `npm run dev` to start the frontend. (Ensure Node.js is installed)
   - Access the application via `http://localhost:5173` in a web browser.
   - If `node_modules` folder is missing:
     - Run `npm run preview` to execute the pre-packaged files.
     - Alternatively, run `npm install` to install dependencies, followed by `npm run dev`.

## Backend Code Structure
- **Layered Architecture**: Divided into controller, DAO (entity), mapper, and service layers.
- **Utility Classes**: Located in the `utils` folder, encapsulating common tools.
- **Data Persistence**: Utilizes Mybatisplus interfaces for mapper and service layers, allowing CRUD operations using QueryWrapper without direct SQL involvement.
- **Module Specifics**: Detailed descriptions of `admin` and `common` modules are provided in the accompanying documentation.

## Frontend Code Structure
- **Vue Framework**: The frontend is constructed with Vue, focusing on API interactions and component management.
- **API Integration**: All APIs are defined in `/shms-frontend/src/api/api.js`.
- **Component Architecture**:
  - Main components located in `/src/components`.
  - Admin-specific components in `/src/components/admin`, with code isolation from the main component structure.
