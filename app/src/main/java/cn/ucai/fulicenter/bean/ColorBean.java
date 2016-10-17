package cn.ucai.fulicenter.bean;

/**
 * Created by Administrator on 2016/10/13.
 */
public class ColorBean {
    private int colorid;
    private String colorname;
    private String colorCode;
    private String colorUrl;

    public int getColorid() {
        return colorid;
    }

    public void setColorid(int colorid) {
        this.colorid = colorid;
    }

    public String getColorname() {
        return colorname;
    }

    public void setColorname(String colorname) {
        this.colorname = colorname;
    }

    public String getColorUrl() {
        return colorUrl;
    }

    public void setColorUrl(String colorUrl) {
        this.colorUrl = colorUrl;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public ColorBean() {
    }

    @Override
    public String toString() {
        return "ColorBean{" +
                "colorid=" + colorid +
                ", colorname='" + colorname + '\'' +
                ", colorCode='" + colorCode + '\'' +
                ", colorUrl='" + colorUrl + '\'' +
                '}';
    }
}

