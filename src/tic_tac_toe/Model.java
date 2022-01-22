package tic_tac_toe;

public class Model {

    private int size;

    private Player actualPlayer;

    private Player[][] table;
    
    
    private int lengthToWin = 5;
    
    private boolean delete1 = false;
    private boolean delete2 = false;

    public Model(int size) {
        this.size = size;
        actualPlayer = Player.X;

        table = new Player[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                table[i][j] = Player.NOBODY;
            }
        }
    }

    /**
     *
     * @param row 
     * Az adott oszlop ahova az új elem el lett helyezve
     * @param column
     * Az adott sor ahova az új elem el lett helyezve
     * @return
     * Visszaadja Az aktuális lépés elemét
     * Ha egy nem üres mezőre akarunk lépni akkor azt a program nem engedi
     * Beállítja az aktuális játékost
     */
    public Player step(int row, int column) {
        if (table[row][column] != Player.NOBODY) {
            return table[row][column];
        }

        table[row][column] = actualPlayer;

        if (actualPlayer == Player.X) {
            actualPlayer = Player.O;
        } else {
            actualPlayer = Player.X;
        }
        setDelete1(false);
        setDelete2(false);
        
        return table[row][column];
    }
   
    /**
     *
     * @return
     * Visszaadja a megtalált értéket és ha ez nem győztes akkor Player.NOBODY-t fog visszaadni
     * Soronként,oszloponként és átlóként megnézi hogy nyert-e az aktuális játékos
     */
    public Player findWinner() {
        boolean draw = true;
        for(int i = 0; i < size; ++i) {
            for(int j = 0; j < size; ++j) {
                if(table[i][j] == Player.NOBODY) {
                    draw = false;
                }
            }
        }
        if(draw) {
            return Player.DRAW;
        }
        for(int top = 0; top <= size - lengthToWin; ++top ) {
            int bottom = top + lengthToWin - 1;
            
            for(int left = 0; left <= size - lengthToWin; ++left) {
                int right = left + lengthToWin - 1;
                
                //Soronkénti vizsgálat
                nextRow: for(int row = top; row <= bottom; ++row) {
                    if(table[row][left] == Player.NOBODY) {
                        continue;
                    }
                    
                    for(int col = left; col <= right; ++col) {
                        if(table[row][col] != table[row][left]) {
                            continue nextRow;
                        } 
                      }
                
                return table[row][left];
            }
                //Oszloponkénti vizsgálat
                nextCol: for (int col = left; col <= right; ++col) {
                    if (table[top][col] == Player.NOBODY) {
                        continue;
                    }

                    for (int row = top; row <= bottom; ++row) {
                        if (table[row][col] != table[top][col]) {
                            continue nextCol;
                        }
                    }
                    return table[top][col];
                }
                //Első átló, balfelső sarokból a jobb alsóba
                diag1: if (table[top][left] != Player.NOBODY) {
                    for (int i = 1; i < lengthToWin; ++i) {
                        if (table[top+i][left+i] != table[top][left]) {
                            break diag1;
                        }
                    }
                    return table[top][left];
                }
                //Második átló, jobbfelső sarokból a bal alsóba
                diag2: if (table[top][right] != Player.NOBODY) {
                    for (int i = 1; i < lengthToWin; ++i) {
                        if (table[top+i][right-i] != table[top][right]) {
                            break diag2;
                        }
                    }
                    return table[top][right];
                }
            }
        }
        return Player.NOBODY;
    }
    
    /**
     * Visszaadja az aktuális játékost
     * @return
     */
    public Player getActualPlayer() {
        return actualPlayer;
    }

    /**
     * Visszaadja az aktuális pályát
     * @return
     */
    public Player[][] getTable() {
        return table;
    }
    
    /**
     * Beállítja a delete1 értékét
     * delete1 ha igaz akkor 1db értéket kell törölni a pályáról
     * @param b
     */
    public void setDelete1(boolean b) {
        delete1 = b;
    }

    /**
     * Beállítja a delete2 értékét
     * delete2 ha igaz akkor 2db értéket kell törölni a pályáról
     * @param b
     */
    public void setDelete2(boolean b) {
        delete2 = b;
    }

    /**
     * Visszaadja a delete1 értékét
     * delete1 ha igaz akkor 1db értéket kell törölni a pályáról
     * @return
     */
    public boolean getDelete1() {
        return delete1;
    }

    /**
     * Visszaadja a delete2 értékét
     * delete2 ha igaz akkor 2db értéket kell törölni a pályáról
     * @return
     */
    public boolean getDelete2() {
        return delete2;
    }

    /**
     * Felszabadítja a pálya [i][j] indexű elemét
     * @param i
     * @param j
     */
    public void freeField(int i, int j) {
        table[i][j] = Player.NOBODY;
    }
    
    /**
     * Megvizsgálja hogy kell-e törölni elemet és ha igen mennyit kell
     * @param row
     * Az aktuálisan lerakott elem sorindexe
     * @param column
     * Az aktuálisan lerakott elem oszlopindexe
     */
    public void dopingAPlayer(int row, int column) {
        int num = 0;
        //sorvizsgálat
        for(int i = column; i >= 0; --i) {
            if(table[row][i] == table[row][column]) {
                ++num;
            } else {
                break;
            }
        }
        --num;
        for(int i = column; i < size; ++i) {
            if(table[row][i] == table[row][column]) {
                ++num;
            } else {
                break;
            }
        }
        if(num == 3) {
            setDelete1(true);
        } else if(num == 4) {
            setDelete2(true);
        }
        num = 0;
        
        //oszlopvizsgálat
        for(int i = row; i >= 0; --i) {
            if(table[i][column] == table[row][column]) {
                ++num;
            } else {
                break;
            }
        }
        --num;
        for(int i = row; i < size; ++i) {
            if(table[i][column] == table[row][column]) {
                ++num;
            } else {
                break;
            }
        }
        if(num == 3) {
            setDelete1(true);
        } else if(num == 4) {
            setDelete2(true);
        }
        num = 0;
        
        //Első átlóvizsgálat
        int j = column;
        for(int i = row; i >= 0 && j >= 0; --i) {
            if(table[i][j] == table[row][column]) {
                ++num;
                --j;
            } else {
                break;
            }
        }
        --num;
        j = column;
        for(int i = row; i < size && j < size; ++i) {
            if(table[i][j] == table[row][column]) {
                ++num;
                ++j;
            } else {
                break;
            }
        }
        if(num == 3) {
            setDelete1(true);
        } else if(num == 4) {
            setDelete2(true);
        }
        num = 0;
        
        //Második átlóvizsgálat
        j = column;
        for(int i = row; i >= 0 && j < size; --i) {
            if(table[i][j] == table[row][column]) {
                ++num;
                ++j;
            } else {
                break;
            }
        }
        --num;
        j = column;
        for(int i = row; i < size && j >= 0; ++i) {
            if(table[i][j] == table[row][column]) {
                ++num;
                --j;
            } else {
                break;
            }
        }
        if(num == 3) {
            setDelete1(true);
        } else if(num == 4) {
            setDelete2(true);
        }
    }
}
