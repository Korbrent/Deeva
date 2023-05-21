package to.us.tcfc.SoundHandler;

import javax.sound.sampled.Mixer;

public class WindowsSoundHandler implements SoundHandler {
    public void test(){
        Mixer.Info[] mixerInfos = javax.sound.sampled.AudioSystem.getMixerInfo();
        for (Mixer.Info info : mixerInfos){
            System.out.println(info.toString());
        }
    }
}
