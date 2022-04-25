package br.ufrpe.habitact.negocio;

import br.ufrpe.habitact.dados.IRepositorio;
import br.ufrpe.habitact.dados.Repositorio;
import br.ufrpe.habitact.excecoes.MaisDeUmPlanoNoMesmoPeriodoException;
import br.ufrpe.habitact.excecoes.MaisDeUmTreinoNoMesmoDiaException;
import br.ufrpe.habitact.excecoes.ObjetoDuplicadoException;
import br.ufrpe.habitact.excecoes.ObjetoNaoExisteException;
import br.ufrpe.habitact.negocio.beans.*;
import br.ufrpe.habitact.negocio.beans.enums.TipoExercicio;

import java.util.ArrayList;
import java.util.List;

public class CrudPlanoTreino{
    private IRepositorio<PlanoTreino> repositorioPlanoTreino;

    public CrudPlanoTreino(){
        this.repositorioPlanoTreino = new Repositorio<>();
    }

    public void cadastrarPlanoTreino(PlanoTreino planoTreino)
            throws ObjetoDuplicadoException, MaisDeUmPlanoNoMesmoPeriodoException {
        for (PlanoTreino p : this.listarPlanoTreino()) {
            if (p.getCliente().equals(planoTreino.getCliente())
                    && (p.getDataInicio().equals(planoTreino.getDataInicio())
                    | p.getDataFim().equals(planoTreino.getDataFim())
                    | ((p.getDataFim().isBefore(planoTreino.getDataFim()))
                    && (p.getDataInicio().isAfter(planoTreino.getDataInicio()))))) {
                throw new MaisDeUmPlanoNoMesmoPeriodoException("Cliente já tem um plano neste período");
            }
        }
        this.repositorioPlanoTreino.inserir(planoTreino);
    }

    public void inserirTreinoNoPlano(PlanoTreino planoTreino, Treino novoTreino)
            throws ObjetoDuplicadoException, ObjetoNaoExisteException, MaisDeUmTreinoNoMesmoDiaException {
        PlanoTreino planoAntigo = planoTreino;
        for (PlanoTreino p : this.listarPlanoTreino()) {
            if(p.getCliente().equals(planoTreino.getCliente())){
                for(Treino t : p.getTreinos()){
                    if(t.getDiaFeito().equals(novoTreino.getDiaFeito())){
                        throw new MaisDeUmTreinoNoMesmoDiaException("Já há um treino no dia escolhido");
                    }
                }
            }
        }
        planoTreino.cadastrarTreino(novoTreino);
        this.repositorioPlanoTreino.atualizar(planoAntigo, planoTreino);
    }

    public void removerTreinoNoPlano(PlanoTreino planoTreino, Treino treinoAlvo) throws ObjetoNaoExisteException{
         PlanoTreino planoNovo = planoTreino;
         planoNovo.removerTreino(treinoAlvo);
         this.repositorioPlanoTreino.atualizar(planoTreino, planoNovo);
    }

    public void alterarPlanoDeTreino(PlanoTreino planoAntigo, PlanoTreino planoNovo) throws ObjetoNaoExisteException {
        this.repositorioPlanoTreino.atualizar(planoAntigo, planoNovo);
    }

    public PlanoTreino buscarPlanoTreino(Cliente cliente) throws ObjetoNaoExisteException {
        List<PlanoTreino> planoList = new ArrayList<>(this.repositorioPlanoTreino.listar());
        return planoList.stream().filter(planoTreino -> planoTreino.getCliente().equals(cliente)).reduce((a, b) -> b)
                .orElse(null);
    }

    public void removerPlanoTreino(PlanoTreino treino) throws ObjetoNaoExisteException {
        this.repositorioPlanoTreino.remover(treino);
    }

    public List<PlanoTreino> listarPlanoTreino(){
        return repositorioPlanoTreino.listar();
    }
}