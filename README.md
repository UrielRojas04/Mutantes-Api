# 🧬 Mutant Detector API: Clasificación de ADN

## ⚡ Resumen & Objetivo

Este proyecto **RESTful** es una solución de backend para detectar mutantes analizando secuencias de ADN. La premisa es simple: si Magneto necesita reclutar mutantes, la API debe clasificar las secuencias de ADN recibidas.

El ADN se recibe como un `array de String` representando una matriz cuadrada **(NxN)**, que solo puede contener las bases nitrogenadas **A, T, C, o G**.

### Criterio de Mutante:
Se considera mutante si se encuentran **más de una secuencia de cuatro letras iguales**, ya sea de forma **horizontal, vertical u oblicua**.

---

## 💻 Stack Tecnológico & Arquitectura

La aplicación está diseñada bajo una arquitectura **por capas** en Java con Spring Boot.

### Tecnologías Clave:
| Categoría | Herramientas |
| :--- | :--- |
| **Lenguaje** | Java 17/21 |
| **Framework** | Spring Boot 3.2.0 |
| **Testing** | JUnit 5, Mockito y MockMvc |
| **Base de Datos** | H2 Database (en memoria) |
| **API Specs** | OpenAPI/Swagger para documentación |
| **Contenedorización** | Docker |
| **Cobertura** | JaCoCo para reportes |

### Persistencia y Optimización
El servicio utiliza **H2 en memoria** para almacenar las verificaciones. Para evitar duplicados y mejorar el rendimiento, se emplea un **hash indexado sobre `dna_hash`**.

---

## 🚀 Guía de Ejecución

### Requisitos Previos
* **JDK 17** o superior.
* El **Wrapper de Gradle** está incluido.
* Docker es opcional.

### 1. Ejecución Local (APIs)
La API estará disponible en: `http://localhost:8080`.

| Plataforma | Comando |
| :--- | :--- |
| **Windows (PowerShell)** | `.\gradlew.bat bootRun` |
| **Linux / Mac** | `./gradlew bootRun` |

### 2. Ejecución con Docker
| Paso | Comando |
| :--- | :--- |
| **Construir Imagen** | `docker build -t mutant-api .` |
| **Ejecutar Contenedor** | `docker run -p 8080:8080 mutant-api` |

---

## 📡 Endpoints Principales

### 1. `POST /mutant` 🧪

**Función:** Analiza la secuencia de ADN y clasifica el resultado.

**Ejemplo de Request (Body):**
```json
{
 "dna": [
    "ATGCGA",
    "CAGTGC",
    "TTATGT",
    "AGAAGG",
    "CCCCTA",
    "TCACTG" 
  ]
  
}
```






## 📡 Respuestas del Endpoint `/mutant`

| Código | Estado      | Descripción                           |
|--------|-------------|----------------------------------------|
| ✅ **200** | Mutante     | La secuencia pertenece a un mutante. |
| ❌ **403** | Humano      | La secuencia NO pertenece a un mutante. |
| ⚠️ **400** | Datos inválidos | El ADN no cumple con las validaciones requeridas. |


## 📊 Endpoint: `GET /stats`

Retorna estadísticas de uso del sistema:

```json
{
  "count_mutant_dna": 40,
  "count_human_dna": 100,
  "ratio": 0.4
}

```

## 🧪 Testing & Cobertura

Ejecutar tests:

```bash

  ./gradlew test

```

### Generar reporte JaCoCo

```bash
    ./gradlew test jacocoTestReport

```

## 📘 Swagger – Documentación Online

UI de Swagger en local:  
👉 http://localhost:8080/swagger-ui.html


## ☁️ Despliegue en Render

🔗 **Base URL:**  
https://mutantes-mercadolibre.onrender.com

🔗 **Swagger Online:**  
https://mutantes-mercadolibre.onrender.com/swagger-ui.html
