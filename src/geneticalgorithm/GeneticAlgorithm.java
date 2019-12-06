/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm;

/**
 *
 * @author ajied
 */
public class GeneticAlgorithm {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int temp;
        Data data = new Data();
//        System.out.println("Masukkan kata : ");
        data.setTarget();
        data.generatePopulation(50);
        data.initFitnessEvaluation();
        System.out.println("Target : ");
        for(int i = 0; i < data.getInitial().get(0).getKromosom().size(); i++){
            temp = data.getInitial().get(0).getKromosom().get(i);
            System.out.print((char)(temp+96)+" ");
        }
        System.out.println("\n");
        for(int i = 0; i < data.getInitial().get(0).getKromosom().size(); i++){
            System.out.print(data.getInitial().get(0).getKromosom().get(i)+" ");
        }
        System.out.println("\n\nPopulasi awal : ");
        for(int j = 0; j < data.individu.size(); j++){
                for(int k = 0; k < data.individu.get(j).getKromosom().size(); k++){
                    System.out.print(data.individu.get(j).getKromosom().get(k)+ " ");
                }
                System.out.println("\n");
            }
        System.out.println("Fitness populasi awal : ");
        for(int k = 0; k < data.individu.size(); k++){
            System.out.print(data.individu.get(k).getFitness()+" ");
        }
        System.out.println("\n");
        for(int i =0; i < 200; i++){
            data.roulette();
            data.crossoverCheck();
            data.mutation();
            data.elitism();
            System.out.println("Kromosom : ");
            for(int j = 0; j < data.individu2n.size(); j++){
                System.out.println("\n");
                for(int k = 0; k < data.individu2n.get(j).getKromosom().size(); k++){
                    temp = data.individu2n.get(j).getKromosom().get(k);
                    System.out.print((char)(temp+96)+" ");
                }
            }
            System.out.println("\n");
            System.out.print("Fitness : ");
            for(int j = 0; j < data.individu2n.size(); j++){
                System.out.print(data.individu2n.get(j).getFitness()+ " ");
            }
            System.out.println("\n");
            data.individu2n.clear();
        }
    }
}
