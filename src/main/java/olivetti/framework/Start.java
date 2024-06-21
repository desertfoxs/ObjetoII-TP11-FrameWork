package olivetti.framework;

import java.util.List;

public class Start {

    private Menu menu;

    public Start(String path) {
        this.menu = new Menu(path);
    }

    public void init(){
        this.menu.print();
    }
}
