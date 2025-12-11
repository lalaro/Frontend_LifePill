# Frontend_LifePill

**LifePill** es una aplicación móvil de gestión nutricional desarrollada en **Kotlin** con **Jetpack Compose** que ayuda a los usuarios a llevar un registro de sus comidas diarias, recibir recomendaciones personalizadas de IA y alcanzar sus objetivos de salud.

## Características Principales

### Para Usuarios
- **Registro de Comidas**: Registra desayuno, almuerzo y cena con opción de búsqueda de alimentos
- **Base de Datos de Alimentos**: Acceso a una amplia base de alimentos con información calórica
- **Asistente IA Nutricional**: Chat inteligente que proporciona recomendaciones nutricionales personalizadas
- **Seguimiento de Calorías**: Monitoreo diario de ingesta calórica con progreso visual
- **Perfil Personalizado**:
    - Información personal (edad, peso, estatura)
    - Cálculo automático de IMC
    - Objetivos personalizados (meta diaria, objetivo de peso)
    - Estadísticas de uso (días activos, comidas registradas, consultas IA)
- **Múltiples Formas de Registro**:
    - Captura de foto de alimentos
    - Búsqueda en base de datos
    - Entrada manual de texto

### Para Administradores
- **Panel de Control**: Vista general de estadísticas del sistema
- **Gestión de Usuarios**:
    - Visualización de todos los usuarios registrados
    - Información detallada de cada usuario
    - Eliminación de usuarios con confirmación
- **Estadísticas del Sistema**:
    - Usuarios totales y activos
    - Comidas registradas
    - Promedios de uso
    - Estado del sistema (Servidor, Base de Datos, IA)

## Diseño UI/UX

### Paleta de Colores
- **Primary**: `#007bff` (Azul)
- **Background**: `#1E1E1E` (Gris oscuro)
- **Surface**: `#16213E` (Azul oscuro)
- **OnSurface**: `#B8BCC8` (Gris claro)

### Componentes Visuales
- Diseño moderno con modo oscuro
- Cards con bordes redondeados
- Navegación inferior intuitiva
- Animaciones fluidas
- Feedback visual en todas las interacciones

## Arquitectura

### Estructura del Proyecto
```
com.escuelaing.edu.lifepill/
├── ui/
│   ├── screens/
│   │   ├── OnBoardingScreen.kt
│   │   ├── loginScreen/
│   │   │   ├── LoginScreens.kt
│   │   │   └── forgotPasswordScreen/
│   │   │       ├── ForgotPasswordScreen.kt
│   │   │       ├── VerificationScreen.kt
│   │   │       ├── ResetPasswordScreen.kt
│   │   │       └── PasswordSuccessScreen.kt
│   │   └── homeScreen/
│   │       ├── AdminHomeScreen.kt
│   │       └── UserHomeScreen/
│   │           ├── UserHomeScreen.kt
│   │           ├── FoodRegisterScreen.kt
│   │           ├── AIAssistantScreen.kt
│   │           └── UserProfileScreen.kt
│   └── theme/
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
└── MainActivity.kt
```

## Tecnologías Utilizadas

- **Lenguaje**: Kotlin
- **UI Framework**: Jetpack Compose
- **Material Design**: Material 3
- **Navegación**: Compose Navigation
- **Arquitectura**: MVVM (preparado para implementación)
- **Build System**: Gradle

## Requisitos

- Android Studio Hedgehog | 2023.1.1 o superior
- Android SDK 24 o superior
- Kotlin 1.9.0 o superior
- Gradle 8.0 o superior

## Instalación

1. Clona el repositorio:
```bash
git clone https://github.com/tuusuario/lifepill.git
```

2. Abre el proyecto en Android Studio

3. Sincroniza las dependencias de Gradle

4. Ejecuta la aplicación en un emulador o dispositivo físico

## Credenciales de Prueba

### Usuario Normal
- Email: `user@email.com`
- Contraseña: `user123`

### Administrador
- Email: `admin@gmail.com`
- Contraseña: `admin123`

## Pantallas

### Flujo de Usuario
1. **OnBoarding**: Pantallas de bienvenida con carrusel
2. **Login/Registro**: Autenticación con validación de campos
3. **Home**: Resumen diario con progreso de calorías
4. **Registro de Comidas**: Búsqueda y selección de alimentos
5. **Asistente IA**: Chat conversacional para consultas nutricionales
6. **Perfil**: Información personal y estadísticas

### Flujo de Administrador
1. **Panel de Control**: Estadísticas generales
2. **Gestión de Usuarios**: Lista y eliminación de usuarios

## Características Destacadas

### Búsqueda de Alimentos
- Base de datos integrada con información calórica
- Filtrado en tiempo real
- Selección múltiple de alimentos
- Visualización de productos seleccionados

### Asistente IA
- Recomendaciones personalizadas
- Interfaz de chat fluida
- Scroll automático a nuevos mensajes

### Cálculo de IMC
- Cálculo automático basado en peso y estatura
- Categorización (Bajo peso, Normal, Sobrepeso, Obesidad)
- Actualización dinámica

## Seguridad

- Validación de campos en tiempo real
- Confirmación para acciones críticas (eliminación)
- Manejo seguro de sesiones
- Políticas de privacidad integradas

## Estadísticas

El sistema rastrea:
- Días activos del usuario
- Comidas registradas
- Consultas realizadas al asistente IA
- Progreso hacia objetivos

## Objetivos del Proyecto

LifePill busca facilitar el seguimiento nutricional mediante:
- **Simplicidad**: Interfaz intuitiva y fácil de usar
- **Personalización**: Adaptación a objetivos individuales
- **Inteligencia**: Recomendaciones basadas en hábitos
- **Motivación**: Visualización clara del progreso

## Contribuciones

Las contribuciones son bienvenidas. Por favor:
1. Haz fork del proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## Equipo

Desarrollado por estudiantes de la Escuela Colombiana de Ingeniería Julio Garavito.
1. Laura Valentina Rodríguez Ortegón
2. Manuel Felipe Barrera Barrera
3. Juan Esteban Cancelado Sanchez
4. David Alfonso Barbosa Gomez

---

**LifePill** - Tu salud en tus manos 💚
