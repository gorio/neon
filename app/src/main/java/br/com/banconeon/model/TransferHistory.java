package br.com.banconeon.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransferHistory extends User{

//    @SerializedName("Id")
//    @Expose
//    private String id;
//    @SerializedName("ClienteId")
//    @Expose
//    private String clienteId;
    @SerializedName("Valor")
    @Expose
    private Double valor;
    @SerializedName("Token")
    @Expose
    private String token;
    @SerializedName("Data")
    @Expose
    private String data;

    public TransferHistory(String id, String clienteId, String nome, String telefone, String foto) {
        super(id, clienteId, nome, telefone, foto);
    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getClienteId() {
//        return clienteId;
//    }
//
//    public void setClienteId(String clienteId) {
//        this.clienteId = clienteId;
//    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String toString(TransferHistory example) {
        return new Gson().toJson(example);
    }
}