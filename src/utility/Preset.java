package utility;

public class Preset {
    private int preset=1;
    private int[] initSugar;
    private int[] maxAge;
    
    public Preset(int preset){
        this.preset=preset;
        if(preset==1){
            int[] initSugar={5,25};
            this.initSugar=initSugar;
        }else{
            int[] maxAge={60,100};
            int[] initSugar={40,80};
        }
    }
}
