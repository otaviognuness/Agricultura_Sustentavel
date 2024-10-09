package Model;

public class DadosSafra {
    private int id;
    private String nome;
    private String dataPlantio;
    private String dataColheita;
    private String variedaCultivada;
    private double areaPlantada;
    private double produtividade;
    private String condicaoClimatica;
    private int idAgroquimicos;
    private String fertilizantes;
    private String pesticida;

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataPlantio() {
        return dataPlantio;
    }

    public void setDataPlantio(String dataPlantio) {
        this.dataPlantio = dataPlantio;
    }

    public String getDataColheita() {
        return dataColheita;
    }

    public void setDataColheita(String dataColheita) {
        this.dataColheita = dataColheita;
    }

    public String getVariedaCultivada() {
        return variedaCultivada;
    }

    public void setVariedaCultivada(String variedaCultivada) {
        this.variedaCultivada = variedaCultivada;
    }

    public double getAreaPlantada() {
        return areaPlantada;
    }

    public void setAreaPlantada(double areaPlantada) {
        this.areaPlantada = areaPlantada;
    }

    public double getProdutividade() {
        return produtividade;
    }

    public void setProdutividade(double produtividade) {
        this.produtividade = produtividade;
    }

    public String getCondicaoClimatica() {
        return condicaoClimatica;
    }

    public void setCondicaoClimatica(String condicaoClimatica) {
        this.condicaoClimatica = condicaoClimatica;
    }

    public int getIdAgroquimicos() {
        return idAgroquimicos;
    }

    public void setIdAgroquimicos(int idAgroquimicos) {
        this.idAgroquimicos = idAgroquimicos;
    }

    public String getFertilizantes() {
        return fertilizantes;
    }

    public void setFertilizantes(String fertilizantes) {
        this.fertilizantes = fertilizantes;
    }

    public String getPesticida() {
        return pesticida;
    }

    public void setPesticida(String pesticida) {
        this.pesticida = pesticida;
    }
}