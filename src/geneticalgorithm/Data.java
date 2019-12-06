/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 *
 * @author ajied
 */
public class Data {
    public ArrayList<Individu> initial = new ArrayList<>();
    public ArrayList<Individu> individu = new ArrayList<>();
    public ArrayList<Individu> individu2n = new ArrayList<>();
    public ArrayList<Individu> offspring = new ArrayList<>();
    public ArrayList<Individu> parent = new ArrayList<>();
    public ArrayList<Individu> parentTemp = new ArrayList<>();
    public ArrayList<Integer> fitness = new ArrayList<>();
    public ArrayList<Double> rouletteProbs = new ArrayList<>();
    Random random = new Random();
    int MAX = 100;
    double pCROSSOVER = 0.9;
    double pMUTATION = 0.1;
    
    public void setTarget(){
        // INPUT HERE
        initial.add(new Individu());
        initial.get(0).getKromosom().add(1);
        initial.get(0).getKromosom().add(10);
        initial.get(0).getKromosom().add(9);
        initial.get(0).getKromosom().add(3);
    }
    
    public void generatePopulation(int n){
        int max = 26;
        int min = 1;
        Random rand = new Random();
        for(int i = 0; i < n; i++){
            individu.add(new Individu());
            for(int j = 0; j < initial.get(0).getKromosom().size(); j++){
                int a = rand.nextInt((max - min) +1 ) + min;
                individu.get(i).getKromosom().add(a);
            }
        }
    }
    
    public void initFitnessEvaluation(){
        int x,y,sum = 0, temp = 0;
        for(int i = 0; i < individu.size(); i++){
            for(int j = 0; j < initial.get(0).getKromosom().size(); j++){
                x = initial.get(0).getKromosom().get(j);
                y = individu.get(i).getKromosom().get(j);
                sum += Math.abs(x-y);
            }
            individu.get(i).setFitness(Math.abs(MAX-sum));
            sum = 0;
        }
    }
    
    public void roulette(){
        int min = 0;
        Random rand = new Random();
        double prevProb = 0;
        double sumOfFitness = 0;
        double count = 0;
        for(int i = 0; i < individu.size(); i++){
            sumOfFitness += individu.get(i).getFitness();
        }
        for(int j = 0; j < individu.size(); j++){
            count = prevProb + individu.get(j).getFitness();
            rouletteProbs.add(count);
            prevProb = count;
        }
        int index;
        for(int i = 0; i < individu.size(); i++){
            index = 0;
            int a = rand.nextInt((int) ((sumOfFitness - min) +1)) + min;
            parent.add(new Individu());
            for(int j=0; j<sumOfFitness; j++){
                if(j == rouletteProbs.get(index))
                    index++;
                if(a == j)
                    break;
            }
                parent.get(i).getKromosom().addAll(individu.get(index).getKromosom());
                parent.get(i).setFitness(individu.get(index).getFitness());
        }
    }
    
    public void crossoverCheck(){
        int temp = 0;
        int temp2 = 0;
        for(int i = 0; i < parent.size()/2; i++){
            double p = random.nextDouble();
            if(p < pCROSSOVER){
                int kiri = 0 + random.nextInt((parent.get(i).getKromosom().size()-1 - 0) + 1);
                int kanan = kiri + random.nextInt((parent.get(i).getKromosom().size() - kiri) + 1);    
//                System.out.println(kanan+" "+kiri);
                for(int j = kiri; j < kanan; j++){
                    temp = parent.get(i).getKromosom().get(j);
                    temp2 = parent.get(i+1).getKromosom().get(j);
                    parent.get(i).getKromosom().set(j, temp2);
                    parent.get(i+1).getKromosom().set(j, temp);
                }
            }
        }
        for(int k = 0; k < parent.size(); k++){
            offspring.add(new Individu());
            offspring.get(k).getKromosom().addAll(parent.get(k).getKromosom());
        }
    }
    
    public void mutation(){
        for(int i = 0; i < offspring.size(); i++){
            double p = random.nextDouble();
            double mut = random.nextDouble();
            if(p < pMUTATION){
                int r = random.nextInt(offspring.get(i).getKromosom().size());
                if(mut > 0.5 && offspring.get(i).getKromosom().get(r) < 26)
                    offspring.get(i).getKromosom().set(r, offspring.get(i).getKromosom().get(r)+1);
                else if(mut < 0.5 && offspring.get(i).getKromosom().get(r) > 1)
                    offspring.get(i).getKromosom().set(r, offspring.get(i).getKromosom().get(r)-1);
            }
        }
    }
    
    public void elitism(){
        fitnessEvaluation();
        individu2n.addAll(parent);
        individu2n.addAll(offspring);
        Collections.sort(individu2n, Collections.reverseOrder(Comparator.comparingInt(h -> h.getFitness())));
        individu.clear();
        for(int i = 0; i < individu2n.size()/2; i++){
           individu.add(new Individu());
           individu.get(i).getKromosom().addAll(individu2n.get(i).getKromosom());
        }
        parent.clear();
        offspring.clear();
    }

    public void fitnessEvaluation() {
        int x,y,sum = 0;
        for(int i = 0; i < parent.size(); i++){
            for(int j = 0; j < initial.get(0).getKromosom().size(); j++){
                x = initial.get(0).getKromosom().get(j);
                y = parent.get(i).getKromosom().get(j);
                sum += Math.abs(x-y);
            }
            parent.get(i).setFitness(Math.abs(MAX-sum));
            sum = 0;
        }
        for(int i = 0; i < parent.size(); i++){
            for(int j = 0; j < initial.get(0).getKromosom().size(); j++){
                x = initial.get(0).getKromosom().get(j);
                y = offspring.get(i).getKromosom().get(j);
                sum += Math.abs(x-y);
            }
            offspring.get(i).setFitness(Math.abs(MAX-sum));
            sum = 0;
        }
    }

    public ArrayList<Individu> getInitial() {
        return initial;
    }

    public ArrayList<Individu> getIndividu() {
        return individu;
    }

    public ArrayList<Individu> getIndividu2n() {
        return individu2n;
    }

    public ArrayList<Individu> getOffspring() {
        return offspring;
    }

    public ArrayList<Individu> getParent() {
        return parent;
    }

    public ArrayList<Integer> getFitness() {
        return fitness;
    }
    
    
}
