### Домашнее задание<br>

Самодельный ORM<br>
Работа должна использовать базу данных H2.<br>
Используйте предложенный на вебинаре api (пакет api).<br>

Создайте в базе таблицу User с полями:<br>

• id bigint(20) NOT NULL auto_increment<br>
• name varchar(255)<br>
• age int(3)<br>

Создайте свою аннотацию @Id<br>

Создайте класс User (с полями, которые соответствуют таблице,<br> поле id отметьте аннотацией).

Напишите JdbcMapper, который умеет работать с классами, <br>в которых есть поле с аннотацией @Id.<br>
JdbcMapper должен сохранять объект в базу и читать объект из базы.<br>
Фактически, это надстройка над DbExecutor<T>,<br> которая по заданному классу умеет генерировать sql-запросы.<br>
А DbExecutor<T> должен выполнять сгенерированные запросы.<br>

Имя таблицы должно соответствовать имени класса,<br> а поля класса - это колонки в таблице.<br>

Методы JdbcTemplate'а:<br>
void create(T objectData);<br>
void update(T objectData);<br>
void createOrUpdate(T objectData); // опционально.<br>
<T> T load(long id, Class<T> clazz);<br>

Проверьте его работу на классе User.<br>

Метод createOrUpdate - необязательный.<br>
Он должен "проверять" наличие объекта в таблице и создавать новый или обновлять.<br>

Создайте еще одну таблицу Account:<br>
• no bigint(20) NOT NULL auto_increment<br>
• type varchar(255)<br>
• rest number<br>

Создайте для этой таблицы класс Account и <br>проверьте работу JdbcMapper на этом классе.