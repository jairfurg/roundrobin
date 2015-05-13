package roundrobin;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Processador_RoundRobin {

    /**
     * @param args the command line arguments
     *
     * pb = Probabilidade de Bloqueio pr = Probabilidade de Resposta tc = Tempo
     * de criação tv = Tempo de Vida
     *
     * [E] = Execucao [P] = Prontos [B] = Bloqueados
     * @return
     */
    public static void main(String[] args) {
        int AC;
        int PC;
        int N, Z;
        int memoria[] = new int[256];
        int instrucao[] = new int[256];
        int comando;
        int[] tmpVet = null;
        Scanner file = null;
        int vlr1;
        try {
            file = new Scanner(new File("c:\\entrada.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int ciclo = 0;
        String erro = "";
        String erro2 = "";
        ArrayList<Processo> processos = new ArrayList<>();

        String tmp = file.nextLine();
        int quantum = Integer.valueOf(tmp);
        if (quantum > 0) {
            tmp = file.nextLine();
        } else {
            erro = erro.concat("Prob de bloqueio deve ser entre 0.1 e 0.9\n");
        }
//        float pb = Float.valueOf(tmp);
//        if (pb < 0.1 || pb > 0.9) {
//            erro = erro.concat("Prob de bloqueio deve ser entre 0.1 e 0.9\n");
//        }

        tmp = file.nextLine();
        float pr = Float.valueOf(tmp);
        if (pr < 0.1 || pr > 0.9) {
            erro = erro.concat("Prob de resposta deve ser entre 0.1 e 0.9\n");
        }

        vlr1 = -1;
        String vlr2 = "";
        while (vlr1 != 0) {
            try {
                vlr1 = file.nextInt();
                vlr2 = file.nextLine();
                System.out.println(vlr1 + " , " + vlr2);
                if (vlr1 > 0) {
                    File fileOrigin = new File(vlr2);
                    //ler bytes e converter em vetor de inteiros
                    BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileOrigin));

                    int x = 0;
                    int i = 0;
                    while ((x = in.read()) != -1) {
                        tmpVet[i] = x;
                        i++;
                    }
                    in.close();

                    //==================
                    processos.add(new Processo(vlr1, tmpVet));
                } else {
                    erro2 = erro2.concat("existe um processo invalido\n");
                }
            } catch (Exception e) {

                vlr1 = 0;
                vlr2 = "";
            }
        }
        if (erro.equals("")) {
            SistemaOperacional windows = new SistemaOperacional(pr);
            for (Processo p : processos) {
                windows.addProcesso(p);
            }
            System.out.print("Ciclo SO ");
            for (Processo x : processos) {
                System.out.print(" P" + processos.indexOf(x) + " ");
            }
            System.out.println("");
            /*
             ==> Daqui pra cima Gabriel se vira
             Gabriel tem que me entregar os processos 
             devidamente instanciados
            
             */
            AC = 0;   //    Start Acumulador
            PC = 0;   //    Start Process Counter
            Z = 0;    //    Start Z
            N = 0;    //    Start Acumulador

            while (windows.getProcesosAtivos()) {
                windows.geraAcoes(ciclo);
                ciclo++;

                for (Processo p : processos) {
                    if (p.getTempodevida() > 0) {
                        if (p.getStatus() == 'P') {
                            p.setStatus('E');
                        }
                        if (p.getStatus() == 'E') {

                            AC = p.getAc();
                            PC = p.getPc();
                            comando = instrucao[PC];
                            memoria = p.getMemoria();
                            instrucao = p.getInstrucoes();

                            for (int i = 0; i < quantum; i++) {

                                switch (comando) {
                                    case 0:
                                        // NOP
                                        PC++;
                                        break;
                                    case 1:
                                        // STO
                                        PC++;
                                        AC = memoria[PC];
                                        break;
                                    case 2:
                                        // STA Men[end]

                                        PC++;
                                        memoria[PC] = AC;
                                        PC++;
                                        break;
                                    case 3:
                                        // LDA
                                        PC++;
                                        AC = memoria[PC];
                                        break;
                                    case 4:
                                        // ADD
                                        PC++;
                                        memoria[PC] += AC;
                                        break;
                                    case 5:
                                        // OR
                                        PC++;
                                        AC = memoria[PC] | AC;
                                        break;
                                    case 6:
                                        // AND
                                        PC++;
                                        AC = memoria[PC] & AC;
                                        break;
                                    case 7:
                                        break;
                                    case 8:
                                        break;
                                    case 9:
                                        break;
                                    case 10:
                                        break;
                                    case 11:
                                        break;
                                    case 12:
                                        break;
                                    case 13:
                                        break;
                                    case 14:
                                        break;
                                    case 15:
                                        break;

                                }
                                if (comando >= 4 && comando <= 6) {
                                    p.setStatus('B');
                                    p.salvaInterrupcao(AC, PC, N, Z, memoria);
                                } else {
                                    p.processa(PC);
                                }

                                System.out.printf(String.format("%05d", ciclo) + "  ");
                                for (Processo x : processos) {
                                    System.out.print("   " + x.toString());
                                }
                                System.out.println("");
                                ciclo++;
                                if (p.getStatus() == 'B') {
                                    break;
                                }
                                if (p.getTempodevida() < 1) {
                                    p.finaliza();
                                    break;
                                }
                            }
                            if (p.getStatus() == 'E') {
                                p.setStatus('P');
                            }
                        }
                    }
                }
            }
            System.out.println("terminado");
        } else {
            System.out.println(erro);
        }
        System.out.println(erro2);
    }

//    void interrompe( Processo p) {
//         p.salvaInterrupcao(AC, PC, N, Z, memoria);
//    }
}
