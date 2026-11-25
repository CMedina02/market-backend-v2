# ADA 12: JWT y Spring Security

## Preparación de la base de datos (PostgreSQL)

Ejecuta una vez en la base de datos que usa tu perfil `dev`:

```sql
ALTER TABLE clientes ADD COLUMN IF NOT EXISTS password VARCHAR(60);
CREATE EXTENSION IF NOT EXISTS pgcrypto;
UPDATE clientes SET password = crypt('1234', gen_salt('bf', 10)) WHERE id = 1;
```

El último comando asigna la contraseña `1234` al cliente `id = 1` como hash BCrypt. Cambia el ID por uno que exista. Los clientes sin contraseña no pueden iniciar sesión hasta que se les asigne una. Las altas y cambios de contraseña realizados por la API usan `BCryptPasswordEncoder`. El hash mostrado como pista en la actividad no debe copiarse sin comprobar que `BCryptPasswordEncoder.matches("1234", hash)` devuelve `true`.

## Inicio

Configura la conexión PostgreSQL en `application-dev.properties` (archivo local ignorado por Git), por ejemplo:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/market
spring.datasource.username=postgres
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=validate
```

Define `APP_JWT_SECRET` con al menos 32 bytes y mantenlo fuera del repositorio. En PowerShell:

```powershell
$env:APP_JWT_SECRET = 'cambia-esta-clave-por-una-aleatoria-de-32-bytes-o-mas'
.\gradlew.bat bootRun
```

## Demostración en Swagger

Abre `http://localhost:777/swagger-ui/index.html`:

1. Ejecuta `POST /auth/login` con `{"username":"1","password":"1234"}`. La respuesta contiene `jwt`.
2. Ejecuta `GET /clients` sin autorizar: debe devolver `401`.
3. Pulsa **Authorize**, pega el token (Swagger añade `Bearer`), y repite `GET /clients`: debe devolver `200`.
4. Prueba una contraseña incorrecta: `POST /auth/login` debe devolver `401`.

Graba estos pasos para el video solicitado en la entrega. El token dura 10 horas y el servidor no crea sesiones HTTP.
