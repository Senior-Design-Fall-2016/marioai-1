package ch.idsia.agents;

import ch.idsia.agents.Agent;
import ch.idsia.agents.BasicLearningAgent;
import ch.idsia.agents.LearningAgent;
import ch.idsia.agents.learning.NeatAgent;
import ch.idsia.benchmark.mario.environments.Environment;
import ch.idsia.benchmark.tasks.LearningTask;
import tk.mechtecs.jneat.jneat.*;
import tk.mechtecs.jneat.jneatcommon.*;
import tk.mechtecs.jneat.jgraph.*;
import tk.mechtecs.jneat.gui.*;


import java.util.Vector;

/**
 * Created by Andrew on 10/31/2016.
 */
public class JneatLearningAgent extends BasicLearningAgent implements LearningAgent
{
    private final int NUMOUTPUTS = Environment.numberOfKeys;
    private final int NUMINPUTS = 28;
    private final int POPSIZE = 10;
    Agent agent;
    Agent finalAgent;
    LearningTask agentLearningTask;
    Population neatPop;
    Agent bestAgent;
    private int bestScore = 5;
    int populationSize = 100;
    int generations = 5000;
    long evaluationQuota; //common number of trials
    long currentEvaluation; // number of exhausted trials
    private String name = getClass().getSimpleName();


    public void learn() {
        // Within main simulation loop

        Vector neatOrgs = neatPop.getOrganisms();
        currentEvaluation = 0;
        while(currentEvaluation < evaluationQuota) {
            for(int i=0;i<neatOrgs.size();i++) {
                // Assign each organism a "fitness". A measure of how well the organism performed since the last evolution.
                agent = new NeatAgent(((Organism) neatOrgs.get(i)));

                ((Organism) neatOrgs.get(i)).setFitness(agentLearningTask.evaluate(agent));
                System.out.println("Organism " + i + ":" + ((Organism) neatOrgs.get(i)).getFitness());
            }
            neatPop.epoch((int)currentEvaluation++);
        }

    }

    public void giveReward(float r) {

    }

    public void newEpisode() {

    }

    public Agent getBestAgent()
    {
        Vector neatOrgs = neatPop.getOrganisms();

        for(int i=0;i<neatOrgs.size();i++)
        {
            // Extract the neural network from the jNEAT organism.
            Organism curr = ((Organism)neatOrgs.get(i));
            if(curr.getChampion()) {
                return new NeatAgent(curr);
            }
        }

        return new NeatAgent((Organism)neatOrgs.get(0));
    }

    public void setEvaluationQuota(long num) {
        evaluationQuota = num;
    }

    public void setLearningTask(LearningTask task) {
        agentLearningTask = task;
    }

    public void init() {
        System.out.println("This should print something");
        Neat.initbase();
        neatPop = new Population(POPSIZE, NUMINPUTS ,NUMOUTPUTS, 10 /* max index of nodes */, true /* recurrent */, 0.5 /* probability of connecting two nodes */ );
    }

    private double probe(int x, int y, byte[][] scene) {
        int realX = x + 11;
        int realY = y + 11;
        return (scene[realX][realY] != 0) ? 1 : 0;
    }
}
