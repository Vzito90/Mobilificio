package com.example.demo;

public class Mobili {
	
	private String nome;
	private String marca;
	private double prezzo;
	private String url;
	private int quantita;
	
	
	public Mobili(String nome, String marca, double prezzo, String url, int quantita) {
		super();
		this.nome = nome;
		this.marca = marca;
		this.prezzo = prezzo;
		this.url = url;
		this.quantita = quantita;
	}
	
	public Mobili() {
		
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Mobili [nome=" + nome + ", marca=" + marca + ", prezzo=" + prezzo + ", url=" + url + ", quantita="
				+ quantita + "]";
	}
	
	

}