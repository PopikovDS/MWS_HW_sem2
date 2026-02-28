# To-do List Manager
MVP on Spring Framework for semester 2

В проекте добавлены профильные конфиги:
- `dev` (подробнее логирование, порт 3000)
- `prod` (production-настройки, порт 8080)

Активировать профиль можно так:

```bash
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=dev"
```