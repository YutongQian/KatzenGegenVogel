package de.katzengegenvogel.middletier.vogel;

import java.util.*;

/**
 * <pre>
 * Wie viele Vögel sind vorgesehen,
 * und wie viele Angriffsrunden sollen durchgeführt werden?
 *
 * </pre>
 *
 */
public class VogelPlan {
    /**Ein Map, wobei Integer, die Zeit angibt und
     * List die Vögel enthält, die zu diesem Zeitpunkt dort abgelegt wurden. */
    private Map<Integer, Vogel> plan;
    private int mindestSpielrund;
    private Random random=new Random();
    private List<Vogel> vogeln=new ArrayList<>();
    public VogelPlan(){
        plan =new HashMap<>();

    }

    /**
     * Füge eine Angriffsrunde hinzu,
     * einschließlich Vogelart, Lebenspunkte, Zeitpunkt des Angriffs und Anzahl der Vögel.
     * @param vogelType die Type der Vogel


     * @param anzahl wie vielen Vogel
     * @return eine List von Vogel
     */
    public VogelPlan addWelle(Class<?extends Vogel>vogelType ,int anzahl){
//        List<Vogel> vogeln = new ArrayList<>();
        try{

            for (int i = 0; i < anzahl; i++) {
                Vogel vogel=vogelType.getDeclaredConstructor().newInstance();
                vogeln.add(vogel);
                int rund;
                if(vogel instanceof Vogel){
                    rund=random.nextInt(20000);

                }else if(vogel instanceof Schwan){
                    rund=random.nextInt(5000,2000);
                }else{
                    rund=random.nextInt(10000,20000);
                }

                plan.put(rund,vogel);


            }
        }catch(Exception ausnahme){
            throw new RuntimeException(ausnahme);
        }


        return this;
    }

    /**
     *  Alle Vögel, die in dieser Spielrunde generiert wurden
     * @return eine List von diesem Vogel
     */
    public List<Vogel> getAlleVogeln(){

        return vogeln;
    }

    /**
     * Alle Vögel, die zu einem bestimmten Zeitpunkt einen Angriff starten müssen
     * @param rund die Zeit
     * @return List vom vogel
     */
   public Vogel get(int rund){
        return plan.get(rund);
   }

   public int getMindestSpielrund(){
       if (!plan.isEmpty()){
           mindestSpielrund= Collections.max(plan.keySet());
       }
       return mindestSpielrund;


   }
}
