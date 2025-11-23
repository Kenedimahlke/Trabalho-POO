import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

// Implementação concreta de persistência usando serialização Java (Strategy Pattern)
public class PersistenciaSerializacao implements EstrategiaPersistencia {
    
    @Override
    public void salvar(Map<String, List<?>> dados, String destino) throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(destino))) {
            oos.writeObject(dados);
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, List<?>> carregar(String origem) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(origem))) {
            return (Map<String, List<?>>) ois.readObject();
        }
    }
    
    @Override
    public boolean existe(String origem) {
        File arquivo = new File(origem);
        return arquivo.exists() && arquivo.isFile();
    }
    
    @Override
    public void deletar(String origem) throws Exception {
        File arquivo = new File(origem);
        if (arquivo.exists()) {
            if (!arquivo.delete()) {
                throw new IOException("Não foi possível deletar o arquivo: " + origem);
            }
        }
    }
}
