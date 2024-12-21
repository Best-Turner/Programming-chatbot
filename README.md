# Чатбот для изучения программирования Java

Этот проект представляет собой чат-бота, разработанного для помощи в изучении программирования. Приложение использует Spring Framework с AOP, а также Swing для графического пользовательского интерфейса (GUI). В проекте реализован паттерн проектирования "Состояние", что позволяет управлять поведением чат-бота в зависимости от его текущего состояния.

## Описание

Чат-бот может взаимодействовать с пользователем, предоставляя информацию, предлагая учебные материалы по различным темам. Приложение имеет графический интерфейс, разработанный с использованием Swing.

## Особенности

- **Spring Framework**: Используется для управления зависимостями и конфигурацией приложения.
- **Spring AOP**: Реализует аспектно-ориентированное программирование для логирования и обработки событий.
- **Swing**: Обеспечивает графический интерфейс для взаимодействия с пользователем.
- **Паттерн "Состояние"**: Позволяет чат-боту изменять свое поведение в зависимости от состояния, в котором он находится.

## Установка

1. Убедитесь, что у вас установлены [Java JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) и [Maven](https://maven.apache.org/download.cgi).
2. Клонируйте репозиторий:

   ```bash
   git clone https://github.com/Best-Turner/Programming-chatbot.git
Перейдите в директорию проекта:
cd Programming-chatbot

3. Соберите проект с помощью Maven:

mvn clean install

4. Запустите приложение:

java -jar Programming-chatbot-1.0-SNAPSHOT.jar

### Использование

После запуска приложения вы увидите графический интерфейс, в котором сможете начать взаимодействие с чат-ботом. Вы можете задавать вопросы и получать ответы по различным темам программирования.