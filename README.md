# HotelSpringBoot

API REST de reservas de hotel construida con Spring Boot 3, Java 17, Spring Data JPA, PostgreSQL y MapStruct.

## Requisitos

- Java 17
- PostgreSQL
- Maven (o el wrapper incluido: `mvnw.cmd`)

Configura la conexión a PostgreSQL en `src/main/resources/application.properties` antes de iniciar la aplicación.

## Ejecutar la aplicación

Desde la raíz del proyecto:

```powershell
.\mvnw.cmd spring-boot:run
```

La API queda disponible por defecto en `http://localhost:8080`.

## Pruebas avanzadas

Las cuatro solicitudes ejecutables están en [`solicitudes-avanzadas.http`](solicitudes-avanzadas.http). Se pueden ejecutar desde IntelliJ IDEA u otro cliente compatible con archivos HTTP. Antes de ejecutar las pruebas 3 y 4, reemplaza `REEMPLAZAR_POR_UUID_DE_CLIENTE` por el UUID de un cliente existente.

### 1. Creación de Suite Presidencial

`POST /api/habitaciones/suites`

```json
{
  "numero": "P05-501",
  "precioPorNoche": 350.0,
  "capacidadMaxima": 2,
  "jacuzziPrivado": true,
  "incluyeMayordomo": true
}
```

**Resultado esperado:** `201 Created`, encabezado `Location` con la URL de la habitación y respuesta con el ID generado, `tipo: "SUITE"` y los atributos propios de la suite.

### 2. Consulta polimórfica

`GET /api/habitaciones`

**Resultado esperado:** `200 OK` con un arreglo JSON que puede contener ambas implementaciones. Los elementos estándar incluyen `tipo: "ESTANDAR"` y `camasIndividuales`; las suites incluyen `tipo: "SUITE"`, `incluyeMayordomo` y `jacuzziPrivado`. Ambas respuestas incluyen los atributos comunes de habitación.

### 3. Resumen Ejecutivo del Cliente

`GET /api/clientes/{id}/resumen`

**Resultado esperado:** `200 OK` con los datos del cliente, `totalReservasRealizadas`, `montoTotalGastado` calculado a partir de sus reservas y `reservasRecientes` con los datos de cada reserva y habitación. Los elementos de reserva no vuelven a incluir al cliente, evitando referencias cíclicas.

Ejemplo de forma de respuesta:

```json
{
  "id": "UUID_DEL_CLIENTE",
  "nombre": "Nombre del cliente",
  "email": "cliente@example.com",
  "activo": true,
  "penalizaciones": 0,
  "totalReservasRealizadas": 1,
  "montoTotalGastado": 700.0,
  "reservasRecientes": [
    {
      "idReserva": "UUID_DE_RESERVA",
      "numeroHabitacion": "P05-501",
      "fechaInicio": "2026-10-01T15:00:00",
      "fechaFin": "2026-10-03T11:00:00",
      "estado": "PENDIENTE",
      "costoTotal": 700.0
    }
  ]
}
```

Los valores del ejemplo son ilustrativos; la respuesta real depende de los datos guardados.

### 4. Actualización selectiva (PATCH)

`PATCH /api/clientes/{id}`

```json
{
  "nombre": "Nuevo Nombre Modificado"
}
```

**Resultado esperado:** `200 OK` con el cliente actualizado. El email omitido no cambia; tampoco cambian `activo` ni `penalizaciones`, ya que esos campos no forman parte del DTO de actualización. Enviar `null` para `nombre` o `email` también conserva el valor existente.

## Ejecutar pruebas automatizadas

```powershell
.\mvnw.cmd test
```
