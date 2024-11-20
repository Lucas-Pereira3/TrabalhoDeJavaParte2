
package pastajavafx.model.dao;

import pastajavafx.model.Cliente;
import java.util.List;

public interface ClienteDAO {

    void salvar(Cliente cliente);

    void atualizar(Cliente cliente);

    void excluir(String cpf);

    Cliente buscarPorId(String cpf);

    List<Cliente> listarTodos();
}

