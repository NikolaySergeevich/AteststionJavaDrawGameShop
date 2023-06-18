public class Game {
    private String name;
    private int countInMarcet;
    private int countInDraw;
    private String collar;
    private int price;
    private  int significance;
    private int probability;



    public Game(String name, int count, String collar, int price) {
        this.name = name;
        this.countInMarcet = count;
        this.collar = collar;
        this.price = price;
        this.significance = getSignific();
        this.probability = 0;
        this.countInDraw = 0;
    }

    public int getCountInDraw() {
        return countInDraw;
    }

    public void setCountInDrawUp() {
        this.countInDraw = countInDraw + 1;
    }

    public void setCountInDrawDown() {
        this.countInDraw = countInDraw - 1;
    }

    public int getCountInMarcet() {
        return countInMarcet;
    }

    public void setCountInMarcet() {
        this.countInMarcet = countInMarcet - 1;
    }


    public void setProbability(int probability) {
        this.probability = probability;
    }

    public String getName() {
        return name;
    }

    public int getSignificance() {
        return significance;
    }

    public void setSignificance(int significance) {
        this.significance = significance;
    }

    public  int getSignific(){
        if (this.price >= 1 && this.price <= 10){ return 1;
        } else if (this.price >= 11 && this.price <= 20) { return 2;
        }
        else if (this.price >= 21 && this.price <= 30) { return 3;
        }
        else if (this.price >= 31 && this.price <= 40) { return 4;
        }
        else if (this.price >= 41 && this.price <= 50) { return 5;
        }
        else if (this.price >= 51 && this.price <= 60) { return 6;
        }
        else if (this.price >= 61 && this.price <= 70) { return 7;
        }
        else if (this.price >= 71 && this.price <= 80) { return 8;
        }
        else if (this.price >= 81 && this.price <= 90) { return 9;
        }
        else if (this.price >= 91) { return 10;
        }
        else{ return  1;
        }
    }

    @Override
    public String toString() {
        return String.format("%s: цена - %d; статус - %d; вероятность - %d; осталось в магазине - %d шт., в розыгрыше - %d шт.",
                name, price, significance,
                probability, countInMarcet, countInDraw);
    }
}
