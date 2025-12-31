# Proyecto-API — API segura + Frontend (Docker)

Proyecto de **API REST segura** (FastAPI) con frontend web y despliegue en contenedores. Incluye autenticación con JWT, control de acceso por roles (RBAC), conexión a PostgreSQL, integración con Keycloak, soporte MQTT y un conjunto de escaneos automáticos de seguridad con evidencias en `backend/reports/`.

## Documentación adicional
Consulta la documentación en [Informe API Frontend.pdf](https://euneiz-my.sharepoint.com/:b:/p/markel_iturbe/IQCu4VLeu6Q8TbnuebVHPLk8AT3Z9qmh4zV0gnX_TjSv52I?e=HFXshU)

## Componentes
- `backend/`: API en FastAPI (código en `backend/app/`) y su Dockerfile.
- `frontend/`: interfaz web estática (HTML/CSS/JS) y Dockerfile.
- `nginx/`: reverse proxy y certificados de ejemplo.
- `init-db/`: scripts SQL para inicializar la base de datos.
- `keycloak-config/` y `keycloak-certs/`: configuración y certificados para Keycloak.
- `mosquitto/`: configuración del broker MQTT.
- `raspberry_simulator/`: simulador MQTT para pruebas.
- `others/`: documentación (modelo de amenazas, checklist OWASP/CWE, scripts de escaneo, etc.).
## Instalación de Docker (si no lo tienes)

Este proyecto requiere Docker y Docker Compose. Si no los tienes instalados, sigue las instrucciones:

### Linux (Ubuntu/Debian)

```bash
# Actualizar paquetes
sudo apt update
sudo apt install -y ca-certificates curl gnupg

# Añadir repo oficial de Docker
sudo install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
sudo chmod a+r /etc/apt/keyrings/docker.gpg

echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# Instalar Docker
sudo apt update
sudo apt install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

# Añadir usuario al grupo docker (para no usar sudo)
sudo usermod -aG docker $USER
newgrp docker

# Verificar instalación
docker --version
docker compose version

## Requisitos
- **Sistema operativo:** Linux
- **Docker** (versión 20.10+) y **Docker Compose** (v2.0+)
- Permisos de ejecución en scripts: `chmod +x start_compose.sh start_compose_clean.sh`
- Puertos libres: **80, 443**
- **(Opcional)** Python 3.12+ para desarrollo local sin Docker

## Arranque rápido (Docker Compose)
Desde la raíz del repositorio:

> Los scripts están en la raíz del repositorio: `start_compose.sh` y `start_compose_clean.sh`. Ajusta permisos si hace falta (`chmod +x`).  
> El script `start_compose_clean.sh` borra datos previos (DB, Keycloak, Mosquitto) y simula un entorno limpio.

**Archivo de configuración requerido:** `backend/.env.production`

# Primer arranque o entorno limpio
```bash
./start_compose_clean.sh
```
# Arranques posteriores (mantiene datos)
```bash
./start_compose.sh
```
## Primera ejecución y credenciales de administrador

### Usuario administrador automático

En el **primer arranque** (base de datos limpia), el sistema genera automáticamente un usuario administrador:

- **Username:** `admin`  
- **Email:** `admin@kalimotxo.local`  
- **Password:** Se genera aleatoriamente (16 caracteres seguros con mayúsculas, minúsculas, dígitos y símbolos)

**La contraseña se muestra UNA ÚNICA VEZ por consola durante el arranque y se elimina del sistema inmediatamente después. Guardala de forma segura.**

### Inicialización paso a paso

1. **Ejecuta el script de inicio:**
 '' ./start_compose_clean.sh ''


2. **Observa los logs del contenedor `kalimotxo_api`**. Verás algo como:
   ```
   ================================================================================
   🔐 CONTRASEÑA INICIAL DEL ADMINISTRADOR
   ================================================================================
      Username: admin
      Email:    admin@kalimotxo.local
      Password: Xy8#kL2mP9@qR5tN
   ================================================================================
   ⚠️  GUARDA ESTA CONTRASEÑA - No se volverá a mostrar
   ================================================================================
   ```

3. **Guarda inmediatamente la contraseña.** El archivo temporal `/tmp/admin_initial_password.txt` se elimina automáticamente después de mostrarse.

### ¿Qué hace el script de inicialización?

El script `backend/app/core/auth/providers/init_keycloak.py` (se ejecuta automáticamente al arrancar el contenedor API):

1. Espera a que Keycloak esté disponible
2. Crea o verifica el cliente OAuth2 `kalimotxo-api-client` en Keycloak
3. Si el usuario `admin` no existe en la base de datos:
   - Genera una contraseña aleatoria segura mediante `secrets` de Python
   - Crea el usuario en Keycloak con rol `Administrador`
   - Sincroniza el usuario con PostgreSQL
   - Escribe temporalmente la contraseña en `/tmp/admin_initial_password.txt` (permisos 600)
   - El script `start_compose.sh` detecta el archivo, muestra la contraseña por consola y la elimina

**Esta inicialización solo ocurre si el usuario `admin` no existe previamente en la base de datos.**

### Si perdiste la contraseña inicial

**Opción A - Reiniciar todo (borra todos los datos):**
```bash
./start_compose_clean.sh
```

**Opción B - Resetear contraseña en Keycloak:**
1. Accede a Keycloak Admin Console: `https://localhost/auth`
2. Credenciales de admin de Keycloak (definidas en `backend/.env.production`):
   - Usuario: valor de `KEYCLOAK_ADMIN` (por defecto `admin`)
   - Contraseña: valor de `KEYCLOAK_ADMIN_PASSWORD`
3. Navega a: **Realm `kalimotxo`** → **Users** → Busca `admin` → **Credentials** → **Reset Password**
4. Establece nueva contraseña (debe cumplir política: 12+ caracteres, mayúsculas, minúsculas, dígitos, símbolos)

## Autenticación en Swagger y pruebas de API

El Swagger (`https://localhost/api/docs`) requiere autenticación mediante **Bearer Token JWT** obtenido de Keycloak.

NO uses el `SECRET_KEY` del archivo `.env.production` como token. Debes obtener un JWT válido desde el endpoint de login.

### Cómo obtener un token JWT

#### Opción 1: Desde Swagger UI

1. Abre Swagger: `https://localhost/api/docs`
2. Localiza el endpoint `POST /auth/login`
3. Haz clic en **"Try it out"**
4. Ingresa las credenciales del administrador en el body:
   ```json
   {
     "username": "admin",
     "password": "LA_CONTRASEÑA_QUE_GUARDASTE"
   }
   ```
5. Haz clic en **"Execute"**
6. Copia el valor de `access_token` de la respuesta JSON
7. Haz clic en el botón **"Authorize"** (arriba a la derecha en Swagger)
8. Pega el token en el campo `value` (solo el token, sin escribir "Bearer")
9. Haz clic en **"Authorize"** y luego **"Close"**

Ahora puedes probar cualquier endpoint protegido.

#### Opción 2: Desde cURL (línea de comandos)

**Obtener token:**
```bash
curl -X POST https://localhost/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "TU_CONTRASEÑA_AQUÍ"
  }' \
  --insecure
```

**Usar el token en peticiones:**
```bash
# Reemplaza YOUR_ACCESS_TOKEN con el token obtenido
curl -X GET https://localhost/api/users \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
  --insecure
```

### Duración de los tokens

- **Access token:** 5 minutos 
- **Refresh token:** 30 minutos

Si el access token expira, usa el endpoint `/auth/refresh` con el `refresh_token` para obtener un nuevo par de tokens sin necesidad de volver a hacer login.

## Backend (API)

Estructura principal:
- `backend/app/main.py`: punto de entrada de la aplicación.
- `backend/app/routers/`: endpoints organizados por dominio (auth, users, vulnerabilities, mqtt).
- `backend/app/core/`: configuración, validación, headers de seguridad, autenticación, base de datos, etc.
- `backend/test/`: suite de tests.

### Documentación OpenAPI

Una vez levantado el backend, la documentación interactiva de la API está disponible en:
- **Swagger UI:** `https://localhost/api/docs`
- **ReDoc:** `https://localhost/api/redoc`

### Endpoints principales

- `/health` — Health check (no requiere autenticación)
- `/auth/*` — Autenticación y gestión de sesiones (register, login, refresh, logout, me)
- `/users/*` — Administración de usuarios (requiere rol Administrador)
- `/vulnerabilities/*` — Gestión de vulnerabilidades (requiere rol Administrador o Moderador)

## Frontend

El frontend es una aplicación web estática ubicada en:
- `frontend/app/` (HTML/CSS/JS)

Páginas incluidas:
- `index.html` — Página de inicio
- `login.html` — Formulario de login
- `register.html` — Formulario de registro (solo administradores)
- `dashboard.html` — Panel principal (HMI, gestión de usuarios y vulnerabilidades)

**Acceso:** `https://localhost`

## Seguridad y evidencias

El proyecto incluye escaneos automáticos de seguridad. Los reportes se guardan en:

- `backend/reports/`

### Herramientas utilizadas

- **pip-audit:** Detección de vulnerabilidades en dependencias Python
- **Bandit:** Análisis estático de seguridad del código Python (SAST)
- **Semgrep:** Análisis estático basado en reglas de seguridad (SAST)
- **Trivy:** Escaneo de imágenes Docker, filesystem, configuración y secretos

### Ejecutar escaneos localmente

Los scripts de auditoría están en:
- `backend/reports/auditory_script.sh` — Script principal de auditoría
- `others/scripts/` — Scripts individuales (Bandit, Semgrep, Trivy, ZAP baseline, etc.)

Ejemplo de ejecución:
```bash
cd backend/reports
./auditory_script.sh
```

## CI/CD (GitHub Actions)

El repositorio incluye un workflow de seguridad (`.github/workflows/security.yml`) que:
- Se ejecuta automáticamente en `push` y `pull_request` sobre las ramas `main` y `develop`
- Ejecuta todos los escaneos de seguridad
- Genera reportes en `backend/reports/`
- Sube los reportes como artefactos de GitHub Actions

## Estructura del repositorio (resumen)

```
.
├── backend/           # API FastAPI + Dockerfile + tests + reports
├── frontend/          # Web estática + Dockerfile
├── nginx/             # Reverse proxy + certificados + configuración
├── init-db/           # Scripts SQL de inicialización
├── keycloak-config/   # Configuración de Keycloak (realm-export.json)
├── keycloak-certs/    # Certificados para Keycloak
├── mosquitto/         # Configuración del broker MQTT
├── raspberry_simulator/ # Simulador de cliente MQTT
├── others/            # Documentación (modelo de amenazas, checklist, scripts)
├── docker-compose.yml # Orquestación de contenedores
├── start_compose.sh   # Script de arranque (mantiene datos)
└── start_compose_clean.sh # Script de arranque limpio (borra datos)
```
