package Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.EOFException;
import java.io.IOException;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];
    FloatControl fc;
    public int volumeScale = 3;
    float volume;

    //constrcutor
    public Sound(){

        soundURL[0] = getClass().getClassLoader().getResource("Sound/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getClassLoader().getResource("Sound/coin.wav");
        soundURL[2] = getClass().getClassLoader().getResource("Sound/powerup.wav");
        soundURL[3] = getClass().getClassLoader().getResource("Sound/unlock.wav");
        soundURL[4] = getClass().getClassLoader().getResource("Sound/fanfare.wav");
        soundURL[5] = getClass().getClassLoader().getResource("Sound/hitmonster.wav");
        soundURL[6] = getClass().getClassLoader().getResource("Sound/receivedamage.wav");
        soundURL[7] = getClass().getClassLoader().getResource("Sound/hitmonster.wav");
        soundURL[8] = getClass().getClassLoader().getResource("Sound/levelup.wav");
        soundURL[9] = getClass().getClassLoader().getResource("Sound/cursor.wav");
        soundURL[10] = getClass().getClassLoader().getResource("Sound/burning.wav");
        soundURL[11] = getClass().getClassLoader().getResource("Sound/cuttree.wav");
        soundURL[12] = getClass().getClassLoader().getResource("Sound/gameover.wav");
        soundURL[13] = getClass().getClassLoader().getResource("Sound/stairs.wav");
        soundURL[14] = getClass().getClassLoader().getResource("Sound/sleep.wav");
        soundURL[15] = getClass().getClassLoader().getResource("Sound/blocked.wav");
        soundURL[16] = getClass().getClassLoader().getResource("Sound/parry.wav");
        soundURL[17] = getClass().getClassLoader().getResource("Sound/speak.wav");
        soundURL[18] = getClass().getClassLoader().getResource("Sound/Merchant.wav");
        soundURL[19] = getClass().getClassLoader().getResource("Sound/Dungeon.wav");
        soundURL[20] = getClass().getClassLoader().getResource("Sound/chipwall.wav");
        soundURL[21] = getClass().getClassLoader().getResource("Sound/dooropen.wav");
        soundURL[22] = getClass().getClassLoader().getResource("Sound/FinalBattle.wav");
















    }

    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        }catch(Exception e){

        }
    }

    public void play(){
        clip.start();
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);

    }

    public void stop(){
        clip.stop();

    }

    public void checkVolume(){
        switch (volumeScale) {
            case 0:
                volume = -80f;
                break;
            case 1:
                volume = -20f;
                break;
            case 2:
                volume = -12f;
                break;
            case 3:
                volume = -5f;
                break;
            case 4:
                volume = 1f;
                break;
            case 5:
                volume = 6f;
                break;
        }
        fc.setValue(volume);
    }
}
