package util;

public class Collegamento {

	private int doveCopiare;
	private String ordine;
	private int limite;

	public Collegamento() {

	}

	public Collegamento(int destinazione, String ordine, int limite) {
		super();
		this.doveCopiare = destinazione;
		this.ordine = ordine;
		this.limite = limite;
	}

	public String getOrdine() {
		return ordine;
	}

	public void setOrdine(String ordine) {
		this.ordine = ordine;
	}

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}

	public int getDestinazione() {
		return doveCopiare;
	}

	public void setDestinazione(int destinazione) {
		this.doveCopiare = destinazione;
	}

	@Override
	public String toString() {
		return "Collegamento [doveCopiare=" + doveCopiare + ", ordine=" + ordine + ", limite=" + limite + "]";
	}

}
