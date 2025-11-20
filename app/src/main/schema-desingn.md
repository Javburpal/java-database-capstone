## 1. Modelo general del sistema

Flujo básico:

- Un **usuario paciente** se registra / loguea y puede:
  - Reservar turnos con doctores.
  - Ver sus propios turnos.
  - Ver sus propios registros médicos.

- Un **usuario doctor** se loguea y puede:
  - Ver sus turnos asignados.
  - Actualizar el estado de los turnos (ej. atendido / cancelado).
  - Crear registros médicos para sus pacientes.

- Un **usuario administrador** se loguea y puede:
  - Gestionar usuarios (activar / desactivar).
  - Gestionar doctores y sus especialidades.
  - Ver estadísticas generales (según lo que definas en la API).

---

## 2. Diagrama entidad–relación (texto / mermaid)

```mermaid
erDiagram
    USERS ||--o{ PATIENTS : "profile"
    USERS ||--o{ DOCTORS : "profile"
    USERS ||--o{ SESSIONS : "creates"

    PATIENTS ||--o{ APPOINTMENTS : "books"
    DOCTORS ||--o{ APPOINTMENTS : "attends"

    PATIENTS ||--o{ MEDICAL_RECORDS : "has"
    DOCTORS ||--o{ MEDICAL_RECORDS : "writes"
    APPOINTMENTS ||--o{ MEDICAL_RECORDS : "related_to"

    DOCTORS ||--o{ DOCTOR_AVAILABILITY : "has"

    USERS {
        int id PK
        varchar email
        varchar password_hash
        varchar full_name
        enum role
        boolean is_active
        datetime created_at
    }

    PATIENTS {
        int id PK
        int user_id FK
        date birth_date
        varchar gender
        varchar phone
    }

    DOCTORS {
        int id PK
        int user_id FK
        varchar speciality
        varchar license_number
        varchar phone
    }

    DOCTOR_AVAILABILITY {
        int id PK
        int doctor_id FK
        datetime start_time
        datetime end_time
        boolean is_available
    }

    APPOINTMENTS {
        int id PK
        int patient_id FK
        int doctor_id FK
        datetime scheduled_at
        enum status
        text reason
        datetime created_at
    }

    MEDICAL_RECORDS {
        int id PK
        int patient_id FK
        int doctor_id FK
        int appointment_id FK
        text notes
        text diagnosis
        text treatment
        datetime created_at
    }

    SESSIONS {
        int id PK
        int user_id FK
        varchar token
        datetime created_at
        datetime expires_at
    }

