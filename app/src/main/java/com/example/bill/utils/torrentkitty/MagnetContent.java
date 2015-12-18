package com.example.bill.utils.torrentkitty;

/**
 * Created by bill_lv on 2015/12/10.
 */
public class MagnetContent {

    private String name;
    private String magnetUrl;

    public MagnetContent(String name, String magnetUrl) {
        this.name = name;
        this.magnetUrl = magnetUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMagnetUrl() {
        return magnetUrl;
    }

    public void setMagnetUrl(String magnetUrl) {
        this.magnetUrl = magnetUrl;
    }

    @Override
    public String toString() {
        return name + " : " + magnetUrl;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MagnetContent){
            MagnetContent content = (MagnetContent) obj;
            System.out.println("src name " + this.getName());
            System.out.println("tag name " + content.getName());
            return content.getName().equals(this.name);
        }
        return super.equals(obj);
    }
}
