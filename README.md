### Simple3DViewer ✨🧭

Интерактивный 3D‑просмотрщик на Java 17 и JavaFX 17: загружайте модели в формате OBJ, применяйте текстуры PNG, вращайте камеру мышью, перемещайтесь по сцене и сохраняйте модели обратно в OBJ.

— Поддержка Windows/macOS/Linux • Maven • Без внешних нативных зависимостей —

---

### 🚀 Возможности
- **Загрузка моделей**: `*.obj`
- **Текстуры**: загрузка `*.png`
- **Рендер**: проекция, Z‑buffer, закраска/растеризация треугольников, полигональная сетка
- **Нормали и триангуляция**: авторасчёт при загрузке
- **Камера**: свободное перемещение, вращение мышью, зум колёсиком
- **Сохранение**: экспорт текущей/трансформированной модели в `*.obj`

---

### 🖥️ Скрин и UI
Главное окно — это `Canvas` с верхним меню и информационными лейблами слева внизу: позиция/вращение камеры и параметры модели. Макет описан в `src/main/resources/com/cgvsu/fxml/gui.fxml`.

---

### 🎮 Управление
- **Мышь**: зажатие ЛКМ и перемещение — вращение камеры (yaw/pitch)
- **Колёсико**: приближение/удаление (zoom)
- **Меню File → Load Model**: выбрать OBJ
- **Меню Texture → Load Texture**: выбрать PNG
- **Сохранение**: File → Save Model / Save Model (modified)

---

### ⌨️ Горячие клавиши
- Камера: **W/S** вперёд/назад, **A/D** влево/вправо, **Q/E** вниз/вверх
- Поворот: стрелки **←/→/↑/↓**
- Загрузка: File → Load Model (F), Texture → Load Texture (F)

Примечание: комбинации назначены в `gui.fxml` и обрабатываются в `GUIController`.

---

### 🧩 Архитектура (основные модули)
- `com.cgvsu.Simple3DViewer` — запуск JavaFX, подгрузка `gui.fxml`
- `com.cgvsu.gui.GUIController` — логика UI, ввод, загрузка/сохранение, цикл рендера
- `com.cgvsu.gui.ModelsController` — список и текущая модель
- `com.cgvsu.io.objReader.ObjReader` — парсинг OBJ
- `com.cgvsu.io.objWriter.ObjWriter` — запись OBJ
- `com.cgvsu.render_engine.Camera` — матрицы вида/проекции, перемещения, зум, вращение
- `com.cgvsu.render_engine.RenderEngine` — конвейер рендера, Z‑buffer, растеризация
- `com.cgvsu.model.Model/Polygon/FinishedModel` — данные сцены и трансформации
- `com.cgvsu.Utils.*` — триангуляция, нормали, растеризация, сетка

---

### 📦 Требования
- Java **17**
- Maven **3.8+**
- JavaFX (**добавлено как Maven‑зависимости**)

---

### 🛠 Установка и сборка
1) Убедитесь, что Java 17 и Maven установлены и доступны в PATH.
2) В корне проекта выполните:

```bash
mvn clean package -DskipTests=true
```

Соберётся jar с зависимостями (см. `maven-assembly-plugin`, main‑class `com.cgvsu.Main`).

---

### ▶️ Запуск
Запуск из Maven:

```bash
mvn -q -DskipTests=true exec:java -Dexec.mainClass="com.cgvsu.Main"
```

Запуск из собранного jar:

```bash
java -jar target/cgvsu.jar
```

Если JavaFX не подхватился автоматически, убедитесь, что используете JDK 17 и зависимости JavaFX подтягиваются Maven’ом (в проекте настроены `org.openjfx:javafx-controls` и `javafx-fxml`).

---

### 📁 Структура проекта
```text
Simple3DViewer/
  pom.xml
  models/                # Примеры моделей и текстур
  src/
    main/
      java/com/cgvsu/
        Main.java
        Simple3DViewer.java
        gui/GUIController.java, ModelsController.java
        io/objReader/ObjReader.java, ...
        io/objWriter/ObjWriter.java
        render_engine/Camera.java, RenderEngine.java, ...
        model/Model.java, Polygon.java, FinishedModel.java
      resources/com/cgvsu/fxml/gui.fxml
    test/java/com/cgvsu/...  # JUnit тесты
```

---

### 🗂 Примеры моделей
В репозитории есть каталог `models/3DModels` с объектами (`.obj`) и текстурами (`.png`), включая:
- `CaracalCube/` (OBJ + PNG)
- `Faceform/` (набор OBJ и превью)
- `SimpleModelsForReaderTests/` (разнообразные OBJ для проверки чтения)

Откройте их через меню: File → Load Model / Texture → Load Texture.

---

### ✅ Тестирование
В проекте присутствуют тесты (JUnit 5) для чтения OBJ и математики: `src/test/java/com/cgvsu/**`. Запуск:

```bash
mvn test
```

---

### ❓FAQ
- **Какие форматы поддерживаются?** Модели: OBJ, текстуры: PNG.
- **Где найти вход?** Точка входа — `com.cgvsu.Main`.
- **Можно ли двигать модель?** Да — через свойства `Model` и `FinishedModel` (позиция, масштаб, поворот). Сценарий см. в `GUIController`.

---

### 🤝 Вклад
PR приветствуются. Рекомендуется придерживаться текущего стиля кода, Java 17, и покрывать изменения базовыми тестами.

---

### 📜 Лицензия
Укажите лицензию проекта (при необходимости).


