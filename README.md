# Лабораторная работа 1
## Цель работы:
Изучить цветовые модели: RGB, CMYK, HSV, HLS, преобразования между ними.

## Задачи работы:
Создать приложение/веб-приложение, позволяющее пользователю  выбирать, а затем интерактивно менять цвет, показывая при этом его составляющие в трех моделях одновременно.
В интерфейсе дать возможность пользователю задавать точные цвета (поля ввода), выбирать цвета из палитры (аналогично графическим редакторам), плавно изменять цвета (например, ползунки).
При изменении любой компоненты цвета все остальные представления этого цвета в двух других цветовых моделях пересчитываются автоматически.
Использованные средства разработки: 
Язык программирования Java

## Ход работы:
1. Создание главного класса Main, служащего главным окном.
2. Создание класса ColorPanel, отвечающего за отображение цвета.
3. Создания вспомогательных классов CMYKSwatch, RGBSwatch, HSVSwatch отвечающих за изменение компонентов цветов в системах CMYK, RGB, HSV.
4. Реализация паттерна проектирования Наблюдатель, где объект наблюдения - ColorPanel, наблюдатели - CMYKSwatch, RGBSwatch, HSVSwatch.
5. Реализован перевод из RGB в HSV и CMYK и наоборот
6. Создан пользовательский интерфейс.
## Вывод:
В ходе выполнения данной работы я:
• создала приложение, позволяющее задать цвет тремя разными способами в RGB, CMYK, HSV, а также показывающее какие значения компонент имеет цвет в разных системах.
• закрепила полученные лекционные знания про цветовые цветовые модели: RGB, CMYK, HSV, преобразования между ними.
• получила дополнительный опыт по проектированию приложений
• углубил знания языка Java
• получил дополнительный опыт работы с системой контроля версий Git