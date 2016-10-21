package ch.idsia.agents;

import ch.idsia.agents.learning.SimpleMLPAgent;
import ch.idsia.benchmark.tasks.LearningTask;
import org.jgap.Chromosome;
import org.jgap.ChromosomeMaterial;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Genotype;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerAllele;

/**
 * Created by Andrew on 10/19/2016.
 */
public class AnjiLearningAgent extends BasicLearningAgent implements LearningAgent
{
    Agent agent;
    Agent finalAgent;

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
        Configuration conf = new DefaultConfiguration();

        // Set the fitness function we want to use, which is our
        // MinimizingMakeChangeFitnessFunction. We construct it with
        // the target amount of change passed in to this method.
        // ---------------------------------------------------------
        FitnessFunction myFunc;

        ChromosomeMaterial sample = new ChromosomeMaterial();
        conf.setSampleChromosomeMaterial( sample );

    }
}
