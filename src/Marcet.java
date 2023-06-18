import java.io.IOException;
import java.util.*;

public class Marcet {
    private List<Game> market = new ArrayList<>();
    private List<Game> draw = new ArrayList<>();
    Fillee dbPrize = new Fillee();


    public Marcet addGame(Game game) {
        market.add(game);
        return this;
    }
    Scanner sc = new Scanner(System.in);
    public void createFile() throws IOException {
        dbPrize.CreateFile();
    }

    /**
     * Добавление игрушки в список разыгрываемых
     * @throws Exception
     */
    public boolean addGameInDraw() throws Exception{
        System.out.println("Введите название игрушки: ");

        boolean flag = false;
        try {
            String name = sc.nextLine().toLowerCase();
            for (Game item: market) {
                if (item.getName().toLowerCase().equals(name) && item.getCountInMarcet() != 0) {
                    if (!draw.contains(item)){
                        draw.add(item);
                    }
                    System.out.println("Игрушка добавлена в перечень разыгрываемых.");
                    item.setCountInDrawUp();
                    item.setCountInMarcet();
                    flag = true;
                    break;
                } else if (item.getName().toLowerCase().equals(name) && item.getCountInMarcet() == 0) {
                    System.out.println("Все такие игрушки уже в розыгрыше.");
                    break;
                }
            }
        }
        catch (Exception e){
            e.getStackTrace();
        }
        if (!flag) {
            System.out.println("Игрушка не найдена");
        }
        return  flag;
    }

    public int givCountAllGameInMar(){
        int count = 0;
        for (Game it:market) {
            count += it.getCountInMarcet();
        }
        return count;
    }

    /**
     * Выводит на экран игрушки, которые попали в список для розыгрыша
     * @return
     */
    public String printDraw(){
        List<Integer> drawIndivid = givIndividList();
        timeSignificance(drawIndivid);
        appropriateProbability(drawIndivid);
        aldSignificance();
        StringBuilder res = new StringBuilder();
        for (Game item: draw) {
            res.append(item);
            res.append("\n");
        }
        return res.toString();
    }

    /**
     * Процесс розыгрыша.
     * @throws IOException
     */
    public void Draw() throws IOException {
        if (!draw.isEmpty()){
            List<Integer> drawIndivid = givIndividList();
            timeSignificance(drawIndivid);
            appropriateProbability(drawIndivid);
            List<Game> winerGame = givPrize(drawIndivid);
            if (winerGame.size() == 1){
                Game prize = winerGame.get(0);//получаем игрушку, которую получат в качестве приза из списка winerGame
                removeWinGameFromListDraw(prize);
                dbPrize.WriteGameInDB(prize);
                System.out.println("Игрушка, которую получат в качестве приза:");
                System.out.println(prize);
            } else {
                Random rn = new Random();
                Game prize = winerGame.get(rn.nextInt(0, winerGame.size()));
                removeWinGameFromListDraw(prize);
                dbPrize.WriteGameInDB(prize);
                System.out.println("Игрушка, которую получат в качестве приза:");
                System.out.println(prize);
            }
            System.out.println("Поздравляем, вы выиграли.");
        }
        else System.out.println("Разыгрываемых игрушек нет. Вы не можете провести розыгрыш");
        aldSignificance();
    }

    /**
     * Этот метод возвращает лист типа Int. В нём хранятся отсортированные значения уровней значимости игрушек, которые
     * учавствуют в розыгреше
     * @return
     */
    private List<Integer> givIndividList(){
        List<Integer> drawIndivid = new ArrayList<>();
        boolean flag;
        for (Game item:draw) {
            flag = true;
            for (Integer it:drawIndivid) {
                if (it == item.getSignificance()) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                drawIndivid.add(item.getSignificance());
            }
        }
        //System.out.println(drawIndivid);
        Collections.sort(drawIndivid);
        //System.out.println(drawIndivid);
        return drawIndivid;
    }

    /**
     * Этот метод нужен для временнного изменения значимостей для списка разыгрываемых
     * @param drawIndivid
     */
    private void timeSignificance(List<Integer> drawIndivid){
        int timeSig = 1;
        for (Integer num: drawIndivid) {
            for (Game item: draw) {
                if (num == item.getSignificance()){
                    item.setSignificance(timeSig);
                }
            }
            timeSig = timeSig + 1;
        }
    }

    /**
     * Метод возвращает старые значимости для игрушек
     */
    private void aldSignificance(){
        for (Game item:draw) {
            item.setSignificance(item.getSignific());
        }
    }

    /**
     * Этот метод устанавливает процент вероятности выпадения игрушки. Учитывается конкретный случай(зависит от кол-ва
     * видов игрушек)
     * @param drawIndivid
     */
    private void appropriateProbability(List<Integer> drawIndivid){
        int len = drawIndivid.size();
        Map<Integer, Integer> significanceProbability = new HashMap<>();
        if (len == 1){significanceProbability.put(1, 100);}
        else if (len == 2) {
            significanceProbability.put(1, 75);
            significanceProbability.put(2, 25);
        } else if (len == 3) {
            significanceProbability.put(1, 48);
            significanceProbability.put(2, 35);
            significanceProbability.put(3, 17);
        } else if (len == 4) {
            significanceProbability.put(1, 35);
            significanceProbability.put(2, 30);
            significanceProbability.put(3, 22);
            significanceProbability.put(4, 13);
        } else if (len == 5) {
            significanceProbability.put(1, 28);
            significanceProbability.put(2, 24);
            significanceProbability.put(3, 22);
            significanceProbability.put(4, 16);
            significanceProbability.put(5, 10);
        } else if (len == 6) {
            significanceProbability.put(1, 23);
            significanceProbability.put(2, 21);
            significanceProbability.put(3, 18);
            significanceProbability.put(4, 16);
            significanceProbability.put(5, 13);
            significanceProbability.put(6, 9);
        } else if (len == 7) {
            significanceProbability.put(1, 21);
            significanceProbability.put(2, 18);
            significanceProbability.put(3, 17);
            significanceProbability.put(4, 15);
            significanceProbability.put(5, 13);
            significanceProbability.put(6, 10);
            significanceProbability.put(7, 6);
        } else if (len == 8) {
            significanceProbability.put(1, 18);
            significanceProbability.put(2, 16);
            significanceProbability.put(3, 15);
            significanceProbability.put(4, 14);
            significanceProbability.put(5, 12);
            significanceProbability.put(6, 10);
            significanceProbability.put(7, 9);
            significanceProbability.put(8, 6);
        } else if (len == 9) {
            significanceProbability.put(1, 16);
            significanceProbability.put(2, 15);
            significanceProbability.put(3, 13);
            significanceProbability.put(4, 12);
            significanceProbability.put(5, 11);
            significanceProbability.put(6, 10);
            significanceProbability.put(7, 9);
            significanceProbability.put(8, 8);
            significanceProbability.put(9, 6);
        } else if (len == 10) {
            significanceProbability.put(1, 15);
            significanceProbability.put(2, 14);
            significanceProbability.put(3, 13);
            significanceProbability.put(4, 12);
            significanceProbability.put(5, 11);
            significanceProbability.put(6, 9);
            significanceProbability.put(7, 8);
            significanceProbability.put(8, 7);
            significanceProbability.put(9, 6);
            significanceProbability.put(10, 5);
        } else significanceProbability.put(0, 0);
        for (Game item:draw) {
            item.setProbability(significanceProbability.get(item.getSignificance()));
        }
    }

    /**
     * Возвращает список с выигрышной игрушкой или список с несколькими игрушками, которые выиграли.
     * Это может произойти, потому что изначально политика присвоения статуса ценности предполагает, что под один из
     * десяти уровень может попасть сразу несколько разных игрушек. Например пингвин, который стоит 4р. и страус, который
     * стоит 6р. т.к. их цены находятся в пределе от 1р до 10р, то они оба получат статус ценности 1. А политика розыгрыша
     * завязана на статусе ценности. Он проводится между 10 уровнями ценности игрушек. Если получается, что список, которы
     * возвращает этот метод содержит больше одной игрушки, то рандом автоматически выберет одну из них на равных условиях,
     * т.к. у них у всех одинаковый уровень ценности. Последнее действие реализовано в методе .....
     * @param drawIndivid
     * @return
     */
    private List<Game> givPrize(List<Integer> drawIndivid){
        Random rn = new Random();
        List<Game> Prize = new ArrayList<>();
        int num = rn.nextInt(1,101);
        int indic = 0;
        int sizeList = drawIndivid.size();
        if(sizeList == 1){
            indic = 1;
        } else if (sizeList == 2) {
            if (num >= 1 && num <= 25){ indic = 2;}
            else if (num >= 26 && num <= 100) { indic = 1;}
        } else if (sizeList == 3) {
            if (num >= 1 && num <= 17) {indic = 3;}
            else if (num >= 18 && num <= 53) {indic = 2;}
            else if (num >= 54 && num <= 100) {indic = 1;}
        } else if (sizeList == 4) {
            if (num >= 1 && num <= 13) {indic = 4;}
            else if (num >= 14 && num <= 35) {indic = 3;}
            else if (num >= 36 && num <= 65) {indic = 2;}
            else if (num >= 66 && num <= 100) {indic = 1;}
        } else if (sizeList == 5) {
            if (num >= 1 && num <= 10){indic = 5;}
            else if (num >= 11 && num <= 26){indic = 4;}
            else if (num >= 27 && num <= 48){indic = 3;}
            else if (num >= 49 && num <= 72){indic = 2;}
            else if (num >= 73 && num <= 100){indic = 1;}
        } else if (sizeList == 6) {
            if (num >= 1 && num <= 9){indic = 6;}
            else if (num >= 10 && num <= 22){indic = 5;}
            else if (num >= 23 && num <= 38){indic = 4;}
            else if (num >= 39 && num <= 56){indic = 3;}
            else if (num >= 57 && num <= 77){indic = 2;}
            else if (num >= 78 && num <= 100){indic = 1;}
        } else if (sizeList == 7) {
            if (num >= 1 && num <= 6){indic = 7;}
            else if (num >= 7 && num <= 16){indic = 6;}
            else if (num >= 17 && num <= 29){indic = 5;}
            else if (num >= 30 && num <= 44){indic = 4;}
            else if (num >= 45 && num <= 61){indic = 3;}
            else if (num >= 62 && num <= 79){indic = 2;}
            else if (num >= 80 && num <= 100){indic = 1;}
        } else if (sizeList == 8) {
            if (num >= 1 && num <= 6){indic = 8;}
            else if (num >= 7 && num <= 15){indic = 7;}
            else if (num >= 16 && num <= 25){indic = 6;}
            else if (num >= 26 && num <= 37){indic = 5;}
            else if (num >= 38 && num <= 51){indic = 4;}
            else if (num >= 52 && num <= 65){indic = 3;}
            else if (num >= 66 && num <= 80){indic = 2;}
            else if (num >= 81 && num <= 100){indic = 1;}
        } else if (sizeList == 9) {
            if (num >= 1 && num <= 6){indic = 9;}
            else if (num >= 7 && num <= 14){indic = 8;}
            else if (num >= 15 && num <= 23){indic = 7;}
            else if (num >= 24 && num <= 33){indic = 6;}
            else if (num >= 34 && num <= 44){indic = 5;}
            else if (num >= 44 && num <= 55){indic = 4;}
            else if (num >= 56 && num <= 68){indic = 3;}
            else if (num >= 69 && num <= 83){indic = 2;}
            else if (num >= 84 && num <= 100){indic = 1;}
        } else if (sizeList == 10) {
            if (num >= 1 && num <= 5){indic = 10;}
            else if (num >= 6 && num <= 11){indic = 9;}
            else if (num >= 12 && num <= 18){indic = 8;}
            else if (num >= 19 && num <= 26){indic = 7;}
            else if (num >= 27 && num <= 34){indic = 6;}
            else if (num >= 35 && num <= 45){indic = 5;}
            else if (num >= 46 && num <= 57){indic = 4;}
            else if (num >= 58 && num <= 70){indic = 3;}
            else if (num >= 71 && num <= 84){indic = 2;}
            else if (num >= 85 && num <= 100){indic = 1;}
        }
        for (Game item: draw) {
            if (item.getSignificance() == indic) {
                Prize.add(item);
            }
        }
        return Prize;
    }

    /**
     * Уменьшает кол-во игрушек в списке, который предназначен для розыгрыша. Уменьшает на единицу тот вид игрушки, которая
     * выиграла. Если это была последняя игрушка из списка, то она вовсе удаляется из списка для розыгрыша.
     * @param game
     */
    private void removeWinGameFromListDraw(Game game){
        for (Game it:draw) {
            if (it.getName().equals(game.getName())){
                it.setCountInDrawDown();
                if (it.getCountInDraw() == 0){
                    draw.remove(it);
                }
                break;
            }
        }
    }
        @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (Game item : market) {
            res.append(item);
            res.append("\n");
        }
        return res.toString();
    }
}
