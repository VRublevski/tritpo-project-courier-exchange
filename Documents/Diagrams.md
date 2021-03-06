# UML Диаграммы
1. [Диаграмма вариантов использования](#1)<br>
1.1 [Актёры](#1.1)<br>
1.2 [Варианты использования](#1.2)<br>
1.2.1 [Аутентификация](#1.2.1)<br>
1.2.2 [Регистрация](#1.2.2)<br>
1.2.3 [Получение списка доступных курьеров](#1.2.3)<br>
1.2.4 [Получение списка доставок](#1.2.4)<br>
1.2.5 [Запрос на изменение статуса доставки](#1.2.5)<br>
1.2.6 [Запрос на изменение личных данных](#1.2.6)<br>
1.2.7 [Запрос на осуществление доставки](#1.2.7)<br>
1.2.8 [Получение списка клиентов](#1.2.8)<br>
1.2.9 [Удаление пользователя](#1.2.9)<br>
1.2.10 [Получение подробной информации о курьере](#1.2.10)<br>

2. [Диаграммы активности](#2)<br>
2.1 [Регистрация](#2.1)<br>
2.2 [Запрос на изменение статуса доставки](#2.2)<br>
2.3 [Запрос на осуществление доставки](#2.3)<br>
2.4 [Запрос на изменение личных данных](#2.4)<br>
2.5 [Получение подробной информации о курьере](#2.5)<br>

3. [Диаграмма последовательности](#3)  
3.1 [Регистрация](#3.1)<br>
3.2 [Запрос на изменение статуса доставки](#3.2)<br>
3.3 [Запрос на осуществление доставки](#3.3)<br>
3.4 [Запрос на изменение личных данных](#3.4)<br>
3.5 [Получение подробной информации о курьере](#3.5)<br>

4. [Диаграммы состояния](#4)<br>
4.1 [Клиент](#4.1)<br>
4.2 [Курьер](#4.2)<br>
4.3 [Доставка](#4.3)<br>

5. [Диаграммы классов](#5)<br>

6. [Совмещённая диаграмма компонентов и развёртывания](#6)<br>

<a name="1"/>

### 1. Диаграмма вариантов использования 
Диаграмма вариантов использования представляет собой следующую диаграмму:
![alt text](https://github.com/VRublevski/tritpo-project-courier-exchange/blob/master/Images/Diagrams/Use-Cases/use-cases.jpg)

<a name="1.1"/>

#### 1.1 Актёры
Актёр | Описание
--- | ---
Гость|Человек, не зарегистрированный в системе. Имеет доступ только к главной странице веб-приложения и формам регистрации.
Клиент|Человек, аутентифицированный в системе, использующий приложение для осуществления запросов на доставку различных грузов. Имеет доступ к данным о курьерах. Может осуществлять запросы на обновление личных данных. Является инициатором процесса доставки. 
Курьер|Человек, аутентифицированный в системе, использующий приложение для выполнения заказов на доставку, может осуществлять запросы на обновление личных данных.
Администратор|Человек, аутентифицированный в системе. Имеет доступ к данным о всех зарегестрированных пользователях. Обладает возможностью удаления пользователей.

<a name="1.2"/>

#### 1.2 Варианты использования

<a name="1.2.1"/>

##### 1.2.1 Аутентификация
**Описание.** Вариант использования **"Аутентификация"** позволяет посетителю аутентифицироваться в приложении.  

**Основной поток:**
1. Посетитель нажимает кнопку "Sign in" на главной странице приложения.
2. Приложение переходит на страницу входа в систему.
3. Посетитель вводит "Логин" и "Пароль".
4. Посетитель нажимает клавишу "Вход".
5. Приложение проверяет соответствие введенных данных с базой данных. При неверных данных приложение выводит сообщение об ошбике, посетитель повторяет действия 3 и 4.  
6. Приложение направляет пользователя на страницу "Личный кабинет".
7. Конец.

<a name="1.2.2"/>

##### 1.2.2 Регистрация
**Описание.** Вариант использования **"Регистрация"** позволяет посетителю создать свою учетную запись в системе.  

**Основной поток:**
1. Посетитель нажимает кнопку "Регистрация" на главной странице приложения.
2. Приложение переходит на страницу входа в систему.
3. Посетитель вводит "Логин", "Имя","Фамилия", "Пароль" и "Роль" (курьер или клиент).
4. Посетитель нажимает кнопку "Зарегистрироваться".
5. Приложение проверяет валидность и уникальность данных. Если данные не прошли валидацию приложение показывает сообщение об ошибке, далее посетитель повторяет дейстия 3 и 4.
6. Приложение добавляет нового пользователя в систему.
7. Приложение направляет пользователя на страницу "Личный кабинет".
8. Конец.

<a name="1.2.3"/>

##### 1.2.3 Получение списка доступных курьеров
**Предусловие.** Выполнение прецедента **Аутентификация**.  
**Описание.** Вариант использования **"Получение списка доступных курьеров"** позволяет пользователю просматривать список доустпных курьеров для доставки груза.  

**Основной поток:**
1. Клиент нажимает кнопку "Список курьеров" на главной панели веб-приложения.
2. Приложение осуществляет запрос базы данных с целью получения списка курьеров.
3. Приложение формирует страницу ответа пользователю на основе полученных данных.
4. Приложение отправляет сформированную страницу пользователю.
5. Конец.

<a name="1.2.4"/>

##### 1.2.4 Получение списка доставок
**Предусловие.** Выполнение прецедента **Аутентификация**.  
**Описание.** Вариант использования **"Получение списка доставок"** позволяет пользователю просматривать подробную информация о сотруднике.  

**Основной поток:**
1. На странице личного кабинета пользователь нажимает кнопку "Список доставок".
2. Приложение осуществляет запрос базы данных с целью получения списка доставок, в которые вовлечён пользователь.
3. Приложение формирует страницу ответа пользователю на основе полученных данных.
4. Приложение отправляет сформированную страницу пользователю.
5. Конец.
<a name="1.2.5"/>

##### 1.2.5 Запрос на изменение статуса доставки
**Предусловие.** Выполнение прецедента **Получения списка доставок**.  
**Описание.** Вариант использования **"Запрос на изменение статуса доставки"** позволяет пользователю осуществлять запрос на завершение доставки.  

**Основной поток:**
1. Пользователь нажимает кнопку "**Доставка звершена**" на странице со списком доставок.
2. Приложение проверяет текущий статус доставки, если доставка ждёт подтверждения завершения текущим пользователем, переход к пункту 3, иначе переход к пункту 4.
3. Статус доставки изменяется на "**Завершена**". Переход к пункту 5.
4. Статус доставки изменяется на "**Ожидающая подтверждения завершения**".
5. Пользователю отправляется текущий статус доставки.
6. Конец.

<a name="1.2.6"/>

##### 1.2.6 Запрос на обновление личных данных
**Предусловие.** Выполнение прецедента **Аутентификация**.  
**Описание.** Вариант использования **"Запрос на обновление личных данных** позволяет пользователю отправлять запросы на добавление и изменение личных данных.  

**Основной поток:**
1. Пользователь нажимает кнопку "**Редактировать профиль**" на странице личного кабинета.
2. Приложение переходит на страницу редактирования профиля.
3. Пользователь редактирует поля с личной информацией и нажимает на кнопку "**Редактировать**".
4. Приложение валидирует данные пользователя. 
5. Если данные корректны, переход к пункту 6, иначе переход к пункту 3.
6. Приложение получает запрос пользователя и формирует запрос на изменение в базе данных.
7. Приложение осуществляет запрос в базу данных.
8. Приложение формирует страницу результат запроса.
9. Приложение отправляет сформированную страницу польpователю. 
10. Конец.

##### 1.2.7 Запрос на осуществление доставки
**Предусловие.** Выполнение прецедента **Получение подробной информации о курьере**.  
**Описание.** Вариант использования **"Запрос на осуществление доставки"** позволяет клиенту создавать запросы на доставку.  

**Основной поток:**
1. Клиент на странице со спиком доступных курьеров нажимает на кнопку **Заказать доставку**.  
2. Приложение переходит на страницу формирования доставки.  
3. Клиент заполняет необходимую информацию о доставке и нажимает на кнопку **Заказать доставку**.
4. Приложение получает запрос клиента, проверяет текущее состояние клиента. Если текущее состояние клиента не позволяет ему осуществлять запросы на доставку, переход к пункту 5, иначе переход к пункту 6.  
5. Приложения перенаправляет пользователя на страницу с ошибкой. Переход к пункту 9.
6. Приложение создаёт необходимые структуры данных необходимые для отслеживания статуса доставки.  
7. Приложение добавляет созданный объект доставки в список доставок, в которые вовлечены текущий пользователь и выбранный курьер.    
8. Приложение отправляет текущей статус доставки клиенту.  
9. Конец.  

<a name="1.2.8"/>

##### 1.2.8 Получение списка клиентов
**Предусловие.** Выполнение прецедента **Аутентификация**.  
**Описание.** Вариант использования **"Получение списка пользователей"** позволяет администратору системы получать список зарегистрированных клиентов.  

**Основной поток:**
1. Администратор системы на странице администрирования системы нажимает выбирает тип пользователей, которых он хочет получить, затем нажимает на кнопку **Получить**.
2. Приложение осуществляет запрос базы данных с целью получения списка заданных пользователей.
3. Приложение формирует страницу ответа администратору на основе полученных данных при выполнении запроса.
4. Приложение отправляет сформированную страницу администратору.
5. Конец

<a name="1.2.9"/>

##### 1.2.9 Удаление пользователя
**Предусловие.** Выполнение прецедента **Получение списка пользователей**.  
**Описание.** Вариант использования **"Удаление пользователя"** позволяет администратору удалять зарегистрированных пользователей системы.  

**Основной поток:**  
1. Администратор на странице администрирования системы выбирает пользователя из списка и нажимает на кнопку **Удалить** рядом с ним.
2. Приложения формирует запрос на удаление заданного пользователя.
3. Приложение отправляет запрос в базу данных.
4. Приложение формирует страницу результат на основе выполненного запроса.
5. Приложение отправляет сформированную страницу администратору.
6. Конец.

<a name="1.2.10"/>

##### 1.2.10 Получение подробной информации о курьере
**Предусловие.** Выполнение прецедента **Получение списка курьеров**.  
**Описание.** Вариант использования **"Получение подробной информации о курьере"** позволяет клиенту получить подробную информацию о курьере.  

**Основной поток:**  
1. Клиент на странице со списком крьеров нажимает на область с конкретным курьером.
2. Приложения отправляет запрос на получение информации о заданном курьере.
3. Приложение формирует запрос к базе данных.
4. Приложение исполняет запрос к базе данных.
5. Приложение формирует страницу результата на основе результата запроса.
6. Приложение отправляет сформированную страницу клиенту.
7. Конец.

<a name="2"/>

### 2. Диаграммы активности

<a name="2.1"/> 

#### 2.1 Регистрация
Гость на главной странице нажимает на кнопку регистрации, и ему предлагается заполнить формы необходимые для регистрации. При заполнении форм данных происходит их валидация. При неверных данных пользователю предлагается ввести их ещё раз, иначе происходит регистрация нового пользователя и переход на страницу личного кабинета.

![alt text](https://github.com/VRublevski/tritpo-project-courier-exchange/blob/master/Images/Diagrams/Activity/registration.jpg)

<a name="2.2"/>

#### 2.2 Запрос на изменение статуса доставки
Пользователь делает запрос на звершение транзакции. Если пользователь является первым из двух участников доставки, сделавшим запрос на изменение, доставке присваивается статус **Ожидающая**, если вторым, статус **Завершена**.

![alt text](https://github.com/VRublevski/tritpo-project-courier-exchange/blob/master/Images/Diagrams/Activity/request-for-delivery-status-change.jpg)

<a name="2.3"/>

#### 2.3 Запрос на осуществление доставки
Клиент на странице с детальной информацию о курьере нажимает на кнопку **Заказать доставку**. Система проверяет состояние клиента. Если клиент способен осуществлять запросы на доставку, будут созданые необходимые структуры данных для отслеживания статуса доставки, иначе пользователь будет перенаправлен на страницу с ошибкой. После создания необходимых структур данных, доставке присваивается статус "**Активна**" и статус доставки отправляется пользователю. 

![alt text](https://github.com/VRublevski/tritpo-project-courier-exchange/blob/master/Images/Diagrams/Activity/request-for-delivery.jpg)
<a name="2.4"/>

#### 2.4 Запрос на обновление личных данных
На странице личного кабинета пользователь нажимает кнопку "**Редактировать профиль**", приложение переходит на страницу редактирования профиля с необходимыми формами для заполнения. После заполнения требуемых форм пользователь нажимает на кнопку "**Редактировать**". Происходит валидация введённых данных, если введённые данные корректны, формируется и исполняется запрос в базу данных приложения, иначе пользователю предлагается заново вввести личную информацию.

![alt text](https://github.com/VRublevski/tritpo-project-courier-exchange/blob/master/Images/Diagrams/Activity/request-for-personal-data-change.jpg)

<a name="2.5"/>

#### 2.5 Получение подробной информации о курьере
На странице со списком курьеров пользователь нажимает на область с конкретным курьером. Приложение обрабатывает запрос пользователя, формирует необходимый запрос в базу данных и исполняет его. На основе данных результата запроса формируется страница результата, которая отправляется пользователю.

![alt text](https://github.com/VRublevski/tritpo-project-courier-exchange/blob/master/Images/Diagrams/Activity/descriptive-information.jpg)


<a name="3"/>

### 3. Диаграммы последовательности

<a name="3.1"/>

#### 3.1 Регистрация

![alt text](https://github.com/VRublevski/tritpo-project-courier-exchange/blob/master/Images/Diagrams/Sequance/register.jpg)

<a name="3.2"/>

#### 3.2 Запрос на изменение статуса доставки

![alt text](https://github.com/VRublevski/tritpo-project-courier-exchange/blob/master/Images/Diagrams/Sequance/request-for-delivery-status-change.jpg)

<a name="3.3"/>

#### 3.3 Запрос на осуществление доставки

![alt text](https://github.com/VRublevski/tritpo-project-courier-exchange/blob/master/Images/Diagrams/Sequance/request-for-delivery.jpg)

<a name="3.4"/>

#### 3.4 Запрос на обновление личных данных

![alt text](https://github.com/VRublevski/tritpo-project-courier-exchange/blob/master/Images/Diagrams/Sequance/request-for-personal-data-change.jpg)

<a name="3.5"/>

#### 3.5 Получение подробной информации о курьере

![alt text](https://github.com/VRublevski/tritpo-project-courier-exchange/blob/master/Images/Diagrams/Sequance/descriptive-information.jpg)

<a name="4"/>

### 4. Диаграммы состояния<a name="4"></a>

<a name="4.1"/>

#### 4.1 Клиент<a name="4.1"></a>
![alt text](https://github.com/VRublevski/tritpo-project-courier-exchange/blob/master/Images/Diagrams/State/client.jpg)

<a name="4.2"/>

#### 4.2 Курьер<a name="4.2"></a>
![alt text](https://github.com/VRublevski/tritpo-project-courier-exchange/blob/master/Images/Diagrams/State/courier.jpg)

<a name="4.3"/>

#### 4.3 Доставка<a name="4.3"></a>

![alt text](https://github.com/VRublevski/tritpo-project-courier-exchange/blob/master/Images/Diagrams/State/delivery.jpg)

<a name="5"/>

### 5. Диаграмма классов<a name="5"></a>
![alt text](https://github.com/VRublevski/tritpo-project-courier-exchange/blob/master/Images/Diagrams/Class/exchange.jpg)

<a name="6"/>

### 6. Совмещенная диаграмма компонентов и развертывания <a name="6"></a>
![alt text](https://github.com/VRublevski/tritpo-project-courier-exchange/blob/master/Images/Diagrams/Deployment/exchange.jpg)
