package sample;

public class player {
    private int player_id;
    private String name;
    private String team;
    private int age;
    private String position;
    private String nationality;
    private int rating;
    private String speciality;
    private int jersey;

    @Override
    public String toString() {
        return "player{" +
                "player_id=" + player_id +
                ", name='" + name + '\'' +
                ", team='" + team + '\'' +
                ", age=" + age +
                ", position='" + position + '\'' +
                ", nationality='" + nationality + '\'' +
                ", rating=" + rating +
                ", speciality='" + speciality + '\'' +
                ", jersey=" + jersey +
                '}';
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public int getJersey() {
        return jersey;
    }

    public void setJersey(int jersey) {
        this.jersey = jersey;
    }
}
