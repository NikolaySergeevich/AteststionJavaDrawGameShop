public class Main {
    public static void main(String[] args) throws Exception {
        Marcet marcet = new Marcet();
        Work work = new Work();
        marcet.addGame(new Game("Мишка", 1, "Чёрный", 23))
                .addGame(new Game("Заяц", 1, "Белый", 44))
                .addGame(new Game("Волк", 2, "Серый", 12))
                .addGame(new Game("Кот", 3, "Белый", 5))
                .addGame(new Game("Собака", 2, "Рыжый", 14))
                .addGame(new Game("Черепаха", 2, "Зеленый",4));

        work.operateDraw(marcet);
    }
}