package cn.delei.designpattern.facade;

/**
 * 操作门户
 *
 * @author deleiguo
 */
public class OperationFacade {
    private TVSystem tvSystem = new TVSystem();
    private VCDSystem vcdSystem = new VCDSystem();

    public void watchMovie() {
        tvSystem.turnOnTv();
        vcdSystem.setCD();
        tvSystem.startWatch();
    }
}
