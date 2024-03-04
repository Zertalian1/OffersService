Запуск
Порядок запуска
1) zookeeper, kafka, kafdrop, postgresql из docker-compose.yml
2) generatingOffers
3) showingOffers

Если не запустить сервис генерации отчетов хоть раз до сервиса показа, то тк hibernate толко валидирует данные, будет получена ошибка

Как выглядит архитектура:
![image](https://github.com/Zertalian1/OffersService/assets/91644941/96f1c9ef-8acb-4544-b26a-45ec407c0f3c)

Дополнительные моменты:
1) Авторизация реализована в сервисе показа отчётов
2) Шаблоны хранятся в формате jrxml, тестовый шаблон сгенерирован в Jaspersoft Studio
