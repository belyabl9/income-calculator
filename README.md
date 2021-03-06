# Інтсрукція з використання

Даний додаток спрощує ведення **обліку доходів**.
Зазвичай IT-фахівці отримують дохід на валютний рахунок, 50% з якого продається автоматично та переводиться на рахунок у грн.
Для ведення обліку доходів треба знати **дату отримання**, **суму**, **валюту** та **курс НБУ на цю дату** (для 50% валюти, що продається за власним бажанням)

Цей додаток дозволяє Вам вводити лише **дату**, **суму** та **валюту**.
**Знаходження курсу НБУ** та **перерахунок в гривню** виконується **автоматично**.
Так як підприємцям необхідно вести облік доходів у **"Книзі обліку доходів"**,
то дуже зручною є можливість отримати її в **готовому електронному вигляді** і лише переписувати вручну.

![img1](https://user-images.githubusercontent.com/6876210/47258813-8e7e3f00-d4a9-11e8-9780-4d3a8fac4bb2.png)

![img2](https://user-images.githubusercontent.com/6876210/47258816-96d67a00-d4a9-11e8-9e0f-49fbbf57a8c4.png)

# Для клієнтів ПриватБанк:
Електронний кабінет "Приват24 для бізнесу" дозволяє створити додаток "Автоклієнт",
який дозволяє отримувати транзакції по Вашим рахнукам за допомогою API.
В налаштуваннях додатку "Автоклієнт" Ви зможете отримати **id** та **token**, які є обов'язковою умовою для використання API.

[Деталі](https://docs.google.com/document/d/e/2PACX-1vS8rx2WKg69o6JvG5L4AhSXcU6vxXcJph6WK84qJcAYDBvsNYEob57jDMQhbosjc9gRS5bOTqTXf0vb/pub")

В даному додатку реалізовано імпорт вхідних транзакцій, використовуючи вище згаданий "Автоклієнт" та API запити.
Це є дуже зручним, так як для імпортування всіх доходів, проведених через рахунки ПриватБанку,
достатньо лише вказати **номер рахунку** та **період часу**.
            

# Увага!

* Даний додаток робить лише read-only API запити.
* Даний додаток знаходиться на етапі тестування. Використання тільки на **власний ризик**!


# Як запустити додаток

* Зклонувати репозиторій
* mvn clean package
* java -jar <шлях_до_створеного_war_файлу>
* Перейти до localhost:8080 у браузері
