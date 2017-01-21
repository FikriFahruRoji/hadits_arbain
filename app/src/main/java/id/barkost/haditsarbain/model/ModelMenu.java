package id.barkost.haditsarbain.model;

/**
 * Created by fikri on 20/01/17.
 */

public class ModelMenu {
    private String latin, arabic;
    private int no;

    public ModelMenu(){}

    public ModelMenu(int no, String latin, String arabic){
        this.no = no;
        this.latin = latin;
        this.arabic = arabic;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getLatin() {
        return latin;
    }

    public void setLatin(String latin) {
        this.latin = latin;
    }

    public String getArabic() {
        return arabic;
    }

    public void setArabic(String arabic) {
        this.arabic = arabic;
    }
}
