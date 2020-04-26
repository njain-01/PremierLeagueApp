package sample;

public class Sponsor {

    public String name;
    public int tier;
    public String path;

    public Sponsor(String name,int tier,String path){
        this.name=name;
        this.tier=tier;
        this.name=this.name.concat(" Tier-").concat(String.valueOf(tier));
        this.path=path;
        this.path=this.path.replace('\\','/');
        this.path= "/".concat(this.path);
        System.out.println(this.name+" "+this.path);
    }
}
