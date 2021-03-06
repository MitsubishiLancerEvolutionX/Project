## Перед запуском:
- Установи JDK 11
- Idea-> Plugins -> Lombok -> Install
- Idea -> Settings -> Build, Execution, Deployment -> Compiler -> Annotation Proccesor -> Enable annotation Proccesing

### Git Branching:
- Новые ветки с изменениями должны называться feature/task-name! (пример: feature/telegram-bot)
- Изменения в master только через pull request, в тайтле указать суть изменений, либо название задачи из trello
- Прежде чем начать работу над задачей :
1) git checkout main
2) git pull origin main
3) git checkout -b feature/task-name
По завершении создать пул реквест и прикрепить в trello в комментарий к задаче

Мы инжектим бины через поля (не через сеттер/конструктор)!

## Начало работы
### Добавление переменных окружения (Environment variables):
- Edit Configurations -> Выбираем нужный сервис -> Вставляем нужные значения* в строку как на картинке** -> Apply

*Значения берём из колонки Utils (Notification и orchestrator) в Trello (ссылка внизу)

**Если строка не отображается, то Modify options -> Environment variables (или Alt+E) -> Вставляем данные-> Apply

![image](https://user-images.githubusercontent.com/94536519/160645457-6ae66f72-fe18-4fd9-8a23-01d806b14e70.png)

### Для разворачивания Postgres:
1) Установи Docker Desktop.
2) Перезагрузи компьютер. Запусти Docker Desktop и удали предыдущие версии контейнеров. 
Выполни в терминале IDEA `mvn clean install`. Это очень важный пункт, если у тебя что-то не получается и в интернете 
решения нет, то можешь повторить любые из этих трех магических действий или пункт целиком.
3) В IDEA открой docker-compose-db.yml. Это compose-файл, в котором указаны конфиги для контейнеров.
Для каждого микросервиса разворачивается свой контейнер с Postgres внутри, и для каждого конфиги свои. 
Внимательно смотри на конфиги!
4) Жмем на кнопку docker-compose up рядом с services или открываем вкладку Services 
жмем Docker -> Deploy -> docker-compose-db.yml: Compose Deployment. В Deploy Log должны появиться
надписи наподобие:
    `Starting platform_notification-db_1 ... done`
А в Docker Desktop должна появится группа контейнеров platform. Если нет, то попробуй повторить п.2.
5) Запускай сервисы, которым нужен Postgres.
Для удобства работы с БД в IDEA есть вкладка Database, можешь использовать её, но однажды она чуть не свела меня с ума, 
поэтому предлагаю иногда проверять работоспособность Postgres'ов вручную. Как это делается:
6) Открой из Docker Desktop терминал (CLI) контейнера с нужной тебе БД.
7) Посмотри п.3 и пойми, какие значения ты будешь подставлять вместо POSTGRES_USER, POSTGRES_PASSWORD, POSTGRES_DB в следующих командах. 
В CLI введи:
    psql -U POSTGRES_USER 
Если приглашение к вводу сменилось на `postgres=#` поздравляю, вы зашли в Postgres. 
8) Выполни `\l` для получения списка доступных БД в контейнере, либо же сразу подключись к нужной:
    \c POSTGRES_DB
9) Если приглашение к вводу сменилось на `POSTGRES_DB=#`, то можешь выполнить `\dt` для получения списка таблиц или любой SQL-запрос
И помни, если у тебя что-то не получается - никогда не поздно попробовать повторить п.2!
### Profiles
Мы используем по два профиля для **каждого** микросервиса: **dev** и **prod**. Настройки dev прописаны в папке resources прямо в application.yml, prod в application-prod.yml.
В application.yml выставлен дефолтный профиль (сейчас dev). Можно переключить профиль, поменяв значение `spring.profiles.active` на имя нужного профиля.

**_Важно:_** при использовании prod свойства подключения к БД берутся из переменных окружения, описанных в application-prod.yml. Для **каждого** микросервиса переменные разные и вставлять их надо в конфиг каждого сервиса отдельно. 

## Работа с проектом
### Тестовые данные
Для инициализации тестовых данных необходимо перейти на ендпоинт `/init`
### Поддержание актуальности мастера:
Не забывайте поддерживать актуальность своей ветки
Способ 1 (в идее):  
Git -> Branches -> main -> update  
Git -> Branches -> feature/yourFeature -> checkout  
Git -> Branches -> main -> merge main into feature/yourFeature  
		
Способ 2 (в консоли):  
git checkout main  
git pull origin main  
git checkout feature/yourFeature  
git merge main  

## Тестирование
### Для тестирования через Postman:
    
1) Получаем token, для этого делаем запрос на /login с логином и паролем в формате json
    
![alt](https://i.ibb.co/c3cXS8C/img.png)
    
2) Копируем токен, который пришел в ответе
   
![alt](https://i.ibb.co/qyPJyTg/img-1.png)

3) Открываем вкладку Cookies и нажимаем на кнопку Add cookie
   
![alt](https://i.ibb.co/WsZGvmy/img-2.png) ![alt](https://i.ibb.co/09ms0yd/img-3.png)

4) Вместо Cookie_N=value ставим token=*наш токен*; *эту часть оставляем*
    
![alt](https://i.ibb.co/j8S68TD/img-6.png) ![alt](https://i.ibb.co/7vx42gV/img-5.png)

### Как проверить какая часть кода покрыта тестами:
1) Выполнить `mvn verify`
2) В target найти папку jacoco-report
3) Открыть index.html, в котором можно посмотреть какой % кода покрыт тестами, а так же какая часть кода покрыта, а какая нет.

## Полезные ссылки:
https://trello.com/b/zN2JnsWE/платформа
