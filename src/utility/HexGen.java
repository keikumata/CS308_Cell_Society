package utility;

import javafx.scene.shape.Polygon;

public class HexGen extends CellGen{

    public HexGen (double r) {
        super(r);
    }
    
    public Polygon makeShape(int i,int j){
        Polygon polygon = new Polygon();
        if(j%2==0){
            polygon.getPoints().addAll(new Double[]{3*cellLength*j/2, (Math.sqrt(3)/2+Math.sqrt(3)*i)*cellLength,
                                                    (0.5+3*j/2)*cellLength,Math.sqrt(3)*i*cellLength,
                                                    (1.5+3*j/2)*cellLength, Math.sqrt(3)*i*cellLength,
                                                    (2+3*j/2)*cellLength, (Math.sqrt(3)/2+Math.sqrt(3)*i)*cellLength,
                                                    (1.5+3*j/2)*cellLength, (Math.sqrt(3)+Math.sqrt(3)*i)*cellLength,
                                                    (0.5+3*j/2)*cellLength,(Math.sqrt(3)+Math.sqrt(3)*i)*cellLength});
        }else{
            polygon.getPoints().addAll(new Double[]{(1.5+3*Math.floor(j/2))*cellLength, Math.sqrt(3)*(i+1)*cellLength,
                                                    (2+3*Math.floor(j/2))*cellLength,Math.sqrt(3)*(i+0.5)*cellLength,
                                                    (3+3*Math.floor(j/2))*cellLength, Math.sqrt(3)*(i+0.5)*cellLength,
                                                    (3.5+3*Math.floor(j/2))*cellLength, Math.sqrt(3)*(i+1)*cellLength,
                                                    (3+3*Math.floor(j/2))*cellLength, Math.sqrt(3)*(i+1.5)*cellLength,
                                                    (2+3*Math.floor(j/2))*cellLength,Math.sqrt(3)*(i+1.5)*cellLength});
        }
        return polygon;
    }

}
