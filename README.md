# 📘 Literalura

_Challenge del curso Oracle ONE G8 con Alura Latam: consumo de API + persistencia con Spring Boot_

Literalura es una aplicación de consola desarrollada en Java que permite buscar libros desde la API pública [Gutendex](https://gutendex.com/) y almacenarlos en una base de datos PostgreSQL 🐘. Se trabajó con entidades relacionadas (Libro, Autor, Idioma), y consultas tanto derivadas como personalizadas usando `@Query` con JPQL.

Este fue el proyecto más desafiante hasta ahora, por la combinación de:

- Consumo y deserialización de una API externa.
- Relacionamiento de entidades con JPA.
- Persistencia y recuperación de datos con consultas complejas.
- Integración completa usando Spring Boot.

## 🚀 Cómo ejecutar

1. Crea una base de datos PostgreSQL llamada `literalura`.
2. Configura las credenciales en `application.properties`:
   ```properties
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
3. Abre el proyecto en IntelliJ y ejecuta:
   `com.alura.literalura.LiteraluraApplication`
   - Esto iniciará el contexto de Spring y habilitará el menú interactivo desde la clase Principal.java.

## 📚 Funcionalidades principales

- Búsqueda de libros por título consumiendo la API pública Gutendex.
- Guardado automático en la base de datos de libros, autores e idiomas nuevos.
- Listado de libros, autores y filtrado por idioma o autores vivos en un año dado.
- Uso de consultas derivadas (`findBy...`) y consultas personalizadas con `@Query` en los repositorios.
- Manejo adecuado de relaciones entre entidades:  
  - Un libro tiene un autor y un idioma.  
  - Un autor puede tener múltiples libros.  
- Clase principal `Principal.java` anotada con `@Component` para que Spring la gestione y permita la inyección de dependencias.
- El proyecto usa `application.properties` para configuración de la conexión a PostgreSQL y `spring.jpa.hibernate.ddl-auto=update` para crear/actualizar tablas automáticamente.

## 🛠️ Tecnologías y librerías usadas

- ☕ Java 17
- 🐘 PostgreSQL — base de datos relacional
- 🐘 PostgreSQL JDBC Driver
- 🐼 Spring Boot 3.x (Spring Data JPA)
- 📦 Gson para convertir JSON a objetos Java
- 📡 Consumo HTTP sencillo con `RestTemplate` o cliente HTTP propio
- 🧪 JPQL y anotaciones `@Query` para consultas personalizadas
- 🛠️ IntelliJ IDEA como IDE principal
## 🧑‍💻 Autor

**Lic. Guillermo Dardón**  
Abogado en Derecho Internacional y Mercantil 
-Entusiasta en la aplicación de la programación software y la IA en el derecho Mexicano 
-Desarrollador en formación - Oracle ONE G8 Alura LATAM  
[GitHub: @Attorneyguillermodardon](https://github.com/Attorneyguillermodardon)

![Mi Foto de Perfil](https://avatars.githubusercontent.com/u/196573116?s=400&u=ddd3ae9a0263d494b7ecb6b0db3dfed8ce292dee&v=4)

---

## 📌 Notas finales

Este fue el proyecto más complejo y dificil hasta el momento (aunque falta uno más). Integró consumo de API externa, persistencia con Spring Data JPA, relaciones entre entidades y consultas personalizadas en base de datos PostgreSQL 🐘.

Lo ejecutamos iniciando la clase `LiteraluraApplication.java`, que arranca el contexto de Spring y permite la ejecución de la clase `Principal.java`, donde se encuentra la lógica del menú.

Con este proyecto consolido mis conocimientos prácticos en Java y el ecosistema Spring Boot.

---

