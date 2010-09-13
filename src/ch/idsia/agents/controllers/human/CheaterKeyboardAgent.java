package ch.idsia.agents.controllers.human;

import ch.idsia.agents.Agent;
import ch.idsia.benchmark.mario.engine.GlobalOptions;
import ch.idsia.benchmark.mario.engine.sprites.Mario;
import ch.idsia.benchmark.mario.environments.Environment;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey Karakovskiy
 * Date: Apr 8, 2009
 * Time: 3:36:16 AM
 * Package: ch.idsia.controllers.agents.controllers;
 */
public class CheaterKeyboardAgent extends KeyAdapter implements Agent
{
    private boolean Action[] = null;

    private String Name = "Instance of CheaterKeyboardAgent";
    private Integer prevFPS = 24;

    public CheaterKeyboardAgent()
    {
        reset();
    }

    public void integrateObservation(int[] serializedLevelSceneObservationZ, int[] serializedEnemiesObservationZ, float[] marioFloatPos, float[] enemiesFloatPos, int[] marioState)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean[] getAction()
    {
        return Action;
    }

    public void integrateObservation(Environment environment) { }

    public void giveIntermediateReward(float intermediateReward)
    {

    }

    public void reset()
    {
        // Just check you keyboard.
        Action = new boolean[16];
    }

    public boolean[] getAction(Environment observation)
    {
        return Action;
    }

    public String getName() { return Name; }

    public void setName(String name) { Name = name; }


    public void keyPressed(KeyEvent e)
    {
        toggleKey(e.getKeyCode(), true);
    }

    public void keyReleased(KeyEvent e)
    {
        toggleKey(e.getKeyCode(), false);
    }

    private void toggleKey(int keyCode, boolean isPressed)
    {
        switch (keyCode)
        {
            //Cheats;
            case KeyEvent.VK_D:
                if (isPressed)
                    GlobalOptions.gameViewerTick();
                break;
            case KeyEvent.VK_V:
                if (isPressed)
                    GlobalOptions.isVisualization = !GlobalOptions.isVisualization;
                break;
            case KeyEvent.VK_U:
                Action[Mario.KEY_LIFE_UP] = isPressed;
                break;
            case KeyEvent.VK_W:
                Action[Mario.KEY_WIN] = isPressed;
                break;
            case KeyEvent.VK_P:
                if (isPressed)
                {
//                    LOGGER.println("Pause On/Off", LOGGER.VERBOSE_MODE.INFO);
                    GlobalOptions.isPauseWorld = !GlobalOptions.isPauseWorld;
                    Action[Mario.KEY_PAUSE] = GlobalOptions.isPauseWorld;
                }
                break;
            case KeyEvent.VK_L:
                if (isPressed)
                {
//                    LOGGER.println("Labels On/Off", LOGGER.VERBOSE_MODE.INFO);
                    GlobalOptions.areLabels = !GlobalOptions.areLabels;
                }
                break;
            case KeyEvent.VK_C:
                if (isPressed)
                {
//                    LOGGER.println("Center On/Off", LOGGER.VERBOSE_MODE.ALL);
                    GlobalOptions.isMarioAlwaysInCenter = !GlobalOptions.isMarioAlwaysInCenter;
                }
                break;
            case 61:
                if (isPressed)
                {
//                    LOGGER.println("FPS increase by 1. Current FPS is " + ++GlobalOptions.FPS, LOGGER.VERBOSE_MODE.INFO);
                    ++GlobalOptions.FPS;
                    GlobalOptions.AdjustMarioVisualComponentFPS();
                }
                break;
            case 45:
                if (isPressed)
                {
//                    LOGGER.println("FPS decrease . Current FPS is " + --GlobalOptions.FPS, LOGGER.VERBOSE_MODE.INFO);
                    --GlobalOptions.FPS;
                    GlobalOptions.AdjustMarioVisualComponentFPS();
                }
                break;
            case 56:  // chr(56) = 8
                if (isPressed)
                {
                    int temp = prevFPS;
                    prevFPS = GlobalOptions.FPS;
                    GlobalOptions.FPS = (GlobalOptions.FPS == GlobalOptions.MaxFPS) ? temp : GlobalOptions.MaxFPS;
//                    LOGGER.println("FPS has been changed. Current FPS is " +
//                            ((GlobalOptions.FPS == GlobalOptions.MaxFPS) ? "\\infty" : GlobalOptions.FPS), LOGGER.VERBOSE_MODE.INFO);
                    GlobalOptions.AdjustMarioVisualComponentFPS();
                }
                break;
            case KeyEvent.VK_G:
                if (isPressed)
                {
                    GlobalOptions.isShowReceptiveField = !(GlobalOptions.isShowReceptiveField);
//                    System.out.println("GlobalOptions.receptiveFieldWidth = " + GlobalOptions.receptiveFieldWidth);
                }
                break;
            case KeyEvent.VK_SPACE:
                if (isPressed)
                {
//                    System.out.println("Space bar pressed");
                    GlobalOptions.isGameplayStopped = !GlobalOptions.isGameplayStopped;
                }
                break;
            case KeyEvent.VK_F:
                if (isPressed)
                {
                    GlobalOptions.isFly = !GlobalOptions.isFly;
                }
        }
    }
}
