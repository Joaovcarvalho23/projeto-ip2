package br.ufrpe.habitact.negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.ufrpe.habitact.dados.IRepositorio;
import br.ufrpe.habitact.dados.Repositorio;
import br.ufrpe.habitact.excecoes.ObjetoDuplicadoException;
import br.ufrpe.habitact.excecoes.ObjetoNaoExisteException;
import br.ufrpe.habitact.excecoes.SenhaIncorretaException;
import br.ufrpe.habitact.negocio.beans.Administrador;
import br.ufrpe.habitact.negocio.beans.Cliente;
import br.ufrpe.habitact.negocio.beans.Exercicio;
import br.ufrpe.habitact.negocio.beans.PlanoTreino;
import br.ufrpe.habitact.negocio.beans.Usuario;
import br.ufrpe.habitact.negocio.beans.enums.TipoExercicio;

public class ControladorUsuario {
	private IRepositorio<Usuario> repositorioUsuario;

	public ControladorUsuario() {
		this.repositorioUsuario = new Repositorio<>();
	}

	public void cadastrarUsuario(Usuario u) throws ObjetoDuplicadoException {
		this.repositorioUsuario.inserir(u);
	}
	
	public Usuario autenticarUsuario(String email, String senha) {
		Usuario u = null;
		for (Usuario usuario : this.repositorioUsuario.listar()) {
			if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
				u = usuario;
			}
		}
		return u;
	}

	public void alterarDados(Usuario usuarioAntigo, Usuario usuarioNovo, String senha)
			throws SenhaIncorretaException, ObjetoNaoExisteException {
		if (usuarioAntigo.getSenha().equals(senha)) {
			this.repositorioUsuario.atualizar(usuarioAntigo, usuarioNovo);
		} else {
			throw new SenhaIncorretaException("Senha Incorreta");
		}
	}
	
	public List<Usuario> buscarUsuario(String nome) throws ObjetoNaoExisteException {
		List<Usuario> usuariosList = new ArrayList<>(this.listarUsuarios());
		List<Usuario> lista = usuariosList.stream()
				.filter(plano -> plano.getNome().equals(nome)).collect(Collectors.toList());
		return lista;
	}

	public List<Usuario> listarUsuarios() {
		return repositorioUsuario.listar();
	}

}