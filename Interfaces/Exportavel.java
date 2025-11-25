package Interfaces;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import java.time.*;
import java.util.*;

public interface Exportavel {
    String exportarParaTexto();
    void salvarEmArquivo(String caminho);
    String getFormatoExportacao();
}