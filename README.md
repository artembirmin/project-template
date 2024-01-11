# Шаблоны по веткам

- [**tabs_orbit_compose_coroutines_room_retrofit**](https://github.com/artembirmin/project-template-android)
    
    Шаблон Single Activity приложения с [Bottom Navigation Tabs](https://m3.material.io/components/navigation-bar/overview).
    
    Архитектура Presentation слоя — MVI (MVVM+) в реализации [Orbit](https://orbit-mvi.org/).  Вёрстка на [Jetpack Compose](https://developer.android.com/jetpack/compose) внутри Фрагментов. 
    
    Используемые технологии: [Coroutines](https://developer.android.com/kotlin/coroutines), [Dagger2](https://dagger.dev/), [Room](https://developer.android.com/training/data-storage/room), [Retrofit](https://square.github.io/retrofit/), [Cicerone](https://github.com/terrakok/Cicerone).
    
    В ветке [demo/tabs_orbit_compose_coroutines_room_retrofit](https://github.com/artembirmin/project-template-android/tree/demo/tabs_orbit_compose_coroutines_room_retrofit) представлен пример работы навигации в приложении.
    

- [**orbit_compose_coroutines_room_retrofit**](https://github.com/artembirmin/project-template-android/tree/orbit_compose_coroutines_room_retrofit)
    
    Шаблон Single Activity приложения с фрагментами без [Bottom Navigation Tabs](https://m3.material.io/components/navigation-bar/overview). В остальном то же самое, что [tabs_orbit_compose_coroutines_room_retrofit](https://github.com/artembirmin/project-template-android).
  Используемые технологии: [Coroutines](https://developer.android.com/kotlin/coroutines), [Dagger2](https://dagger.dev/), [Room](https://developer.android.com/training/data-storage/room), [Retrofit](https://square.github.io/retrofit/), [Cicerone](https://github.com/terrakok/Cicerone).
    

- **(Depricated)** [**mvp_simple_fragments_room_retrofit**](https://github.com/artembirmin/project-template-android/tree/mvp_simple_fragments_room_retrofit)
    
    Шаблон Single Activity приложения на Фрагментах. 
    
    Архитектура Presentation слоя — MVP в реализации [Moxy](https://github.com/Arello-Mobile/Moxy). Вёрстка на XML с [Data Binding](https://developer.android.com/topic/libraries/data-binding). 
    
    Используемые технологии: [RxJava3](https://github.com/ReactiveX/RxJava), [Dagger2](https://dagger.dev/), [Room](https://developer.android.com/training/data-storage/room), [Retrofit](https://square.github.io/retrofit/), [Cicerone](https://github.com/terrakok/Cicerone).
    

- **(Depricated) [mvp_simple_fragments](https://github.com/artembirmin/project-template-android/tree/mvp_simple_fragments)**
    
    Шаблон Single Activity приложения на Фрагментах без Data слоя. Учебный.
    
    Архитектура Presentation слоя — MVP в реализации [Moxy](https://github.com/Arello-Mobile/Moxy). Вёрстка на XML с [Data Binding](https://developer.android.com/topic/libraries/data-binding). 
    
    Используемые технологии: [RxJava3](https://github.com/ReactiveX/RxJava), [Dagger2](https://dagger.dev/), [Cicerone](https://github.com/terrakok/Cicerone).
    

# Прочее

Для исследования архитектурных подходов для Presentation слоя было реализовано небольшое приложение со счетчиком. По выбранному числу запрашивался факт о нем с https://numbersapi.com/.

[sandbox/mvvm_reducer_counter](https://github.com/artembirmin/project-template-android/tree/sandbox/mvvm_reducer_counter) — MVVM с собственной релизацией Редьюсера во ViewModel.

[sandbox/mvvm_orbit_counter](https://github.com/artembirmin/project-template-android/tree/sandbox/mvvm_orbit_counter) — MVI (MVVM+) в реализации фреймворка [Orbit](https://orbit-mvi.org/).

[sandbox/mvikotlin_counter](https://github.com/artembirmin/project-template-android/tree/sandbox/mvikotlin_counter) — MVI в реализации фреймворка [MVIKotlin](https://arkivanov.github.io/MVIKotlin/).
