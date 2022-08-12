import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Main {
    final static Scanner scanner = new Scanner(System.in, "utf-8");

    public static void main(String[] args) {
        int numOfPeople = getNumOfPeople(); // Колисчестов людей в компании.
        Calculator calculator = new Calculator(); // Модель хранения продуктов.
        addsProductsInList(calculator); // Добавляем продукты в модель.
        printResults(calculator, numOfPeople); // Печатаем результат.
    }

    /**
     * Запрашивает пользователя ввести количество человек в компании, и возвращает это количество.
     *
     * @return Возвращает число человек в комании.
     */
    public static int getNumOfPeople() {
        System.out.println("Привет! На сколько человек должен быть разделен счет?"); // Просим ввести количество людей
        int numOfPeople;
        try {
            numOfPeople = Integer.parseInt(scanner.nextLine()); // Перехватываем вылет, если пользователь ввел не число
        } catch (NumberFormatException e) {
            numOfPeople = 0;
        } // Получаем пользовательский ввод
        while (numOfPeople <= 1) { // Если ввод неверный, сообщаем об ошибке и просим ввести снова
            System.out.println("Неправильный ввод! Количество человек должно быть > 1, попробуйте ввести еще раз.");
            // Повтор запроса ввода
            System.out.println("На сколько человек должен быть разделен счет?");
            try {
                numOfPeople = Integer.parseInt(scanner.nextLine()); // Перехватываем вылет, если пользователь ввел не число
            } catch (NumberFormatException e) {
                numOfPeople = 0;
            }
        }
        return numOfPeople;
    }

    /**
     * Добавляет товары в список и подсчитывает общую сумму за них.
     *
     * @param calculator Экземляр класса, со списком товаров.
     */
    public static void addsProductsInList(Calculator calculator) {
        String userInputAdd; // Пользовательский ввод.
        String name; // Имя товара.
        String price; // Цена товара.
        int[] indexes = new int[4]; // Массив индексов для вытаскивания подстроки.
        while (true) {
            System.out.println("Введите имя товара и его цену в соответствии с форматом, чтобы добавить. Формат -> \"'рубли.копейки' [10.45, 11.40]\"."); // Просим пользователя ввести товар.
            userInputAdd = scanner.nextLine(); // Получаем пользовательский ввод.
            if (!userInputAdd.isEmpty()) { // Если пользователь что-то ввел.
                parseUserAddInput(userInputAdd, indexes);
                if (indexes[0] < 1 || indexes[1] < 1 || indexes[2] < 1 || indexes[3] < 1) { // Проверка ввода пользователя на соответствие формату.
                    if (indexes[0] == 0 || indexes[1] == -1) {
                        System.out.println("Неправильный ввод, повторите попытку! Убедитесь что поместили имя товара в ковычки!"); // Сообщение об ошибке.
                    } else {
                        System.out.println("Неправильный ввод, повторите попытку! Убедитесь что правильно указали цену!"); // Сообщение об ошибке.
                    }
                    clearIndexes(indexes);
                    continue; // Перемещение к началу тела цикла.
                }
                if (userInputAdd.charAt(indexes[2] - 1) == '-') { // Проверка на существование минуса перед числом.
                    System.out.println("Неправильный ввод, повторите попытку! Цена товара не может быть отрицательной!");
                    clearIndexes(indexes);
                    continue;
                }
                name = userInputAdd.substring(indexes[0], indexes[1]); // Извлечение из пользовательского ввода имени товара.
                if (name.isEmpty()) {
                    name = Character.toString(userInputAdd.charAt(indexes[0]));
                }
                price = userInputAdd.substring(indexes[2], indexes[3]); // Извлечение из пользовательского ввода цены товара.
                if (price.isEmpty()) {
                    price = Character.toString(userInputAdd.charAt(indexes[2]));
                }
                calculator.addProduct(name, Float.parseFloat(price)); // Добавление товара в список.
                System.out.println("Товар был успешно добавлен!"); // Сообщение об успешном добавлении.
                clearIndexes(indexes);
            } else {
                System.out.println("Неправильный ввод, повторите попытку!");
                continue;
            }
            System.out.println("Хотите добавить еще один продукт?\nВведите \"Завершить\" чтобы закончить добавление товаров.\nВведите любую букву для продолжения."); // Спрашиваем пользователя, хочет ли он добавить еще один товар.
            userInputAdd = scanner.nextLine(); // Получаем ответ на вопрос
            if (userInputAdd.equalsIgnoreCase("завершить")) { // Если пользователь ввел "Завершить", выходим из цикла.
                break;
            }
        }
    }

    /**
     * Парсер для извлечения из пользовательского ввод данных, для добавления в список продуктов.
     *
     * @param userInput Пользовательский ввод.
     * @param indexes   Массив для сохранения индексов, для подстрок.
     */
    public static void parseUserAddInput(String userInput, int[] indexes) {
        indexes[0] = userInput.indexOf('\'') + 1; // Буква после первого попавшегося символа "'".
        indexes[1] = userInput.indexOf('\'', indexes[0]); // Буква перед следующим попавшимся символом "'".
        if (indexes[0] == 0 || indexes[1] == -1) {
            return;
        }
        int length = userInput.length();
        for (int i = indexes[1]; i < length; i++) { // Начинаем со следующего символа после второй "'".
            if (Character.isDigit(userInput.charAt(i)) && indexes[2] == 0) { // Находим цифру.
                indexes[2] = i;
            }
            // Если после нахождения цифры нашелся символ не цифры и это не ., то запоминаем последнее вхождение цифры.
            if (!Character.isDigit(userInput.charAt(i)) && userInput.charAt(i) != '.' && indexes[2] != 0) {
                indexes[3] = i - 1;
                return;
            }
        }
        if (indexes[2] != 0 && indexes[3] == 0) {
            indexes[3] = userInput.length() - 1;
        }
    }

    /**
     * Печатает результат подсчетов.
     *
     * @param calculator  Список всех продуктов и их общей стоимости.
     * @param numOfPeople Количество человек в компании.
     */
    public static void printResults(Calculator calculator, int numOfPeople) {
        System.out.println("Добавленные товары:");
        int size = calculator.getProducts().size();
        float part = calculator.getTotalAmount() / numOfPeople; // Поделенный счет на одного человека
        for (int i = 0; i < size; i++) {
            System.out.println(calculator.getProducts().get(i) + ".");
        }
        String format = "На каждого человека получится по = %.2f"; // Округление до 2 чисел после запятой
        if (part % 100 > 10 && part % 100 < 15) {
            System.out.println(String.format(format, part) + " рублей.");
        } else if (part % 10 == 1) {
            System.out.println(String.format(format, part) + " рублю.");
        } else if (part % 10 > 1 && part % 10 < 5) {
            System.out.println(String.format(format, part) + " рубля.");
        } else {
            System.out.println(String.format(format, part) + " рублей.");
        }
    }

    /**
     * Очистка массива от значений
     * @param indexes Массив для обнуления
     */
    public static void clearIndexes(int[] indexes) {
        int size = indexes.length;
        for (int i = 0; i < size; i++) {
            indexes[i] = 0;
        }
    }
}
