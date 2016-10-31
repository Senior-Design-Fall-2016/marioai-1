package ch.idsia.agents.learning;

import ch.idsia.agents.Agent;
import ch.idsia.agents.BasicLearningAgent;
import ch.idsia.agents.LearningAgent;
import ch.idsia.benchmark.tasks.LearningTask;
import com.anji.util.Properties;
import org.jgap.*;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerAllele;

import com.anji.neat.*;
import com.anji.nn.*;

import java.io.IOException;

/**
 * Created by Andrew on 10/19/2016.
 */
public class AnjiLearningAgent extends BasicLearningAgent implements LearningAgent
{
    Agent agent;
    Agent finalAgent;
    Properties myProps;
    Evolver anjiEvolver;

    public void learn() {

    }

    public void giveReward(float r) {

    }

    public void newEpisode() {

    }

    public Agent getBestAgent()
    {
        return null;
    }

    public void setEvaluationQuota(long num) {

    }

    public void setLearningTask(LearningTask task) {

    }

    public void init() {
        // Start with a DefaultConfiguration, which comes setup with the
        // most common settings.
        // -------------------------------------------------------------
        try{
            myProps = new Properties("mario.properties");
            anjiEvolver = new Evolver();
            anjiEvolver.init(myProps);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
