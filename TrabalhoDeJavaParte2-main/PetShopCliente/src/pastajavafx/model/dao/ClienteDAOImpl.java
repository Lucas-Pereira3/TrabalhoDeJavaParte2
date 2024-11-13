package pastajavafx.model.dao;

import java.util.ArrayList;
import java.util.List;
import pastajavafx.model.Cliente;

public class ClienteDAOImpl implements ClienteDAO {

    private final List<Cliente> clientes = new ArrayList<>();

    @Override
    public void salvar(Cliente cliente) {
        // Verifica se o cliente já existe pelo CPF
        Cliente existente = buscarPorId(cliente.getCpf());
        if (existente != null) {
            atualizar(cliente);  // Se já existe, atualiza o cliente
        } else {
            clientes.add(cliente);  // Se não existe, adiciona um novo cliente
            System.out.println("Cliente salvo: " + cliente.getNome());
        }
    }

    @Override
    public void atualizar(Cliente cliente) {
        // Atualiza os dados de um cliente existente
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCpf().equals(cliente.getCpf())) {
                clientes.set(i, cliente);
                System.out.println("Cliente atualizado: " + cliente.getNome());
                return;
            }
        }
    }

    @Override
    public void excluir(String cpf) {
        clientes.removeIf(cliente -> cliente.getCpf().equals(cpf));
        System.out.println("Cliente excluído com CPF: " + cpf);
    }

    @Override
    public Cliente buscarPorId(String cpf) {
        return clientes.stream()
                       .filter(cliente -> cliente.getCpf().equals(cpf))
                       .findFirst()
                       .orElse(null);
    }

    @Override
    public List<Cliente> listarTodos() {
        return clientes;
    }
}


