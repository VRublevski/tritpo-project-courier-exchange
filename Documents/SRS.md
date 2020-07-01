 # Требования к проекту  
### *COURIER EXCHANGE*


# Содержание

 1.	[Введение](#1)   
   1.1. [Назначение](#1.1)    
   1.2. [Рекомендации по чтению](#1.2)  
   1.3.  [Границы проекта](#1.3)      
 2.	[Общее описание](#2)   
   2.1. [Общий взгляд на продукт](#2.1)   
   2.2. [Классы и характеристики пользователей](#2.2)   
   2.3. [Операционная среда](#2.3) 
   
 3.	[Функции системы](#3)    
   3.1. [Указание вида транспорта](#3.1)   
   3.2. [Указание вида перевозимых грузов](#3.2)   
   3.3. [Создание запроса на доставку](#3.3)   
   3.4. [Анализ истории закозов на доставку](#3.4)
      
 4.	[Требования к внешнему интерфейсу](#4)   
   4.1. [Интерфейс пользователя](#4.1)
   
 5.	[Другие нефункциональные требования](#5)   
    5.1. [Атрибуты качества](#5.1)   
 
---
##  1.	Введение <a name="1"></a>     
###    1.1	Назначение.<a name="1.1"></a>

Данный документ описывает требования к веб-приложению: "Courier Exchange". 

###     1.2 Рекомендации по чтению.<a name="1.2"></a> 
Данный документ рекомендуется читать последовательно раздел за разделом. Для краткого ознакомления с проектом достаточно прочитать пункт [1.3. Границы проекта](#1.3) , а также раздел [2. Общее описание](#2). 
###	1.3 Границы проекта.<a name="1.3"></a>      
####	1.3.1 Исходные данные.<a name="1.3.1"></a>
В современном мире люди всё чаще совершают покупки онлайн в интернет-магазинах.Данный вид бизнеса является привлекательным для мелких предпринимателей, т.к. является менее требовательным к начальному капиталу и масштабирующемся быстрее, чем магазин в реальном мире. 
Однако им всё ещё необходимы значительные суммы средств для обеспечения доставки товаров своим покупателем. Для мелких предпринимателей
наличие постоянного штата курьеров является непозволительным особенно на ранних этапах развития бизнеса. Данное приложение предоставит 
предпринимателям возможность иметь виртуальный штаб курьеров, подстраивающийся под текущую продуктивность бизнеса.  
####	1.3.2 Возможности бизнеса.<a name="1.3.2"></a> 
На сегодняшний день на рынке уже есть платформа предоставляющая данную возможность. Недостатком этой платформы является долгое время доставки грузов.
Создатели проекта:"Courier Exchange" полагают, что данный недостаток можно устранить используя средства анализа данных и историю доставок товаров.
Можно было бы предсказывать очередные заказы на доставку и предоставлять информацию о прогназах и точности этих прогнозов курьерам. Это позволило бы снизить среднее время ожидания товара, что , в свою очередь,  должно повысить
число товаров реализуемых интернет-магазинами, а это приведёт к росту их прибыли. Таким образом данный проект ставит своей целью сокращение времени ожидания
доставки товара заказчиками.
####	1.3.3 Объём первоначальной версии.<a name="1.3.3"></a> 
Первая версия данного веб-приложения должна реализовывать базовый для такого рода веб-приложения функионал, который позволил бы просто осуществлять доставку груза.        
####	1.3.4 Объём последующих версий.<a name="1.3.4"></a> 
Последующие версии будут стремиться всячески ускорить доставку товара клиенту. Важнейшей функцией данной системы создатели проекта счтиают возможность предсказывать очередные заказы на доставку. Также планируются добавить возможноть создания и выбора шаблона доставки товаров.
## 2.	Общее описание<a name="2"></a> 

### 2.1	Общий взгляд на продукт<a name="2.1"></a>   

Данный проект можно отнести к категории проектов предоставляющих посреднические услуги. Яркими представителями данной категории является Uber и eBay.  

### 2.2	Классы и характеристики пользователей<a name="2.2"></a>   
Можно выделить три основных класса пользователей данной системы : курьеры, заказчики и администраторы. Администраторы обладают полным контролем над данными системы, т.е. имеют доступ к любым данным системы с возможностью их редактирования.
Заказчики и курьеры могут лишь осуществлять запросы на изменение и добавление данных в систему. 

### 2.3	Операционная среда<a name="2.3"></a>   
Данная система будет осуществлять работу с заказчиками и курьерами города Минска, которые будут взаимодействовать с ней с помощью веб-браузера.

## 3. Функции системы<a name="3"></a>   
### 3.1 Указание вида транспорта<a name="3.1"></a> 
#### 3.1.1 Описание и приоритеты
Курьеры имеют возможность указывать вид транспорта, с помощью которого будет осуществляться доставка грузов.
Данная функция имеет высокий приоритет. 
#### 3.1.2 Функциональные требования 
1. Регистрация курьера в системе.
2. Аутентификация курьера в системе.
3. Отправка данных. 
4. Получение результата запроса.

### 3.2 Указание вида перевозимых грузов<a name="3.2"></a> 
#### 3.2.1 Описание и приоритеты
Курьеры имеют возможность указывать вид груза, который они имеют возможность доставлять.
Данная функция имеет высокий приоритет. 
#### 3.2.2 Функциональные требования 
1. Регистрация курьера в системе.
2. Аутентификация курьера в системе.
3. Отправка данных. 
4. Получение результата запроса.

### 3.3 Создание запроса на доставку<a name="3.3"></a> 
#### 3.3.1 Описание и приоритеты
Заказчики имеют возможность создавать запрос на доставку.  
Данная функция имеет высокий приоритет. 
#### 3.3.2 Функциональные требования 
1. Регистрация заказчика в системе.
2. Аутентификация заказчика в системе.
3. Отправка данных. 
4. Получение результата запроса.

### 3.4 Анализ истории заказов на доставку<a name="3.4"></a> 
#### 3.4.1 Описание и приоритеты
Система имеет возможность прогнозировать очередные заказы на доставку.  
Данная функция имеет высокий приоритет. 
#### 3.4.2 Функциональные требования 
1. Хранение данных.
2. Обработка данных.

## 4. Требования к внешнему интерфейсу<a name="4"></a>   
### 4.1 Интерфейс пользователя<a name="4.1"></a> 
   Стартовая страница веб-приложения. На этой странице пользователь сможет получить краткое описание функциональности приложения. 
   
 ![alt text](https://github.com/VRublevski/tritpo-project-courier-exchange/blob/master/Images/Mockups/Home%20Page.png)  
 
   Страница "Личный кабинет". На данной странице пользователь(курьер, заказчик). Может редактировать информацию о себе. 
   Также на этой странице предоставляется информация о заказах. Курьер сможет наблюдать заказы, которые ему предстоит выполнить.
   Для заказчика отображается информация о ещё не завершённых доставках. 
   
 ![alt text](https://github.com/VRublevski/tritpo-project-courier-exchange/blob/master/Images/Mockups/Cabinet.png)
   
  
   Страница со списком курьеров. На этой странице пользователь сможет видеть курьеров с их кратким описанием.
   
 ![alt text](https://github.com/VRublevski/tritpo-project-courier-exchange/blob/master/Images/Mockups/Currier%20List.png)  
## 5. Другие нефункциональные требования<a name="5"></a>
### 5.1 Атрибуты качечтва<a name="5.1"></a> 
   1. Доступность.  
      Система должна быть доступна каждый день с 6:00 до 00:00 . В период наиболее вероятного осуществления запросов на доставку.
      
   2. Целостность.  
      Только у авторизованных пользователей есть доступ к личным данным пользователя. Этот доступ ограничивается доступом только к собственным данным. 
      Исключение составляют администраторы, которым доступны личные данные других пользователей. 