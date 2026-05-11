# Skok na Hajs

Nowoczesna aplikacja bankowa zbudowana w Spring Boot + React.

## Stack

**Backend:** Java 21, Spring Boot 3.5, Spring Security, JWT, PostgreSQL (NeonDB), JPA, Gradle
**Frontend:** React 19, TypeScript, Vite, Tailwind CSS, shadcn/ui, React Router

## Uruchomienie lokalne

### Wymagania
- Java 21
- Node.js 20+
- konto NeonDB (lub lokalny PostgreSQL)

### Backend
1. Skopiuj `backend/src/main/resources/application-local.properties.example` jako `application-local.properties`
2. Uzupełnij dane bazy danych i JWT secret
3. Uruchom: `./gradlew bootRun`

### Frontend
\`\`\`bash
cd frontend
npm install
npm run dev
\`\`\`

Backend działa na `http://localhost:8080`, frontend na `http://localhost:5173`.

## Struktura projektu

\`\`\`
SkokNaHajs/
├── backend/        # Spring Boot REST API
├── frontend/       # React SPA
└── .github/        # CI/CD workflows
\`\`\`

## License

MIT