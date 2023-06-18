import java.util.Scanner;

public class Work {
    private int enterNum(){
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }
    /**
     * Процесс добавления игрушки в список для розыгрыша
     * @param marcet
     * @throws Exception
     */
    private void AddGameInListDraw(Marcet marcet) throws Exception {
        int allGame = marcet.givCountAllGameInMar();
        int num = 0;
        while (allGame!=0){
            try {
                if (marcet.addGameInDraw()){
                    allGame -= 1;
                }
                System.out.println("Что бы добавить ещё введите 1 или другую цифру что бы остановиться:");
                num = enterNum();
                if (num != 1){
                    break;
                }
            }catch (Exception e){
                e.getStackTrace();
                System.out.println("Добавление прервано.");
                break;
            }
        }
        if (allGame == 0){
            System.out.println("В магазине закончились игрушки. Они все находятся в розыгрыше");
        }
    }

    /**
     * Вызывая этот метод проходит процесс всей работы связанный с добавлением игрушки для розыгрыша и самим розыгрышом.
     * @param marcet
     * @throws Exception
     */
    public void operateDraw(Marcet marcet) throws Exception {
        marcet.createFile();
        System.out.println("Вы находитесь в своём магазине игрушек. Как раз сегодня вы проводите розыгрыш игрушек в честь " +
                "открытия магазина! Вы можете добавить игрушки в список разыгрываемых,\nзатем провести розыгрыш или закончить " +
                "работу и закрыть магазин.\nТак же сможете посмотреть на весь перечень игрушек, которые есть в магазине." +
                "\nМожно посмотреть на список игрушек, которые в розыгрыше.\nЧто бы добавить игрушки для розыгрыша введите 1" +
                "\nЧто бы провести розыгрыш введите 2\nЧто бы посмотреть на весь перечень игрушек, то введите 3\n" +
                "Что бы посмотреть на список игрушек, которые в розыгрыше введите 4\nЧто бы закончить работу магазина " +
                "введите 0\nА вот весь перечень игрушек, которые есть в магазине:");
        System.out.println(marcet);
        int num = 0;
        boolean fl = true;
        while (fl){
            try {
                System.out.println("Введите число соответствующие для добавления, розыгрыша или закрытия магазина:");
                num = enterNum();
                if (num == 0){
                    System.out.println("Магазин закрывается.");
                    fl = false;
                } else if (num == 1) {
                    AddGameInListDraw(marcet);
                } else if (num == 2) {
                    marcet.Draw();
                } else if (num == 3) {
                    System.out.println(marcet);
                } else if (num == 4) {
                    System.out.println(marcet.printDraw());
                } else System.out.println("Такого функционала нет.");
            }catch (Exception e){
                e.getStackTrace();
                System.out.println("Что-то пошло не так.");
            }

        }


    }
}
