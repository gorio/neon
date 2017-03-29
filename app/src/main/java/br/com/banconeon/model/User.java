package br.com.banconeon.model;

import com.android.internal.util.Predicate;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("ClienteId")
    @Expose
    private String clienteId;
    @SerializedName("Nome")
    @Expose
    private String nome;
    @SerializedName("Telefone")
    @Expose
    private String telefone;
    @SerializedName("Foto")
    @Expose
    private String foto;

    public User(String id, String clienteId, String nome, String telefone, String foto) {
        this.id = id;
        this.clienteId = clienteId;
        this.nome = nome;
        this.telefone = telefone;
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
