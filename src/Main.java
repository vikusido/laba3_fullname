import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите ФИО");
        Scanner sc = new Scanner(System.in);
        String fullName = sc.nextLine();
        String[] nameParts = fullName.split(" ");
        System.out.println("Введите дату рождения в формате ДД.ММ.ГГГГ или ДД/ММ/ГГГГ");
        String birthDate = sc.nextLine();
        String[] dateParts = birthDate.split("[./]");

        if (nameParts.length < 3) {
            System.out.println("Некорректный формат ФИО");
            return;
        }

        //initials
        String surname = nameParts[0];
        String name = nameParts[1];
        String patronymic = nameParts[2];
        String initials = String.format("%s %s.%s.", surname, name.charAt(0), patronymic.charAt(0));

        //gender
        char gender = 'A';
        if (patronymic.endsWith("вна") || patronymic.endsWith("ична")) {
            gender = 'Ж';
        } else if (patronymic.endsWith("вич") || patronymic.endsWith("ич")) {
            gender = 'М';
        }
        String genderOut = (gender == 'Ж') ? "Женский" : (gender == 'М') ? "Мужской" : "Определить не удалось";

        //age
        if (dateParts.length != 3) {
            System.out.println("Некорректный формат даты");
            return;
        }
        int day, month, year;
        try {
            day = Integer.parseInt(dateParts[0]);
            month = Integer.parseInt(dateParts[1]);
            year = Integer.parseInt(dateParts[2]);
        } catch (NumberFormatException ex) {
            System.out.println("Некорректный формат даты");
            return;
        }
        //check date
        if (day < 1 || day > 31 || month < 1 || month > 12) {
            System.out.println("Некорректный формат даты");
            return;
        }
        int age = java.time.LocalDate.now().getYear() - year;
        if (java.time.LocalDate.now().getYear() < year){
            System.out.println("Этот человек пока не родился");
            return;
        }
        int currentDay = java.time.LocalDate.now().getDayOfYear();
        int birthDay = getDayOfYear(day, month, year);
        if (currentDay < birthDay) {
            age--;
        }
        String ageS = ageString(age);
        System.out.println("Инициалы " + initials);
        System.out.println("Пол " + genderOut);
        System.out.println("Возраст " + age + ageS);
        sc.close();
    }

    private static int getDayOfYear(int day, int month, int year) {
        int[] days = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (isLeapYear(year)) {
            days[2] = 29;
        }
        int dayOfYear = day;
        for (int i = 1; i < month; i++) {
            dayOfYear += days[i];
        }
        return dayOfYear;
    }

    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    private static String ageString(int age) {
        if (age % 10 == 1 && age % 100 != 11) {
            return " год";
        } else if (age % 10 >= 2 && age % 10 <= 4 && (age % 100 < 10 || age % 100 >= 20)) {
            return " года";
        } else {
            return " лет";
        }
    }
}