import java.util.List;

public interface Subject {
    void adicionarObservador(Observer observer);
    void removerObservador(Observer observer);
    void notificarObservadores(String evento, Object dados);
    List<Observer> getObservadores();
}
