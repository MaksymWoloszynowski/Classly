# Classly - Frontend

## Overview

React + TypeScript frontend for the Classly school management system. Talks to the backend exclusively through the API Gateway (`http://localhost:8080`), authenticated via httpOnly JWT cookies. Available in two languages - Polish and English.

## Technologies

[![React](https://img.shields.io/badge/React-19-61DAFB?style=for-the-badge&logo=react&logoColor=black)](https://react.dev/)
[![TypeScript](https://img.shields.io/badge/TypeScript-6-3178C6?style=for-the-badge&logo=typescript&logoColor=white)](https://www.typescriptlang.org/)
[![Vite](https://img.shields.io/badge/Vite-B73BFE?style=for-the-badge&logo=vite&logoColor=white)](https://vitejs.dev/)

## Quick Start

### Prerequisites

- **Node.js 20+**
- The backend (`api-gateway` + all domain services) running - see the main repo's `README.md`

### 📥 Installation

```bash
git clone https://github.com/MaksymWoloszynowski/Classly.git
cd frontend
npm install
```

### Configuration

Create `frontend/.env.local` or copy the example file:

```bash
cp .env.example .env.local
```

```env
VITE_API_BASE_URL=http://localhost:8080
```

For Docker Compose, the production build uses `VITE_API_BASE_URL=/`. Requests are then proxied by the frontend Nginx to the internal API Gateway.

### Running

```bash
npm run dev
```

The app will be available at `http://localhost:5173`.

### Building

```bash
npm run build
```

Output is written to `dist/`.

## Authentication Notes

- Login/register/refresh calls go to `/auth/*`.
- Access tokens expire after 15 minutes - a response interceptor automatically calls `/auth/refresh` on a `401` and retries the original request once.
- The current user's profile is available via `GET /api/my-profile`.

## Project Structure

```
frontend/
├── src/
│   ├── api/            # API client and interceptors
│   ├── components/     # Shared and feature-specific UI components
│   ├── context/        # React contexts
│   ├── hooks/          # Shared React hooks
│   ├── i18n/           # Polish and English translations
│   ├── layouts/        # Page layouts
│   ├── pages/          # Application pages
│   ├── styles/         # Global styles
│   ├── types/          # Shared TypeScript types
│   ├── utils/          # Shared utilities
│   └── main.tsx
├── public/
├── index.html
├── vite.config.ts
└── package.json
```