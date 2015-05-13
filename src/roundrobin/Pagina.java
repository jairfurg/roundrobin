package roundrobin;

public class Pagina {
    
    private int tipo;      // 0 - Segmento Codigo  ou 1 - Segmento Memória
    private int segmento; //usar pc/tamanho pagina
    private int ciclo;    //usado para saber quem sai
    private int tamanho; 
    private int processo;

    public Pagina( int tamanho) {
        this.ciclo = -1;
        this.tamanho = tamanho;
                
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getSegmento() {
        return segmento;
    }

    public void setSegmento(int segmento) {
        this.segmento = ((int)segmento/this.tamanho)+1;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public int getProcesso() {
        return processo;
    }

    public void setProcesso(int processo) {
        this.processo = processo;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }
    
    
    
    @Override
    public String toString (){
        return tipo+segmento+ciclo+"";
    }
    
}
