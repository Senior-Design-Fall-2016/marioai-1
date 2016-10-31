package ch.idsia.agents.learning;

import ch.idsia.agents.Agent;
import ch.idsia.agents.controllers.BasicMarioAIAgent;
import ch.idsia.benchmark.mario.environments.Environment;
import ch.idsia.evolution.Evolvable;
import jneat.Genome;
import jneat.NNode;
import jneat.Network;
import jneat.Organism;

import java.util.Vector;

/**
 * Created by Andrew on 10/31/2016.
 */
public class NeatAgent extends BasicMarioAIAgent implements Agent, Evolvable {

    private Organism org;
    private Network net;
    final int numberOfOutputs = Environment.numberOfKeys;
    final int numberOfInputs = /*53*/28;
    static private final String name = "NeatAgent";

    public NeatAgent(Organism organism)
    {
        super(name);
        this.org = organism;
        this.net = this.org.getNet();
    }


    public Evolvable getNewInstance()
    {
        return new NeatAgent(new Organism(this.org.getFitness(), this.org.getGenome(), this.org.getGeneration()));
    }

    public Evolvable copy()
    {
        Genome g = this.org.getGenome();

        int id = g.getGenome_id();

        return new NeatAgent(new Organism(this.org.getFitness(), this.org.getGenome().duplicate(id), this.org.getGeneration()));
    }

    public void reset()
    {
        this.net = new Network(this.net.getInputs(), this.net.getOutputs(), new Vector(), this.net.getNet_id());
    }

    public void mutate()
    {
        return;
    }

    public boolean[] getAction()
    {
        double inputs[] = new double[numberOfInputs+1];
        inputs[numberOfInputs] = -1.0; // Bias

        byte[][] scene = mergedObservation;

        // Populate the rest of "inputs" from this organism's status in the simulation.
        //
        //

        int which = 0;
        for (int k = -2; k < 3; k++)
        {
            for (int j = -2; j < 3; j++)
            {
                inputs[which++] = probe(k, j, scene);
            }
        }

        inputs[inputs.length - 3] = isMarioOnGround ? 1 : 0;
        inputs[inputs.length - 2] = isMarioAbleToJump ? 1 : 0;
        inputs[inputs.length - 1] = 1;


        // Load these inputs into the neural network.
        this.net.load_sensors(inputs);

        int net_depth = this.net.max_depth();
        // first activate from sensor to next layer....
        this.net.activate();

        // next activate each layer until the last level is reached
        for (int relax = 0; relax <= net_depth; relax++)
        {
            this.net.activate();
        }

        double[] outputs = new double[numberOfOutputs];

        for(int output = 0; output < numberOfOutputs; output++) {
            outputs[output] = ((NNode) this.net.getOutputs().elementAt(output)).getActivation();
        }
        for (int i = 0; i < action.length; i++)
        {
            action[i] = outputs[i] > 0;
        }
        return action;
    }

    private double probe(int x, int y, byte[][] scene)
    {
        int realX = x + 11;
        int realY = y + 11;
        return (scene[realX][realY] != 0) ? 1 : 0;
    }
}
