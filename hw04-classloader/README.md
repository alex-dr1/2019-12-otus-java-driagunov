#### Домашнее задание<br>

##### Автоматическое логирование

Разработайте такой функционал:<br>
метод класса можно пометить самодельной аннотацией @Log, например, так:

class TestLogging {<br>
	@Log<br>
	public void calculation(int param) {};<br>
}

При вызове этого метода "автомагически" в консоль должны логироваться значения параметров.
Например так.

class Demo {<br>
	public void action() {<br>
		 new TestLogging().calculation(6);<br>
	}<br>
}<br>


В консоле дожно быть:<br>
executed method: calculation, param: 6<br>
Обратите внимание: явного вызова логирования быть не должно.
