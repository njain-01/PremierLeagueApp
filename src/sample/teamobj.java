package sample;

public class teamobj {
    private String name;
    private String captain;
    private String manager;
    private String home_stadium;

    @Override
    public String toString() {
        return "teamobj{" +
                "name='" + name + '\'' +
                ", captain='" + captain + '\'' +
                ", manager='" + manager + '\'' +
                ", home_stadium='" + home_stadium + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaptain() {
        return captain;
    }

    public void setCaptain(String captain) {
        this.captain = captain;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getHome_stadium() {
        return home_stadium;
    }

    public void setHome_stadium(String home_stadium) {
        this.home_stadium = home_stadium;
    }
}
