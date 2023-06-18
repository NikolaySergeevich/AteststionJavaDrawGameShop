import java.io.*;
import java.util.Date;

public class Fillee {
    public void CreateFile() throws IOException {
        try {
            String pathProject = System.getProperty("user.dir");
            String pathFile = pathProject.concat("/Prize.txt");
            File file1 = new File(pathFile);
            if(file1.createNewFile()){
                System.out.println("Создана БД для игрушек, которые выиграли.");
            }else System.out.println("В магазине уже есть БД, куда записываются игрушки, которые выиграли.");
        }catch (Exception e){
            e.getMessage();
            System.out.println("Что-то пошло не так.");
        }
    }
    public void WriteGameInDB(Game game) throws IOException {
        FileWriter file = new FileWriter("Prize.txt", true);
        PrintWriter pw = new PrintWriter(file);
        Date date = new Date();
        pw.println(String.format("%s, дата выигрыша - %s",game.getName(), date));
        pw.close();
    }

}
