package ch.idsia.agents;

import ch.idsia.agents.learning.NeatMonsterAgent;
import ch.idsia.benchmark.mario.environments.Environment;
import ch.idsia.benchmark.tasks.LearningTask;
import com.sun.tools.javac.code.Attribute;
import fr.neatmonster.labs.neat.Genome;
import fr.neatmonster.labs.neat.Pool;
import fr.neatmonster.labs.neat.Species;

import java.util.ArrayList;

/**
 * Created by Andrew on 11/4/2016.
 */
public class NeatMonsterLearningAgent extends BasicLearningAgent implements LearningAgent {
    private final int NUMOUTPUTS = Environment.numberOfKeys;
    private final int NUMINPUTS = 28;
    private final int POPSIZE = 10;
    ArrayList<NeatMonsterAgent> agents = new ArrayList<>();
    LearningTask agentLearningTask;
    NeatMonsterAgent bestAgent;
    private int bestScore = 5;
    int populationSize = 100;
    int generations = 5000;
    long evaluationQuota; //common number of trials
    long currentEvaluation; // number of exhausted trials
    private String name = getClass().getSimpleName();


    public void learn() {
        // Within main simulation loop
        currentEvaluation = 0;
        bestAgent = agents.get(0);
        while (currentEvaluation < evaluationQuota) {
            System.out.println("CurrentEval: " + currentEvaluation);
            int index = 0;
            for (NeatMonsterAgent ag : agents) {
                // Assign each organism a "fitness". A measure of how well the organism performed since the last evolution.
                ag.setGenomeFitness(agentLearningTask.evaluate(ag));
                if (ag.getGenomeFitness() > bestAgent.getGenomeFitness()) {
                    bestAgent = ag;
                }
                index++;
                System.out.println("Agent " + index + ": " + ag.getGenomeFitness());
            }
            Pool.newGeneration();
            initAgents();
            currentEvaluation++;
        }

    }

    private void initAgents() {
        agents.clear();
        for (final Species species : Pool.species)
            for (final Genome genome : species.genomes) {
                genome.generateNetwork();
                agents.add(new NeatMonsterAgent(species, genome));
            }
    }

    public void giveReward(float r) {

    }

    public void newEpisode() {

    }

    public Agent getBestAgent() {

        return bestAgent;
    }

    public void setEvaluationQuota(long num) {
        evaluationQuota = num;
    }

    public void setLearningTask(LearningTask task) {
        agentLearningTask = task;
    }

    public void init() {
        System.out.println("This should print something");
        Pool.initializePool();
        initAgents();
    }
}
