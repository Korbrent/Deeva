package to.us.tcfc;

import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;

public class WindowsSoundHandler {
    void test(){
        Mixer.Info[] mixerInfos = javax.sound.sampled.AudioSystem.getMixerInfo();
        for (Mixer.Info info : mixerInfos){
            System.out.println(info.toString());
        }
    }
}
