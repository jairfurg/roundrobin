package roundrobin;

import java.util.ArrayList;
import java.util.Random;

public class SistemaOperacional {

    private final ArrayList<Processo> processos = new ArrayList<>();
    private int ciclo;
    private final float probResposta;

    public SistemaOperacional(float pr) {
        this.probResposta = pr;

    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public void addProcesso(Processo process) {
        this.processos.add(process);

    }

    public void geraAcoes(int ciclo) {
        /*
         Primeiro vamos ver se tem algum processo pra ser Startado...
         */
        System.out.print(String.format("%05d", ciclo) + " E");
        for (Processo p : processos) {
            if (p.getStatus() == 'X' && ciclo >= p.getTempodecriacao()) {
                p.setStatus('P');
            }
            if (p.getTempodevida() == 0) {
                p.setStatus('F');
            } else {
                if (p.getStatus() == 'B') {
                    int npb = (int) (this.probResposta * 10);
                    Random random = new Random();
                    int sorteado = random.nextInt(10);
                    if (sorteado >= npb) {
                        p.setStatus('P');
                    }
                }

            }
            System.out.print("   " + p.toString() );
        }
        System.out.println("");
    }

    public boolean getProcesosAtivos() {
        boolean terminou = false;
        for (Processo p : processos) {
            if (p.getStatus() != 'F') {
                terminou = true;
                break;
            }
        }
        return terminou;
    }

}
